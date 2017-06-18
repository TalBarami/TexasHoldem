package Server.service;

import Exceptions.InvalidArgumentException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by RonenB on 6/17/2017.
 */
public class SessionManager {
    private Map<String, UUID> sessionMappings;

    private static SessionManager ourInstance = new SessionManager();

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
        sessionMappings = new HashMap<>();
    }

    public String buildSession(String userName){
        UUID id = UUID.randomUUID();
        sessionMappings.put(userName,id);

        return id.toString();
    }

    public void destroySession(String userName){
        sessionMappings.remove(userName);
    }

    public void validate(String userName, String sessionId) throws InvalidArgumentException {
        if(!sessionMappings.keySet().contains(userName)) {
            throw new InvalidArgumentException("There is no user '" + userName + "' in the system.");
        }
        if(!sessionMappings.get(userName).toString().equals(sessionId)){
            throw new InvalidArgumentException("Wrong user's session ID !");
        }
    }
}
