package MutualJsonObjects;

/**
 * Created by אחיעד on 13/05/2017.
 */
public class ClientGameRequest {
    private String username;
    private String gameName;
    private int action;
    private int amount;
    private boolean spectating;
    private String messageContent;
    private String recipientUserName;

    public String getMessage() {
        return messageContent;
    }

    public void setMessage(String newMessage) {
        this.messageContent = newMessage;
    }

    public boolean getSpectating() {
        return spectating;
    }

    public void setSpectating(boolean spectating) {
        this.spectating = spectating;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRecipientUserName() {
        return recipientUserName;
    }

    public void setRecipientUserName(String recipientUserName) {
        this.recipientUserName = recipientUserName;
    }

    @Override
    public String toString() {
        return "ClientGameRequest{" +
                "username='" + username + '\'' +
                ", gameName='" + gameName + '\'' +
                ", action=" + action +
                ", amount=" + amount +
                ", spectating=" + spectating +
                ", messageContent='" + messageContent + '\'' +
                ", recipientUserName='" + recipientUserName + '\'' +
                '}';
    }
}
