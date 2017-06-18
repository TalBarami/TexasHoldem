package Client.communication;

/**
 * Created by RonenB on 18/06/2017.
 */
public class SessionStorage {
    private String sessionID;

    private static SessionStorage ourInstance = new SessionStorage();

    public static SessionStorage getInstance() {
        return ourInstance;
    }

    private SessionStorage() {
    }

    public void setSessionID(String id){
        this.sessionID = id;
    }

    public String getSessionId(){
        return sessionID;
    }
}
