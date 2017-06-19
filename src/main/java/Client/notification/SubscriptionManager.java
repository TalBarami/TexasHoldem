package Client.notification;

import Client.domain.SessionHandler;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by rotemwald on 31/05/17.
 */
public class SubscriptionManager {
    private static ClientStompSessionHandler stompSessionHandler;

    public static StompSession subscribe(String userName) throws ExecutionException, InterruptedException {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String serverAddress = SessionHandler.getInstance().getIpAddress();
        String url = "ws://" + serverAddress + "/subscribe";

        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add("Login", userName);

        stompSessionHandler = new ClientStompSessionHandler();

        return stompClient.connect(url, webSocketHttpHeaders, stompHeaders, stompSessionHandler).get();
    }

    public static ClientStompSessionHandler getStompSessionHandler(){
        return stompSessionHandler;
    }
}
