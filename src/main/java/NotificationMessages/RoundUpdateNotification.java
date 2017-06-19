package NotificationMessages;

import MutualJsonObjects.ClientCard;
import MutualJsonObjects.ClientPlayer;

import java.util.List;

/**
 * Created by user on 03/06/2017.
 */
public class RoundUpdateNotification extends GameNotification {
    private int currentPotSize;
    private String currentPlayerName;
    private List<ClientPlayer> currentPlayers;
    private List<ClientCard> currentOpenedCards;
    private List<ClientPlayer> winnerPlayers;
    private boolean isFinished;

    public RoundUpdateNotification(String recipientUserName, String gameName, int currentPotSize, String currentPlayerName, List<ClientPlayer> currentPlayers, List<ClientCard> currentOpenedCards, List<ClientPlayer> winnerPlayers, boolean isFinished) {
        super(recipientUserName, gameName);
        this.currentPotSize = currentPotSize;
        this.currentPlayerName = currentPlayerName;
        this.currentPlayers = currentPlayers;
        this.currentOpenedCards = currentOpenedCards;
        this.winnerPlayers = winnerPlayers;
        this.isFinished = isFinished;
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

    public List<ClientPlayer> getWinnerPlayers() {
        return winnerPlayers;
    }

    public void setWinnerPlayers(List<ClientPlayer> winnerPlayers) {
        this.winnerPlayers = winnerPlayers;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return "RoundUpdateNotification{" +
                "currentPotSize=" + currentPotSize +
                ", currentPlayerName='" + currentPlayerName + '\'' +
                ", currentPlayers=" + currentPlayers +
                ", currentOpenedCards=" + currentOpenedCards +
                ", winnerPlayers=" + winnerPlayers +
                ", isFinished=" + isFinished +
                '}';
    }
}
