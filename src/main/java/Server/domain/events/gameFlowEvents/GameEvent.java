package Server.domain.events.gameFlowEvents;

import Server.domain.events.SystemEvent;
import Server.domain.game.GameActions;
import Server.domain.game.participants.Participant;

import javax.persistence.*;

/**
 * Created by user on 02/05/2017.
 */

@Entity
@Table(name = "gameevent")
public class GameEvent extends SystemEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameevent_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gameaction")
    private GameActions eventAction;

    public GameEvent(Participant eventInitiator, GameActions action, String gameName) {
        super(eventInitiator, gameName);
        this.eventAction = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventAction(GameActions eventAction) {
        this.eventAction = eventAction;
    }

    public GameActions getEventAction(){return eventAction;}


}
