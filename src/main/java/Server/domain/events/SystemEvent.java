package Server.domain.events;

import Server.domain.game.participants.Participant;
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

    @Column(name = "creator_user_name")
    private String creatorUserName;

    @Column(name = "game_name")
    private String gameName;

    public SystemEvent(String creatorUserName, String gameName) {
        this.eventTime = DateTime.now();
        this.creatorUserName = creatorUserName;
        this.gameName = gameName;
    }

    public SystemEvent() {
    }

    public DateTime getEventTime() {
        return eventTime;
    }
    public String getCreatorUserName(){return creatorUserName;}

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setCreatorUserName(String newCreatorUserName)
    {
        this.creatorUserName = newCreatorUserName;
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
