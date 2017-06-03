package Client.notification;

import Client.domain.SessionManager;
import NotificationMessages.MessageNotification;
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
        session.subscribe("/user/queue/move", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return PlayMoveNotification.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                // TODO :: Call callback
            }
        });

        session.subscribe("/user/queue/message", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return MessageNotification.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                // TODO :: Call callback
            }
        });

        session.subscribe("/user/queue/profile", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return UserProfileUpdateNotification.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                SessionManager.getInstance().updateUserDetails(((UserProfileUpdateNotification)o).getClientUserProfile());
            }
        });
    }
}
