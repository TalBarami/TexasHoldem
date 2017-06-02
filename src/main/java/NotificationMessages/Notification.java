package NotificationMessages;

import org.joda.time.DateTime;

/**
 * Created by rotemwald on 02/06/17.
 */
public class Notification {
    private String recipientUserName;
    private DateTime time;

    public Notification(String recipientUserName) {
        this.recipientUserName = recipientUserName;
        time = DateTime.now();
    }

    public Notification() {
    }

    public String getRecipientUserName() {
        return recipientUserName;
    }

    public void setRecipientUserName(String recipientUserName) {
        this.recipientUserName = recipientUserName;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }
}
