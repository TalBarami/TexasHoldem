package Server.communication.converters;

import MutualJsonObjects.ClientGameEvent;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.game.GameActions;

/**
 * Created by rotemwald on 17/06/17.
 */
public class GameEventClientGameEvent {
    public static ClientGameEvent convert(GameEvent e) {
        String gameEventInitiator = e.getEventInitiator().getUser().getUsername();
        GameActions action = e.getEventAction();

        return new ClientGameEvent(gameEventInitiator, action);
    }
}
