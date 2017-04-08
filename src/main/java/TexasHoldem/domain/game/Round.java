package TexasHoldem.domain.game;

import java.util.List;

/**
 * Created by Hod and Rotem on 05/04/2017.
 */
public class Round {
    private boolean isRoundActive;
    private GameSettings gameSettings;
    private List<Player> activePlayers;
    private Dealer dealer;

    public Round(List<Player> players, GameSettings settings) {
        this.gameSettings = settings;
        this.activePlayers = players;
        this.dealer = new Dealer();
    }

    public void startRound() {

    }

    public void notifyPlayerExited(Player p) {

    }
}
