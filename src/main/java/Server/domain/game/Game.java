package Server.domain.game;

import Exceptions.ArgumentNotInBoundsException;
import Exceptions.GameException;
import Server.domain.events.chatEvents.MessageEvent;
import Server.domain.events.chatEvents.WhisperEvent;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.game.chat.Message;
import Server.domain.game.participants.Participant;
import Server.domain.user.LeagueManager;
import Server.domain.game.participants.Player;
import Server.domain.game.participants.Spectator;
import Server.domain.user.User;
import Server.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game {
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    private int gameId;
    private GameSettings settings;
    private boolean game_over;
    private List<Player> players;
    private List<Round> rounds;
    private List<Spectator> spectators;
    private int dealerIndex;
    private LeagueManager leagueManager;
    private boolean isActive;
    private List<GameEvent> gameEvents;
    private int numPlayersStarted;
    private boolean canBeArchived;

    public Game(GameSettings settings, User creator, LeagueManager leagueManager){
        this.settings=settings;
        this.players=new ArrayList<>();
        this.rounds=new ArrayList<>();
        this.spectators= new ArrayList<>();
        this.gameEvents= new ArrayList<>();
        this.dealerIndex=0;
        this.leagueManager = leagueManager;
        this.isActive=true;
        this.numPlayersStarted=0;
        this.game_over = false;
        //Automatically the creator is added to the room.
        logger.info("A new game '{}' created by the user {}.",getName(),creator.getUsername());
        addPlayer(creator);
    }

    private Game(){}

    public void joinGameAsPlayer(User user){
        addPlayer(user);
    }

    public void joinGameAsSpectator(User user){
        Spectator spec=new Spectator(user);
        addSpectator(spec);
        user.addGameParticipant(this,spec);
        addGameEvent(spec,GameActions.ENTER);
        logger.info("'{}' has joined the game '{}' as spectator.", user.getUsername(),getName());

        // Send notification about the action
        NotificationService.getInstance().sendGameUpdateNotification(GameActions.ENTER, user.getUsername(),this);
    }

    public void startGame(Player initiator) throws GameException {
        if(!canPlayerContinue(initiator)){
            throw new GameException("You can't start a new round, because you've lost your chips\\money.\nYou'll be joined now as a spectator.");
        }

        if (!canStart())
            throw new GameException(String.format("Can't start round, minimal amount for a new round is %d, but currently there are only %d players.",
                    getMinimalAmountOfPlayer(), players.size()));

        filterNoChipsPlayersOrZeroBalance();

        if(isTournamentAndEnded())
            throw new GameException("Can't start a new round, because tournament has ended. \nWait for new players.");

        if(isCashAndLast())
            throw new GameException("Can't start a new round, because you are the only one in the table.\nWait for new players.");

        addGameEvent(initiator, GameActions.NEWROUND);
        if (realMoneyGame()) {
            logger.info("A new money round in game '{}' has started.", getName());
            handleNewRound(initiator.getUser().getUsername());
        } else { //tournament
            if(isActive)
                numPlayersStarted = players.size();
            setIsActive(false);
            logger.info("A new tournament round in game '{}' has started.", getName());
            handleNewRound(initiator.getUser().getUsername());
        }
    }

    private void handleNewRound(String initiator){
        initPlayersBeforeStartNewRound();
        if(dealerIndex == players.size())
            dealerIndex=0;
        Round rnd=new Round(players,settings,dealerIndex);
        rnd.setSpectatorList(this.spectators);
        dealerIndex=(dealerIndex+1)%players.size();
        rounds.add(rnd);

        // Send notification about the action
        NotificationService.getInstance().sendGameUpdateNotification(GameActions.NEWROUND, initiator,this);

        rnd.startRound();
    }

    public void removeParticipant(Spectator spectator){
        spectators.remove(spectator);
        addGameEvent(spectator,GameActions.EXIT);
        logger.info("'{}' has stopped watching this game.", spectator.getUser().getUsername());

        // Send notification about the action
        NotificationService.getInstance().sendGameUpdateNotification(GameActions.EXIT, spectator.getUser().getUsername(),this);
    }

    public void removeParticipant(Player player){
        players.remove(player);
        logger.info("'{}' has left the game. Attempting to notify active round if exists ...", player.getUser().getUsername());

        //if the player is within an active round, inform the round
        if(!rounds.isEmpty()){
            Round lastRound=getLastRound();
            if(lastRound.isRoundActive())
                lastRound.notifyPlayerExited(player);
        }

        if(isTournamentAndEnded()){
            logger.info("'{} has won the tournament in game {}.",players.get(0).getUser().getUsername(),getName());
            handleEndTournament();
        }

        leagueManager.updateUserLeague(player.getUser());

        addGameEvent(player,GameActions.EXIT);

        // Send notification about the action
        NotificationService.getInstance().sendGameUpdateNotification(GameActions.EXIT, player.getUser().getUsername(),this);
    }

    public boolean isFull(){
        return players.size() == settings.getPlayerRange().getRight();
    }

    public boolean canBeSpectated(){
        return settings.isAcceptSpectating();
    }

    private void addPlayer(User user){
        Player p = new Player(user,settings.getChipPolicy(), settings.getChipPolicy());
        if(!realMoneyGame()) try {
            p.getUser().withdraw(getBuyInPolicy(), false);//decrease amount by buy-in amount
            logger.info("'{}' paid {} as entrance fee to game '{}'.",user.getUsername(),getBuyInPolicy(),getName());
        } catch (ArgumentNotInBoundsException ignored) {}//Do nothing, wont be exception because there is check in the callee.

        addPlayer(p);
        user.addGameParticipant(this,p);

        addGameEvent(p,GameActions.ENTER);
        logger.info("'{}' has joined the game '{}' as player.", user.getUsername(),getName());

        // Send notification about the action
        NotificationService.getInstance().sendGameUpdateNotification(GameActions.ENTER, user.getUsername(),this);
    }

    public void handleMessageFromPlayer(MessageEvent messageEvent) {
        sendMessageToPlayers(messageEvent);
        sendMessageToAllSpectators(messageEvent);
    }

    public void handleMessageFromSpectator(MessageEvent messageEvent) {
        sendMessageToAllSpectators(messageEvent);
    }

    public void handleWhisperFromPlayer(WhisperEvent whisperEvent) {
        NotificationService.getInstance().sendWhisperNotification(whisperEvent);
    }

    public void handleWhisperFromSpectator(Spectator spectator, WhisperEvent whisperEvent) throws ArgumentNotInBoundsException{
        handleWhisperFromSpectator(spectator, whisperEvent.getContent(), whisperEvent.getParticipantToSendTo());
    }

    //should not be called because @param spcToSendTo - is suppose to be from type Spectator
    public void handleWhisperFromSpectator(Spectator spectator, Message whisper, Participant spcToSendTo) throws ArgumentNotInBoundsException{
        throw new ArgumentNotInBoundsException("spectator should send whispers only to other spectators");
    }

    private void sendMessageToAllSpectators(MessageEvent event) {
        for (Spectator s : spectators) {
            NotificationService.getInstance().sendMessageNotification(s, event);
        }
    }

    private void sendMessageToPlayers(MessageEvent event) {
        for (Player p : players) {
            NotificationService.getInstance().sendMessageNotification(p, event);
        }
    }

    private void filterNoChipsPlayersOrZeroBalance(){
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player p = it.next();
            if (!canPlayerContinue(p)) {
                it.remove();
                logger.info("'{}' has left the round, because his chips amount is 0 or balance is 0, now he is a spectator in the room .", p.getUser().getUsername());

                if (isTournamentAndEnded()) {
                    logger.info("'{} has won the tournament in game {}.", players.get(0).getUser().getUsername(), getName());
                    handleEndTournament();
                }
                leagueManager.updateUserLeague(p.getUser());
                addGameEvent(p, GameActions.EXIT);
                // Send notification about the action
                NotificationService.getInstance().sendGameUpdateNotification(GameActions.EXIT, p.getUser().getUsername(), this);

                joinGameAsSpectator(p.getUser());
            }
        }
    }

    private boolean canPlayerContinue(Player p){
        return !((!realMoneyGame() && p.getChipsAmount() == 0) || (realMoneyGame() && p.getUser().getBalance() == 0));
    }

    public boolean realMoneyGame(){
        return !settings.tournamentMode();
    }

    public boolean isActive(){
        if(rounds.isEmpty())
            return false;
        if(realMoneyGame())
            return rounds.get(rounds.size()-1).isRoundActive();
        return !canBeJoined() && rounds.get(rounds.size()-1).isRoundActive();
    }

    public boolean canBeJoined(){
        return isActive;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public GameSettings getSettings() {
        return settings;
    }

    public int getLeague(){
        return settings.getLeagueCriteria();
    }

    public int getBuyInPolicy(){
        return settings.getBuyInPolicy();
    }

    public int getMinimalAmountOfPlayer(){
        return settings.getPlayerRange().getLeft();
    }

    public String getName(){
        return settings.getName();
    }

    public Round getLastRound() {
        return !getRounds().isEmpty() ? rounds.get(rounds.size() - 1) : null;
    }

    public int getMaximalAmountOfPlayers() {
        return settings.getPlayerRange().getRight();
    }

    public int getDealerIndex(){
        return dealerIndex;
    }

    public List<Spectator> getSpectators() {
        return spectators;
    }

    public boolean canBeArchived(){
        return players.isEmpty() && spectators.isEmpty();
    }

    private void setIsActive(boolean selection){
        isActive = selection;
    }

    private boolean canStart(){
        return realMoneyGame() ? players.size() >= getMinimalAmountOfPlayer() : canBeJoined() ? players.size() >= getMinimalAmountOfPlayer() : true;
    }

    private void resetDealerIndex(){
        dealerIndex=0;
    }

    private boolean isTournamentAndEnded(){
        // amount of players in the room is 1 and he won the whole 'pot'.
        return checkEndCase(false);
    }

    private boolean isCashAndLast(){
        return checkEndCase(true);
    }

    private boolean checkEndCase(boolean realMoney){
        return (realMoneyGame() == realMoney && (players.size() == 1));
    }

    public void addGameEvent(Participant initiator, GameActions eventAction){
        this.gameEvents.add(new GameEvent(initiator.getUser().getUsername(),eventAction, this.getName()));
    }

    public List<GameEvent> getGameEvents(){
        return gameEvents;
    }

    private void handleEndTournament(){
        setIsActive(true);
        resetDealerIndex();
        try {
            depositTournamentEarningsForWinner();
            numPlayersStarted = 0;
        } catch (ArgumentNotInBoundsException e) {
            e.printStackTrace();
        }
    }

    public int getBalanceOfPlayer(String userName){
        return getPlayers().stream()
                .filter(player -> player.getUser().getUsername().equals(userName))
                .collect(Collectors.toList()).get(0).getChipsAmount();
    }

    private void depositTournamentEarningsForWinner() throws ArgumentNotInBoundsException {
        players.get(0).getUser().deposit(numPlayersStarted * getBuyInPolicy(),false);
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void addSpectator(Spectator s){
        spectators.add(s);
    }

    public void initPlayersBeforeStartNewRound(){
        for(Player p : players){
            p.clearCards();
            p.setLastBetSinceCardOpen(0);
            p.setTotalAmountPayedInRound(0);
        }
    }

    public void setSettings(GameSettings settings) {
        this.settings = settings;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public void setSpectators(List<Spectator> spectators) {
        this.spectators = spectators;
    }

    public void setDealerIndex(int dealerIndex) {
        this.dealerIndex = dealerIndex;
    }

    public LeagueManager getLeagueManager() {
        return leagueManager;
    }

    public void setLeagueManager(LeagueManager leagueManager) {
        this.leagueManager = leagueManager;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setGameEvents(List<GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
    }

    public int getNumPlayersStarted() {
        return numPlayersStarted;
    }

    public void setNumPlayersStarted(int numPlayersStarted) {
        this.numPlayersStarted = numPlayersStarted;
    }

    public boolean isGame_over() {
        return game_over;
    }

    public void setGame_over(boolean game_over) {
        this.game_over = game_over;
    }

    public int getGameId() {
        return gameId;
    }

    public int getId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}

