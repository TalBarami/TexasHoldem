package Client.notification;

import MutualJsonObjects.ResponseMessage;
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
                return ResponseMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.err.println(((ResponseMessage<Object>)o).getMessage());
            }
        });
    }
}
