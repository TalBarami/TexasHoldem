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
    private int dealerIndex;

    public Round(List<Player> players, GameSettings settings, int dealerIndex) {
        this.gameSettings = settings;
        this.activePlayers = players;
        this.dealer = new Dealer();
        this.dealerIndex = dealerIndex;
    }

    public void startRound() {
        dealer.dealCardsToPlayers(activePlayers);
    }

    public void notifyPlayerExited(Player p) {

    }
}
