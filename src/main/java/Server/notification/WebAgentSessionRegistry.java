package Server.notification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rotemwald on 31/05/17.
 */
public class WebAgentSessionRegistry {
    public Map<String, String> map;

    public WebAgentSessionRegistry() {
        map = new ConcurrentHashMap<>();
    }
}
