package TexasHoldem.data.games;

import TexasHoldem.domain.game.Game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * Created by RotemWald on 05/04/2017.
 */
public class Games implements IGames {
    private HashMap<Integer, Game> _games;
    int _newGameId;

    public Games() {
        _games = new HashMap<Integer, Game>();
        _newGameId = 0;
    }

    public boolean addGame(Game game) {
        int gameId = getNewGameId();

        game.setGameId(gameId); // FIXME: Using temporary method
        _games.put(gameId, game);

        return true;
    }

    public LinkedList<Game> searchGame(Predicate<Game> p) {
        LinkedList<Game> result = new LinkedList<Game>();

        for (Game g : _games.values()) {
            if (p.test(g)) {
                result.add(g);
            }

        }
        return result;
    }

    public LinkedList<Game> getActiveGames() {
        return searchGame(Game::isActive); // FIXME: Using temporary method.
    }

    private int getNewGameId() {
        return _newGameId++;
    }
}