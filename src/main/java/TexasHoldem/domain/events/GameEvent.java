package TexasHoldem.domain.events;

import org.joda.time.DateTime;

/**
 * Created by user on 02/05/2017.
 */
public abstract class GameEvent {
    private DateTime eventTime;

    public GameEvent(DateTime eventTime) {
        this.eventTime = eventTime;
    }

    public DateTime getEventTime() {
        return eventTime;
    }
}
