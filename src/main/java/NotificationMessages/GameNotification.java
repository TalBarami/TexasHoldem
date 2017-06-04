package NotificationMessages;

/**
 * Created by rotemwald on 04/06/17.
 */
public class GameNotification extends Notification {
    private String gameName;

    public GameNotification(String recipientUserName, String gameName) {
        super(recipientUserName);
        this.gameName = gameName;
    }

    public GameNotification() {
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
