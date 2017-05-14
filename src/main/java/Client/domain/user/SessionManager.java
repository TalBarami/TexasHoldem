package Client.domain.user;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.communication.SessionRequestHandler;
import Client.communication.UserRequestHandler;
import Client.communication.entities.ClientUserDetails;
import Client.communication.entities.ClientUserProfile;
import org.joda.time.DateTime;

import javax.security.auth.login.LoginException;

/**
 * Created by User on 14/05/2017.
 */
public class SessionManager {
    private SessionRequestHandler sessionRequestHandler;
    private ClientUserProfile user;
    private UserRequestHandler userRequestHandler;

    public SessionManager(){
        sessionRequestHandler = new SessionRequestHandler();
        userRequestHandler = new UserRequestHandler();
    }

    public ClientUserProfile register(String username, String password, String email, String birthday, String localImagePath) throws InvalidArgumentException {
        String[] date = birthday.split("/");
        ClientUserProfile profile = new ClientUserProfile(username, password, email, Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        userRequestHandler.requestUserProfileRegistration(profile);

        user = userRequestHandler.requestUserProfileEntity(username);

        return user;
    }

    public ClientUserProfile editProfile(String newPassword, String newEmail, String n){
        return null;
    }

    public ClientUserProfile login(String username, String password) throws LoginException, EntityDoesNotExistsException, InvalidArgumentException {
        ClientUserDetails details = new ClientUserDetails(username, password);
        sessionRequestHandler.requestUserLogin(details);

        // FIXME: init() user and return it.
        return user;
    }

    public void logout(String username) throws InvalidArgumentException {
        ClientUserDetails details = new ClientUserDetails(username, "");
        sessionRequestHandler.requestUserLogout(details);
        user = null;
    }

    public ClientUserProfile getProfile(){
        return user;
    }
}
