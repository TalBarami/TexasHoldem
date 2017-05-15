package TexasHoldem.communication.entities;

import java.util.Set;

/**
 * Created by rotemwald on 15/05/17.
 */
public class ClientPlayer {
    private String playerName;
    private Set<ClientCard> playerCards;
    private int chipAmount;

    public ClientPlayer() {
    }

    public ClientPlayer(String playerName, Set<ClientCard> playerCards, int chipAmount) {
        this.playerName = playerName;
        this.playerCards = playerCards;
        this.chipAmount = chipAmount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Set<ClientCard> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Set<ClientCard> playerCards) {
        this.playerCards = playerCards;
    }

    public int getChipAmount() {
        return chipAmount;
    }

    public void setChipAmount(int chipAmount) {
        this.chipAmount = chipAmount;
    }
}
