package TexasHoldem.domain.game;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.domain.game.leagues.LeagueManager;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.game.participants.Spectator;
import TexasHoldem.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public enum GameActions {
        CHECK, RAISE, CALL, FOLD
    }
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    private GameSettings settings;
    private int id;
    private List<Player> players;
    private List<Round> rounds;
    private List<Spectator> spectators;
    private int dealerIndex;
    private LeagueManager leagueManager;

    public Game(GameSettings settings, User creator, LeagueManager leagueManager){
        this.rounds=new ArrayList<>();
        this.players=new ArrayList<>();
        this.settings=settings;
        this.leagueManager = leagueManager;
        spectators= new ArrayList<>();
        dealerIndex=0;
        //Automatically the creator is added to the room.
        addPlayer(creator);
    }

    private Game(){}

    public void joinGameAsPlayer(User user) throws GameIsFullException, NoBalanceForBuyInException, LeaguesDontMatchException {
        int gameLeague=getLeague();
        int usersLeague=user.getCurrLeague();
        double userBalance=user.getBalance();
        double buyInPolicy=getBuyInPolicy();

        if(gameLeague != usersLeague)
            throw new LeaguesDontMatchException(String.format("Can't join game, user's league is %d ,while game's league is %d.",usersLeague,gameLeague));
        else if (isFull())
            throw new GameIsFullException("Can't join game as player because it's full.");
        else if(!realMoneyGame() && (userBalance < buyInPolicy))
            throw new NoBalanceForBuyInException(String.format("Buy in is %d, but user's balance is %d;",buyInPolicy,userBalance));

        addPlayer(user);
    }

    public void joinGameAsSpectator(User user) throws CantSpeactateThisRoomException {
        if(!canBeSpectated())
            throw new CantSpeactateThisRoomException("Selected game can't be spectated due to it's settings.");

        Spectator spec=new Spectator(user);
        spectators.add(spec);
        user.addGameParticipant(this,spec);
    }

    //todo: handle differently if its tournamnet or not .
    public void startNewRound() throws Exception {
        if(players.size()< settings.getPlayerRange().getLeft())
            throw new Exception(String.format("Can't start round, minimal amount for a new round is %d, but currently there are only %d players.",
                    getMinimalAmountOfPlayer(),players.size()));
        else{
            Round rnd=new Round(players,settings,dealerIndex);
            dealerIndex=dealerIndex++%players.size();
            rounds.add(rnd);
            rnd.startRound();
        }
    }

    public void removeParticipant(Spectator spectator){
        logger.info("{} has stopped watching this game.", spectator);
        spectators.remove(spectator);
    }

    public void removeParticipant(Player player){
        logger.info("{} has left the game.", player.getUser().getUsername());
        players.remove(player);

        //if the player is within an active round, inform the round
        if(!rounds.isEmpty()){
            Round lastRound=getLastRound();
            if(lastRound.isRoundActive())
                lastRound.notifyPlayerExited(player);
        }

        leagueManager.updateUserLeague(player.getUser());
    }

    private boolean isFull(){
        return players.size() == settings.getPlayerRange().getRight();
    }

    public boolean canBeSpectated(){
        return settings.isAcceptSpectating();
    }

    private void addPlayer(User user){
        Player p = new Player(user,settings.getChipPolicy(), settings.getChipPolicy());
        if(!realMoneyGame()) try {
            p.getUser().withdraw(getBuyInPolicy(), false);//decrease amount by buy-in amount
        } catch (ArgumentNotInBoundsException ignored) {}//Do nothing, wont be exception because there is check in the callee.

        players.add(p);
        user.addGameParticipant(this,p);
        logger.info("{} has joined the game.", user.getUsername());
    }

    public boolean realMoneyGame(){
        return !settings.tournamentMode();
    }

    private double getConvertRatio(){
        return (realMoneyGame()) ? 1 : settings.getBuyInPolicy()/settings.getChipPolicy();

    }
    // todo : think what to do with this flag, when to flip to false\true
    public boolean isActive(){
        return players.size() == 0;
    }

    // todo :needed?
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

    public Round getLastRound(){
        return rounds.get(rounds.size()-1);

    public int getMaximalAmountOfPlayers() {
        return settings.getPlayerRange().getRight();
    }
}
