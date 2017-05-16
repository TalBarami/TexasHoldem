package TexasHoldem.communication.entities;

/**
 * Created by אחיעד on 13/05/2017.
 */
public class ClientGameRequest {
    private String username;
    private String gamename;
    private int action;
    private int amount;
    private boolean spectating;
    private String messageContent;

    public String getMassage() {
        return messageContent;
    }

    public void setMassage(String newMassage) {
        this.messageContent = newMassage;
    }

    public boolean getSpectating() {
        return spectating;
    }

    public void setSpectating(boolean spectating) {
        this.spectating = spectating;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
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
}
