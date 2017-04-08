package TexasHoldem.domain.system;

import TexasHoldem.domain.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RonenB on 4/8/2017.
 */
public class GameCenter {
    private List<Game> activeGames;
    public GameCenter() {
        activeGames=new ArrayList<>();
    }
}
