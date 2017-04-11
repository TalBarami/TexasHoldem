package TexasHoldem.domain.game;

import TexasHoldem.common.Exceptions.BelowBuyInPolicyException;
import TexasHoldem.common.Exceptions.CantSpeactateThisRoomException;
import TexasHoldem.common.Exceptions.GameIsFullException;
import TexasHoldem.common.Exceptions.NoBalanceForBuyInException;
import TexasHoldem.domain.game.leagues.LeagueManager;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.game.participants.Spectator;
import TexasHoldem.domain.users.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public enum GameActions {
        CHECK, RAISE, CALL, FOLD
    }

    private GameSettings settings;
    private int id;
    private List<Player> players;
    private List<Round> rounds;
    private List<Spectator> spectators;
    private int dealerIndex;
    private double convertRatio;
    private LeagueManager leagueManager;

    public Game(GameSettings settings, User creator, LeagueManager leagueManager){
        this.rounds=new ArrayList<>();
        this.players=new ArrayList<>();
        this.settings=settings;
        this.leagueManager = leagueManager;
        spectators= new ArrayList<>();
        dealerIndex=0;
        convertRatio = (settings.getChipPolicy() != 0) ? settings.getBuyInPolicy()/settings.getChipPolicy() : 1;
        //Automatically the creator is added to the room.
        addPlayer(creator);
    }

    private Game(){

    }

    public boolean joinGame(User user,boolean spectate,int buyIn) throws CantSpeactateThisRoomException,
            GameIsFullException, BelowBuyInPolicyException {
        if(spectate){
            if(!canBeSpectated())
                throw new CantSpeactateThisRoomException("Selected game can't be spectated due to it's settings.");
            else {
                Spectator spec=new Spectator(user);
                spectators.add(spec);
                user.addGameParticipant(this,spec);
            }
        }
        else if (isFull())
            throw new GameIsFullException("Can't join game as player because it's full.");
        else if(user.getBalance() < settings.getBuyInPolicy())
            throw new BelowBuyInPolicyException("Buy in is "+settings.getBuyInPolicy()+", but user's balance is "+user.getBalance());

        addPlayer(user);
        return true;
    }

    public void startNewRound() throws NoBalanceForBuyInException {
        if(players.size()< settings.getPlayerRange().getLeft())
            throw new NoBalanceForBuyInException("Can't start round, minimal amount for a new round is "+
                    settings.getPlayerRange().getLeft()+", but currently only "+players.size() +" exist.");
        else{
            Round rnd=new Round(players,settings,dealerIndex);
            dealerIndex=dealerIndex++%players.size();
            rounds.add(rnd);
            rnd.startRound();
        }
    }

    public void removePlayer(Spectator spectator){
        spectators.remove(spectator);
    }

    public void removePlayer(Player player){
        players.remove(player);

        //todo : remove because of tournament mode ????
//        if(settings.getChipPolicy()!=0)
//            player.calculateEarnings(convertRatio);

        //if the player is within an active round, inform the round
        if(!rounds.isEmpty()){
            Round lastRound=rounds.get(rounds.size()-1);
            if(lastRound.isRoundActive())
                lastRound.notifyPlayerExited(player);
        }

        leagueManager.updateUserLeague(player.getUser());
    }

    private boolean isFull(){
        return players.size() == settings.getPlayerRange().getRight();
    }

    private boolean canBeSpectated(){
        return settings.isAcceptSpectating();
    }

    private void addPlayer(User user){
        Player p = new Player(user,settings.getChipPolicy(), settings.getChipPolicy());
//        if(!realMoneyGame())
//            p.updateWallet(settings.getBuyInPolicy()*-1); //decrease amount by buy-in amount
        players.add(p);
        user.addGameParticipant(this,p);
    }

    public boolean realMoneyGame(){
        return settings.getChipPolicy()==0;
    }

    // FIXME: Temporary to fix the build.
    public boolean isActive(){
        return players.size() == 0;
    }

    // FIXME: Temporary to fix the build.
    public void setGameId(int gameId){

    }
}
