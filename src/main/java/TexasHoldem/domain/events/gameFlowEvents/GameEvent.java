package TexasHoldem.domain.events.gameFlowEvents;

import TexasHoldem.domain.events.SystemEvent;
import TexasHoldem.domain.game.GameActions;
import TexasHoldem.domain.game.participants.Participant;

import org.joda.time.DateTime;

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
