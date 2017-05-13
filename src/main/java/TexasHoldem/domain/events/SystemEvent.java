package TexasHoldem.domain.events;

import TexasHoldem.domain.game.participants.Participant;
import org.joda.time.DateTime;

/**
 * Created by hod on 12/05/2017.
 */
public class SystemEvent {
    private DateTime eventTime;
    private Participant eventInitiator;

    public SystemEvent(Participant eventInitiator) {
        this.eventTime = DateTime.now();
        this.eventInitiator = eventInitiator;
    }

    public DateTime getEventTime() {
        return eventTime;
    }
    public Participant getEventInitiator(){return eventInitiator;}
}
