package Server.domain.events;

import Server.domain.game.participants.Participant;
import org.joda.time.DateTime;

/**
 * Created by hod on 12/05/2017.
 */
public class SystemEvent {
    private DateTime eventTime;
    private Participant eventInitiator;
    private String gameName;

    public SystemEvent(Participant eventInitiator, String gameName) {
        this.eventTime = DateTime.now();
        this.eventInitiator = eventInitiator;
        this.gameName = gameName;
    }

    public DateTime getEventTime() {
        return eventTime;
    }
    public Participant getEventInitiator(){return eventInitiator;}

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
