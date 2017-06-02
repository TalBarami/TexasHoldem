package Server.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * Created by rotemwald on 31/05/17.
 */
@Service
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {
    private WebAgentSessionRegistry webAgentSessionRegistry;

    @Autowired
    public STOMPConnectEventListener(WebAgentSessionRegistry webAgentSessionRegistry) {
        this.webAgentSessionRegistry = webAgentSessionRegistry;
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        String agentId = sha.getNativeHeader("Login").get(0);
        String sessionId = sha.getSessionId();

        webAgentSessionRegistry.map.put(agentId, sessionId);
    }
}
