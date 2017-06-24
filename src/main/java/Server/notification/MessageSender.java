package Server.notification;

import NotificationMessages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(MessageSender.class);

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
            logger.info("PlayMoveNotification about game {} has been sent to {}", notification.getGameName(), notification.getRecipientUserName());
        }
    }

    public void sendMessageNotification(ChatNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/message", notification, createHeaders(sessionId));

            if (!notification.isPrivate()) {
                logger.info("Public ChatNotification to room {} has been sent to {} from {}", notification.getGameName(), notification.getRecipientUserName(), notification.getSenderUserName());
            } else {
                logger.info("Private ChatNotification to room {} has been sent to {} from {}", notification.getGameName(), notification.getRecipientUserName(), notification.getSenderUserName());
            }
        }
    }

    public void sendUserProfileUpdateNotification(UserProfileUpdateNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/profile", notification, createHeaders(sessionId));
            logger.info("UserProfileUpdateNotification has been sent to {}", notification.getRecipientUserName());
        }
    }

    public void sendRoundUpdateNotification(RoundUpdateNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/round", notification, createHeaders(sessionId));
            logger.info("RoundUpdateNotification about game {} has been sent to {}", notification.getGameName(), notification.getRecipientUserName());
        }
    }

    public void sendGameUpdateNotification(GameUpdateNotification notification) {
        String sessionId = webAgentSessionRegistry.map.get(notification.getRecipientUserName());

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/game", notification, createHeaders(sessionId));
            logger.info("GameUpdateNotification about game {} has been sent to {}", notification.getGameName(), notification.getRecipientUserName());
        }
    }
}
