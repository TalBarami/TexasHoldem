package Server.domain.events.gameFlowEvents;

import Server.domain.events.SystemEvent;
import Server.domain.game.GameActions;
import Server.domain.game.participants.Participant;

/**
 * Created by user on 02/05/2017.
 */
public class GameEvent extends SystemEvent {
    private GameActions eventAction;

    public GameEvent(Participant eventInitiator, GameActions action) {
        super(eventInitiator);
        this.eventAction = action;
    }

    public GameActions getEventAction(){return eventAction;}
}
