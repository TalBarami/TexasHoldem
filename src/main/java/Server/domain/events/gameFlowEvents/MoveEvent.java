package Server.domain.events.gameFlowEvents;

import Server.domain.game.GameActions;
import Server.domain.game.participants.Player;

/**
 * Created by user on 02/05/2017.
 */
public class MoveEvent extends GameEvent {
    private int amountToRaise;

    public MoveEvent(Player eventInitiator, GameActions eventAction, int amountToRaise) {
        super(eventInitiator,eventAction);
        this.amountToRaise = amountToRaise; // Should  be zero if unless eventAction is of type RAISE
    }

    public int getAmountToRaise() {
        return amountToRaise;
    }
}
