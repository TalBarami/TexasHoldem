package TexasHoldem.domain.game;

import TexasHoldem.common.Exceptions.BelowBuyInPolicyException;
import TexasHoldem.common.Exceptions.CantSpeactateThisRoomException;
import TexasHoldem.common.Exceptions.GameIsFullException;
import TexasHoldem.common.Exceptions.NoMinimumAmountOfPlayersException;
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
    private List<Player> spectators;
    private int dealerIndex;
    private double convertRatio;

    public Game(GameSettings settings, User creator){
        this.rounds=new ArrayList<>();
        this.players=new ArrayList<>();
        this.settings=settings;
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
            else spectators.add(new Player(user,0, settings.getChipPolicy()));
        }
        else if (isFull())
            throw new GameIsFullException("Can't join game as player because it's full.");
        else if(buyIn != settings.getBuyInPolicy())
            throw new BelowBuyInPolicyException("Buy-in amount  is :" + settings.getBuyInPolicy() + ", can't join with different amount.");

        addPlayer(user);
        return true;
    }

    public void startNewRound() throws NoMinimumAmountOfPlayersException {
        if(players.size()< settings.getPlayerRange().getLeft())
            throw new NoMinimumAmountOfPlayersException("Can't start round, minimal amount for a new round is "+
                    settings.getPlayerRange().getLeft()+", but currently only "+players.size() +" exist.");
        else{
            Round rnd=new Round(players,settings,dealerIndex);
            dealerIndex=dealerIndex++%players.size();
            rounds.add(rnd);
            rnd.startRound();
        }
    }

    public void removePlayer(Player player){
        //if that's player spectating -> just remove him from spectators list.
        if(spectators.contains(player)){
            spectators.remove(player);
            return;
        }

        players.remove(player);

        if(settings.getChipPolicy()!=0)
            player.calculateEarnings(convertRatio);

        //if the player is within an active round, inform the round
        if(!rounds.isEmpty()){
            Round lastRound=rounds.get(rounds.size()-1);
            if(lastRound.isRoundActive())
                lastRound.notifyPlayerExited(player);
        }

    }

    private boolean isFull(){
        return players.size() == settings.getPlayerRange().getRight();
    }

    private boolean canBeSpectated(){
        return settings.isAcceptSpectating();
    }

    private void addPlayer(User user){
        Player p = new Player(user,settings.getChipPolicy(), settings.getChipPolicy());
        p.updateWallet(settings.getBuyInPolicy()*-1); //decrease amount by buy-in amount
        players.add(p);
        user.addGamePlayer(this,p);
    }
}
