package MutualJsonObjects;

import Server.domain.game.GameActions;

/**
 * Created by rotemwald on 17/06/17.
 */
public class ClientGameEvent {
    private String eventInitiatorUserName;
    private GameActions action;

    public ClientGameEvent(String eventInitiatorUserName, GameActions action) {
        this.eventInitiatorUserName = eventInitiatorUserName;
        this.action = action;
    }

    public ClientGameEvent() {
    }

    public String getEventInitiatorUserName() {
        return eventInitiatorUserName;
    }

    public void setEventInitiatorUserName(String eventInitiatorUserName) {
        this.eventInitiatorUserName = eventInitiatorUserName;
    }

    public GameActions getAction() {
        return action;
    }

    public void setAction(GameActions action) {
        this.action = action;
    }
}
