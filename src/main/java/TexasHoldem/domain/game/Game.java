package TexasHoldem.domain.game;

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

    public boolean joinGame(User user,boolean spectate){
        if(spectate){
            if(spectators == null) return false; // TODO: exception maybe? cant spectate becuase game canot be spectated
            else spectators.add(user);
        }
        else if (isFull()) return false;//Todo : game is full exception maybe?
        return true;
    }

    public boolean startNewRound(){
        if(players.size()< settings.getPlayerRange().getLeft())
            return false; // TODO: sure ? exception maybe?
        else{
            Round rnd=new Round();
            rounds.add(rnd);
            rnd.start();
            return true; // TODO: no need, change signautre maybe to void and throw exception in the first condition leg.
        }
    }

    private boolean isFull(){
        return players.size() == settings.getPlayerRange().getRight();
    }
}
