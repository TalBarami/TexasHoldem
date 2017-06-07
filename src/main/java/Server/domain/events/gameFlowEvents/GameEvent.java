package Server.domain.events.gameFlowEvents;

import Server.domain.events.SystemEvent;
import Server.domain.game.GameActions;
import Server.domain.game.participants.Participant;

import javax.persistence.*;

/**
 * Created by user on 02/05/2017.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "game_event")
@PrimaryKeyJoinColumn(name="system_event_id")
public class GameEvent extends SystemEvent {

    @Enumerated(EnumType.STRING)
    @Column(name = "game_action")
    private GameActions eventAction;

    public GameEvent(Participant eventInitiator, GameActions action, String gameName) {
        super(eventInitiator, gameName);
        this.eventAction = action;
    }

    public void setEventAction(GameActions eventAction) {
        this.eventAction = eventAction;
    }

    public GameActions getEventAction(){return eventAction;}


}
