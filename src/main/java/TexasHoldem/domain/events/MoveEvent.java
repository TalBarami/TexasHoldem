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
    private Player eventInitiator;
    private GameActions eventAction;
    private int amountToRaise;

    public MoveEvent(DateTime eventTime, Round round, Player eventInitiator, GameActions eventAction, int amountToRaise) {
        super(DateTime.now());
        this.round = round;
        this.eventInitiator = eventInitiator;
        this.eventAction = eventAction;
        this.amountToRaise = amountToRaise; // Should  be zero if unless eventAction is of type RAISE
    }

    public Round getRound() {
        return round;
    }

    public Player getEventInitiator() {
        return eventInitiator;
    }

    public GameActions getEventAction() {
        return eventAction;
    }

    public int getAmountToRaise() {
        return amountToRaise;
    }
}
