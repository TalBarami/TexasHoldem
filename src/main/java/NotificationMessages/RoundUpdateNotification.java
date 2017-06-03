package NotificationMessages;

import MutualJsonObjects.ClientCard;
import MutualJsonObjects.ClientPlayer;

import java.util.List;

/**
 * Created by user on 03/06/2017.
 */
public class RoundUpdateNotification extends Notification {
    private int currentPotSize;
    private String currentPlayerName;
    private List<ClientPlayer> currentPlayers;
    private List<ClientCard> currentOpenedCards;

    public RoundUpdateNotification(String recipientUserName, int currentPotSize, String currentPlayerName, List<ClientPlayer> currentPlayers, List<ClientCard> currentOpenedCards) {
        super(recipientUserName);
        this.currentPotSize = currentPotSize;
        this.currentPlayerName = currentPlayerName;
        this.currentPlayers = currentPlayers;
        this.currentOpenedCards = currentOpenedCards;
    }

    public RoundUpdateNotification() {
    }

    public int getCurrentPotSize() {
        return currentPotSize;
    }

    public void setCurrentPotSize(int currentPotSize) {
        this.currentPotSize = currentPotSize;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public void setCurrentPlayerName(String currentPlayerName) {
        this.currentPlayerName = currentPlayerName;
    }

    public List<ClientPlayer> getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(List<ClientPlayer> currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public List<ClientCard> getCurrentOpenedCards() {
        return currentOpenedCards;
    }

    public void setCurrentOpenedCards(List<ClientCard> currentOpenedCards) {
        this.currentOpenedCards = currentOpenedCards;
    }
}
