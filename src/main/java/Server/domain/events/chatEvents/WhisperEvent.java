package Server.domain.events.chatEvents;

import Server.domain.events.SystemEvent;
import Server.domain.game.chat.Message;
import Server.domain.game.participants.Participant;

/**
 * Created by hod on 13/05/2017.
 */
public class WhisperEvent extends SystemEvent {
    Message content;
    Participant participantToSendTo;

    public WhisperEvent(Participant eventInitiator, Message content, Participant participantToSendTo, String gameName) {
        super(eventInitiator, gameName);
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
