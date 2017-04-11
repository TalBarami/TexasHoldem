package TexasHoldem.data.games;

import TexasHoldem.domain.game.Game;

import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * Created by RotemWald on 05/04/2017.
 */
public interface IGames {
    boolean addGame(Game game);
    LinkedList<Game> searchGame(Predicate<Game> p);
    LinkedList<Game> getActiveGames();
}
