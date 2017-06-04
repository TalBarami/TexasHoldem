package NotificationMessages;

/**
 * Created by rotemwald on 02/06/17.
 */
public class ChatNotification extends GameNotification {
    private String senderUserName;
    private String messageContent;
    private boolean isPrivate;

    public ChatNotification(String recipientUserName, String senderUserName, String messageContent, String gameName, boolean isPrivate) {
        super(recipientUserName, gameName);
        this.senderUserName = senderUserName;
        this.messageContent = messageContent;
        this.isPrivate = isPrivate;
    }

    public ChatNotification() {
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
