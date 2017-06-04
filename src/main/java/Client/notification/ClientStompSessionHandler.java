package Client.notification;

import Client.domain.callbacks.ChatUpdateCallback;
import Client.domain.callbacks.GameUpdateCallback;
import Client.domain.callbacks.MoveUpdateCallback;
import Client.domain.callbacks.UserUpdateCallback;
import NotificationMessages.ChatNotification;
import NotificationMessages.PlayMoveNotification;
import NotificationMessages.RoundUpdateNotification;
import NotificationMessages.UserProfileUpdateNotification;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rotemwald on 31/05/17.
 */
public class ClientStompSessionHandler extends StompSessionHandlerAdapter {
    private Map<String, GameUpdateCallback> gameUpdateCallbackMap;
    private Map<String, ChatUpdateCallback> chatUpdateCallbackMap;
    private Map<String, MoveUpdateCallback> moveUpdateCallbackMap;
    private UserUpdateCallback userUpdateCallback;

    public ClientStompSessionHandler() {
        super();

        gameUpdateCallbackMap = new HashMap<>();
        chatUpdateCallbackMap = new HashMap<>();
        moveUpdateCallbackMap = new HashMap<>();
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
                PlayMoveNotification notification = (PlayMoveNotification)o;
                String gameName = notification.getGameName();
                moveUpdateCallbackMap.get(gameName).execute(notification.getMoveList());
            }
        });

        session.subscribe("/user/queue/message", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return ChatNotification.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                ChatNotification notification = (ChatNotification)o;
                String gameName = notification.getGameName();
                chatUpdateCallbackMap.get(gameName).execute(notification);
            }
        });

        session.subscribe("/user/queue/profile", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return UserProfileUpdateNotification.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                userUpdateCallback.execute(((UserProfileUpdateNotification)o).getClientUserProfile());
            }
        });

        session.subscribe("/user/queue/round", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return RoundUpdateNotification.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                // TODO :: Add RoundUpdateNotification callback
            }
        });
    }

    public void addGameUpdateCallBack(String gameName, GameUpdateCallback callback) {
        gameUpdateCallbackMap.put(gameName, callback);
    }

    public void addChatUpdateCallback(String gameName, ChatUpdateCallback callback) {
        chatUpdateCallbackMap.put(gameName, callback);
    }

    public void addMoveUpdateCallback(String gameName, MoveUpdateCallback callback) {
        moveUpdateCallbackMap.put(gameName, callback);
    }

    public void setUserUpdateCallback(UserUpdateCallback userUpdateCallback) {
        this.userUpdateCallback = userUpdateCallback;
    }
}
