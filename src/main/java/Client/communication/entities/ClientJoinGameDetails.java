package Client.communication.entities;

/**
 * Created by user on 13/05/2017.
 */
public class ClientJoinGameDetails {
    private String username;
    private String gameName;
    private boolean isSpectating;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isSpectating() {
        return isSpectating;
    }

    public void setSpectating(boolean spectating) {
        isSpectating = spectating;
    }
}
