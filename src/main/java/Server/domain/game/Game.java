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
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private static Logger logger = LoggerFactory.getLogger(Game.class);
    private GameSettings settings;
    private int id;
    private List<Player> players;
    private List<Round> rounds;
    private List<Spectator> spectators;
    private int dealerIndex;
    private LeagueManager leagueManager;
    private boolean isActive;
    private List<GameEvent> gameEvents;
    private int numPlayersStarted;

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
        if (!canStart())
            throw new GameException(String.format("Can't start round, minimal amount for a new round is %d, but currently there are only %d players.",
                    getMinimalAmountOfPlayer(), players.size()));

        addGameEvent(initiator, GameActions.NEWROUND);

        if (realMoneyGame()) {
            logger.info("A new money round in game '{}' has started.", getName());
            handleNewRound();
        } else { //tournament
            setIsActive(false);
            numPlayersStarted=players.size();
            logger.info("A new tournament round in game '{}' has started.", getName());
            handleNewRound();
        }

        // Send notification about the action
        NotificationService.getInstance().sendGameUpdateNotification(GameActions.NEWROUND, initiator.getUser().getUsername(),this);
    }

    private void handleNewRound(){
        initPlayersBeforeStartNewRound();
        Round rnd=new Round(players,settings,dealerIndex);
        rnd.setSpectatorList(this.spectators);
        dealerIndex=(dealerIndex+1)%players.size();
        rounds.add(rnd);
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

    public boolean realMoneyGame(){
        return !settings.tournamentMode();
    }

    private double getConvertRatio(){
        return (realMoneyGame()) ? 1 : settings.getBuyInPolicy()/settings.getChipPolicy();

    }

    public boolean isActive(){
        return isActive;
    }

    public void setGameId(int gameId){
        this.id=gameId;
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

    public int getId() {
        return id;
    }

    public boolean canBeArchived(){
        return players.isEmpty() && spectators.isEmpty();
    }

    private void setIsActive(boolean selection){
        isActive = selection;
    }

    private boolean canStart(){
        if(!realMoneyGame() && (numPlayersStarted>0 && numPlayersStarted<getMinimalAmountOfPlayer()))
            return true;
        return players.size()>=getMinimalAmountOfPlayer();
    }

    private void resetDealerIndex(){
        dealerIndex=0;
    }

    private boolean isTournamentAndEnded(){
        // amount of players in the room is 1 and he won the whole 'pot'.
        return (!realMoneyGame() && (players.size() == 1));
    }

    public void addGameEvent(Participant initiator, GameActions eventAction){
        this.gameEvents.add(new GameEvent(initiator,eventAction, this.getName()));
    }

    public List<GameEvent> getGameEvents(){
        return gameEvents;
    }

    private void handleEndTournament(){
        setIsActive(true);
        resetDealerIndex();
        try {
            depositTournamentEarningsForWinner();
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
}

