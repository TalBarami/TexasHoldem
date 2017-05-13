package TexasHoldem.domain.events.chatEvents;

import TexasHoldem.domain.events.SystemEvent;
import TexasHoldem.domain.game.chat.Message;
import TexasHoldem.domain.game.participants.Participant;

/**
 * Created by hod on 13/05/2017.
 */
public class WhisperEvent extends SystemEvent {
    Message content;
    Participant participantToSendTo;

    public WhisperEvent(Participant eventInitiator, Message content, Participant participantToSendTo) {
        super(eventInitiator);
        this.content = content;
        this.participantToSendTo = participantToSendTo;
    }

    public Message getContent() {
        return content;
    }

    public Participant getParticipantToSendTo() {
        return participantToSendTo;
    }
}
