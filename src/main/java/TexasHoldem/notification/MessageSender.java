package TexasHoldem.notification;

import NotificationMessages.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Service;

/**
 * Created by rotemwald on 31/05/17.
 */
@Service
public class MessageSender {
    private SimpMessageSendingOperations messagingTemplate;
    private WebAgentSessionRegistry webAgentSessionRegistry;

    @Autowired
    public MessageSender(SimpMessageSendingOperations messagingTemplate, WebAgentSessionRegistry webAgentSessionRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.webAgentSessionRegistry = webAgentSessionRegistry;
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    public void sendNotification(Notification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());
        messagingTemplate.convertAndSendToUser(sessionId, "/queue", notification, createHeaders(sessionId));
    }
}
