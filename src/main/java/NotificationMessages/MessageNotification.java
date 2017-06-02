package NotificationMessages;

/**
 * Created by rotemwald on 02/06/17.
 */
public class MessageNotification extends Notification {
    private String senderUserName;
    private String messageContent;

    public MessageNotification(String recipientUserName, String senderUserName, String messageContent) {
        super(recipientUserName);
        this.senderUserName = senderUserName;
        this.messageContent = messageContent;
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
}
