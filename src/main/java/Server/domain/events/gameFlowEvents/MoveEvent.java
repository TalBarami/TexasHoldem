package Server.domain.events.gameFlowEvents;

import Server.domain.game.GameActions;
import Server.domain.game.participants.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by user on 02/05/2017.
 */

@Entity
@Table(name = "move_event")
@PrimaryKeyJoinColumn(name="system_event_id")
public class MoveEvent extends GameEvent {

    @Column(name = "amount_to_raise")
    private int amountToRaise;

    public MoveEvent(String creatorUserName, GameActions eventAction, int amountToRaise, String gameName) {
        super(creatorUserName,eventAction,gameName);
        this.amountToRaise = amountToRaise; // Should  be zero if unless eventAction is of type RAISE
    }

    public int getAmountToRaise() {
        return amountToRaise;
    }

    public void setAmountToRaise(int amountToRaise) {
        this.amountToRaise = amountToRaise;
    }
}
