package TexasHoldem.domain.game;

import TexasHoldem.common.Exceptions.BelowBuyInPolicyException;
import TexasHoldem.common.Exceptions.CantSpeactateThisRoomException;
import TexasHoldem.common.Exceptions.GameIsFullException;
import TexasHoldem.common.Exceptions.NoMinimumAmountOfPlayersException;
import TexasHoldem.domain.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RonenB on 05/04/2017.
 */
public class Game {
    private GameSettings settings;
    private int id;
    private List<Player> players;
    private List<Round> rounds;
    private List<User> spectators;

    public Game(GameSettings settings, User creator){
        this.rounds=new ArrayList<Round>();
        this.players=new ArrayList<Player>(); // TODO: add a new constructed associated player (to user) to the players list.
        this.settings=settings;
        if(settings.isAcceptSpectating()) spectators=new ArrayList<User>();
        else spectators=null;
    }

    public boolean joinGame(User user,boolean spectate,int buyIn) throws CantSpeactateThisRoomException,
            GameIsFullException, BelowBuyInPolicyException {
        if(spectate){
            if(spectators == null)
                throw new CantSpeactateThisRoomException("Selected game can't be spectated due to it's settings.");
            else spectators.add(user);
        }
        else if (isFull())
            throw new GameIsFullException("Can't join game as player because it's full.");
        else if(buyIn<settings.getBuyInPolicy())
            throw new BelowBuyInPolicyException("Minimal buy-in is " + settings.getBuyInPolicy() + " ");
        // todo: maybe there is need to save he Game for User? mapping between players and buy-in for each ?
        players.add(new Player(user));
        return true;
    }

    public void startNewRound() throws NoMinimumAmountOfPlayersException {
        if(players.size()< settings.getPlayerRange().getLeft())
            throw new NoMinimumAmountOfPlayersException("Can't start round, minimal amount for a new round is "+
                    settings.getPlayerRange().getLeft()+", but currently only "+players.size() +" exist.");
        else{
            Round rnd=new Round();
            rounds.add(rnd);
            rnd.start();
        }
    }

    private boolean isFull(){
        return players.size() == settings.getPlayerRange().getRight();
    }
}
