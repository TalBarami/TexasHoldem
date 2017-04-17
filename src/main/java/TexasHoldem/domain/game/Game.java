package TexasHoldem.domain.game;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.game.participants.Spectator;
import TexasHoldem.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    public Game(GameSettings settings, User creator, LeagueManager leagueManager){
        this.settings=settings;
        players=new ArrayList<>();
        rounds=new ArrayList<>();
        spectators= new ArrayList<>();
        dealerIndex=0;
        this.leagueManager = leagueManager;
        isActive=true;
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
        spectators.add(spec);
        user.addGameParticipant(this,spec);
        logger.info("'{}' has joined the game '{}' as spectator.", user.getUsername(),getName());
    }

    public void startGame(){
        /*if(players.size()< getMinimalAmountOfPlayer())
            throw new Exception(String.format("Can't start round, minimal amount for a new round is %d, but currently there are only %d players.",
                    getMinimalAmountOfPlayer(),players.size()));*/
        if(realMoneyGame())
            playMoneyGame();
        else
            playTournament();
    }

    private void playMoneyGame(){
        while(!canBeArchived()){
            if(canStart()){
                Round rnd=new Round(players,settings,dealerIndex);
                dealerIndex=(dealerIndex+1)%players.size();
                rounds.add(rnd);
                logger.info("A new money round in game '{}' has started.", getName());
                rnd.startRound();
            }
        }
    }

    private void playTournament(){
        while(!canBeArchived()){
            if(canStart()){
                Round rnd=new Round(players,settings,dealerIndex);
                dealerIndex=(dealerIndex+1)%players.size();
                rounds.add(rnd);
                logger.info("A new tournament round in game '{}' has started.", getName());
                setIsActive(false); //lock game so new players cant join.
                rnd.startRound();
            }
            if(!isActive() && isTournamentEnded()){
                resetDealerIndex();
                setIsActive(true);
            }
        }
    }

    public void removeParticipant(Spectator spectator){
        logger.info("'{}' has stopped watching this game.", spectator.getUser().getUsername());
        spectators.remove(spectator);
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

        leagueManager.updateUserLeague(player.getUser());
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

        players.add(p);
        user.addGameParticipant(this,p);
        logger.info("'{}' has joined the game '{}' as player.", user.getUsername(),getName());
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
        return players.isEmpty();
    }

    private void setIsActive(boolean selection){
        isActive = selection;
    }

    private boolean canStart(){
        return players.size()>=getMinimalAmountOfPlayer();
    }

    private void resetDealerIndex(){
        dealerIndex=0;
    }

    private boolean isTournamentEnded(){
        // amount of players in the room is 1 and he won the whole 'pot'.
        return (players.size() == 1) && (players.get(0).getChipsAmount() == getMinimalAmountOfPlayer() * settings.getChipPolicy());
    }

}
