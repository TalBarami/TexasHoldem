package TexasHoldem.domain.events;

import TexasHoldem.domain.game.GameActions;
import TexasHoldem.domain.game.Round;
import TexasHoldem.domain.game.participants.Player;
import org.joda.time.DateTime;

/**
 * Created by user on 02/05/2017.
 */
public class MoveEvent extends GameEvent {
    private Round round;
    private int amountToRaise;

    public MoveEvent(Round round, Player eventInitiator, GameActions eventAction, int amountToRaise) {
        super(eventInitiator,eventAction);
        this.round = round;

        this.amountToRaise = amountToRaise; // Should  be zero if unless eventAction is of type RAISE
    }

    public Round getRound() {
        return round;
    }

    public int getAmountToRaise() {
        return amountToRaise;
    }
}
