package TexasHoldem.domain.events;

import TexasHoldem.domain.game.GameActions;
import TexasHoldem.domain.game.participants.Participant;

import org.joda.time.DateTime;

/**
 * Created by user on 02/05/2017.
 */
public class GameEvent {
    private DateTime eventTime;
    private Participant eventInitiator;
    private GameActions eventAction;

    public GameEvent(Participant eventInitiator, GameActions action) {
        this.eventTime = DateTime.now();
        this.eventInitiator = eventInitiator;
        this.eventAction = action;
    }

    public DateTime getEventTime() {
        return eventTime;
    }
    public Participant getEventInitiator(){return eventInitiator;}
    public GameActions getEventAction(){return eventAction;}
}
