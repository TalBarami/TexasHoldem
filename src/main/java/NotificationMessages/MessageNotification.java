package NotificationMessages;

/**
 * Created by rotemwald on 02/06/17.
 */
public class MessageNotification extends Notification {
    private String senderUserName;
    private String messageContent;
    private String gameName;
    private boolean isPrivate;

    public MessageNotification(String recipientUserName, String senderUserName, String messageContent, String gameName, boolean isPrivate) {
        super(recipientUserName);
        this.senderUserName = senderUserName;
        this.messageContent = messageContent;
        this.gameName = gameName;
        this.isPrivate = isPrivate;
    }

    public MessageNotification() {
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
