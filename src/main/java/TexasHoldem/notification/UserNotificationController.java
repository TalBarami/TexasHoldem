package TexasHoldem.notification;

import TexasHoldem.communication.entities.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;

/**
 * Created by rotemwald on 31/05/17.
 */
public class UserNotificationController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private WebAgentSessionRegistry webAgentSessionRegistry;

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    public void sendMessageToUser(String userName) {
        String sessionId = webAgentSessionRegistry.map.get(userName);
        messagingTemplate.convertAndSendToUser(sessionId, "/queue", new ResponseMessage<Object>("Hello there", null), createHeaders(sessionId));
    }
}
