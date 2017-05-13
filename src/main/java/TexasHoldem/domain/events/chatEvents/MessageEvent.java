package TexasHoldem.domain.events.chatEvents;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.domain.events.SystemEvent;
import TexasHoldem.domain.game.chat.Message;
import TexasHoldem.domain.game.participants.Participant;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.game.participants.Spectator;

/**
 * Created by hod on 12/05/2017.
 */
public class MessageEvent extends SystemEvent {
    Message content;

    public MessageEvent(Participant eventInitiator, Message content) {
        super(eventInitiator);
        this.content = content;
    }

    public Message getContent() {
        return content;
    }
}
