package Server.service;

import Exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by RonenB on 6/17/2017.
 */
public class SessionManager {
    private static Logger logger = LoggerFactory.getLogger(SessionManager.class);
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
        logger.info("New session id['{}'] is genereated for the user: {}",id.toString(), userName);
        sessionMappings.put(userName,id);

        return id.toString();
    }

    public void destroySession(String userName){
        logger.info("Session id for the user '{}' has deleted.", userName);
        sessionMappings.remove(userName);
    }

    public void validate(String userName, String sessionId) throws InvalidArgumentException {
        logger.info("New session id validation request with the following information: [user name: {}, session id: {}].",userName, sessionId);
        if(!sessionMappings.keySet().contains(userName)) {
            throw new InvalidArgumentException("There is no user '" + userName + "' in the system.");
        }
        if(!sessionMappings.get(userName).toString().equals(sessionId)){
            throw new InvalidArgumentException("Wrong user's session ID !");
        }
    }
}
