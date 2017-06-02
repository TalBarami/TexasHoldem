package Client.notification;

import Client.domain.SessionManager;
import NotificationMessages.MessageNotification;
import NotificationMessages.Notification;
import NotificationMessages.PlayMoveNotification;
import NotificationMessages.UserProfileUpdateNotification;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

/**
 * Created by rotemwald on 31/05/17.
 */
public class ClientStompSessionHandler extends StompSessionHandlerAdapter {
    public ClientStompSessionHandler() {
        super();
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/user/queue", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return Notification.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.err.println("received");
                Notification notification = (Notification)o;

                if (notification instanceof UserProfileUpdateNotification) {
                    SessionManager.getInstance().updateUserDetails(((UserProfileUpdateNotification)o).getClientUserProfile());
                } else if (notification instanceof MessageNotification) {
                    // Call callback which handles MessageNotification
                } else if (notification instanceof PlayMoveNotification) {
                    // Call callback which handles PlayMoveNotification
                }
            }
        });
    }
}
