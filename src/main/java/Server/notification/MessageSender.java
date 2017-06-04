package Server.notification;

import NotificationMessages.ChatNotification;
import NotificationMessages.PlayMoveNotification;
import NotificationMessages.RoundUpdateNotification;
import NotificationMessages.UserProfileUpdateNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Service;

/**
 * Created by rotemwald on 31/05/17.
 */
@Service("MessageSender")
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

    public void sendPlayMoveNotification(PlayMoveNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/move", notification, createHeaders(sessionId));
        }
    }

    public void sendMessageNotification(ChatNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/message", notification, createHeaders(sessionId));
        }
    }

    public void sendUserProfileUpdateNotification(UserProfileUpdateNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/profile", notification, createHeaders(sessionId));
        }
    }

    public void sendRoundUpdateNotification(RoundUpdateNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/round", notification, createHeaders(sessionId));
        }
    }
}
