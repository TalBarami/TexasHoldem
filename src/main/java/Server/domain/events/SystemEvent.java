package Server.domain.events;

import Server.domain.game.participants.Participant;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by hod on 12/05/2017.
 */
@Entity
@Table(name = "system_event")
@Inheritance(strategy = InheritanceType.JOINED)
public class SystemEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_event_id")
    private int id;

    @Column(name = "creation_date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime eventTime;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant eventInitiator;

    @Column(name = "game_name")
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

    public void setEventInitiator(Participant newParticipant)
    {
        this.eventInitiator = newParticipant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventTime(DateTime eventTime) {
        this.eventTime = eventTime;
    }
}
