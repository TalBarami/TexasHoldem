package MutualJsonObjects;

import java.util.Set;

/**
 * Created by rotemwald on 15/05/17.
 */
public class ClientPlayer {
    private String playerName;
    private Set<ClientCard> playerCards;
    private int chipAmount;
    private int lastBetSinceCardOpen;
    private String image;

    public ClientPlayer() {
    }

    public ClientPlayer(String playerName, Set<ClientCard> playerCards, int chipAmount, int lastBetSinceCardOpen, String image) {
        this.playerName = playerName;
        this.playerCards = playerCards;
        this.chipAmount = chipAmount;
        this.lastBetSinceCardOpen = lastBetSinceCardOpen;
        this.image = image;
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

    public int getLastBetSinceCardOpen() {
        return lastBetSinceCardOpen;
    }

    public void setLastBetSinceCardOpen(int lastBetSinceCardOpen) {
        this.lastBetSinceCardOpen = lastBetSinceCardOpen;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ClientPlayer{" +
                "playerName='" + playerName + '\'' +
                ", playerCards=" + playerCards +
                ", chipAmount=" + chipAmount +
                ", lastBetSinceCardOpen=" + lastBetSinceCardOpen +
                '}';
    }
}
