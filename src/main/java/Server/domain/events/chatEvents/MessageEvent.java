package Server.domain.events.chatEvents;

import Server.domain.events.SystemEvent;
import Server.domain.game.chat.Message;
import Server.domain.game.participants.Participant;

/**
 * Created by hod on 12/05/2017.
 */
public class MessageEvent extends SystemEvent {
    Message content;

    public MessageEvent(Participant eventInitiator, Message content, String gameName) {
        super(eventInitiator, gameName);
        this.content = content;
    }

    public Message getContent() {
        return content;
    }

    public void setContent(Message content) {
        this.content = content;
    }
}