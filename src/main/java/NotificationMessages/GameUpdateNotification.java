package NotificationMessages;

import MutualJsonObjects.ClientGameDetails;
import Server.domain.game.GameActions;

/**
 * Created by rotemwald on 06/06/17.
 */
public class GameUpdateNotification extends GameNotification {
    private GameActions action;
    private String gameActionInitiator;
    private ClientGameDetails gameDetails;

    public GameUpdateNotification(String recipientUserName, String gameName, GameActions action, String gameActionInitiator, ClientGameDetails gameDetails) {
        super(recipientUserName, gameName);
        this.action = action;
        this.gameActionInitiator = gameActionInitiator;
        this.gameDetails = gameDetails;
    }

    public GameUpdateNotification() {
    }

    public GameActions getAction() {
        return action;
    }

    public void setAction(GameActions action) {
        this.action = action;
    }

    public String getGameActionInitiator() {
        return gameActionInitiator;
    }

    public void setGameActionInitiator(String gameActionInitiator) {
        this.gameActionInitiator = gameActionInitiator;
    }

    public ClientGameDetails getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(ClientGameDetails gameDetails) {
        this.gameDetails = gameDetails;
    }
}
