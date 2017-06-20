package NotificationMessages;

/**
 * Created by rotemwald on 02/06/17.
 */
public class Notification {
    private String recipientUserName;

    public Notification(String recipientUserName) {
        this.recipientUserName = recipientUserName;
    }

    public Notification() {
    }

    public String getRecipientUserName() {
        return recipientUserName;
    }

    public void setRecipientUserName(String recipientUserName) {
        this.recipientUserName = recipientUserName;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "recipientUserName='" + recipientUserName + '\'' +
                '}';
    }
}
