package Client.domain;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.communication.SessionRequestHandler;
import Client.communication.UserRequestHandler;
import Client.communication.entities.ClientUserDetails;
import Client.communication.entities.ClientUserProfile;
import Client.notification.SubscriptionManager;
import TexasHoldem.common.SystemUtils;

import javax.security.auth.login.LoginException;
import java.util.concurrent.ExecutionException;

/**
 * Created by User on 14/05/2017.
 */
public class SessionManager {
    private static SessionManager instance;

    private ClientUserProfile user;
    private SessionRequestHandler sessionRequestHandler;
    private UserRequestHandler userRequestHandler;

    private SessionManager(){
        sessionRequestHandler = new SessionRequestHandler();
        userRequestHandler = new UserRequestHandler();
    }

    public static SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public void register(String username, String password, String email, String birthday, String localImagePath) throws InvalidArgumentException {
        verifyStrings(username, password, email, birthday, localImagePath);
        String[] date = birthday.split("/");
        ClientUserProfile profile = new ClientUserProfile(username, password, email, Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), -1, -1, -1, -1);
        userRequestHandler.requestUserProfileRegistration(profile);

        user = userRequestHandler.requestUserProfileEntity(username);

        // Subscribe to channel
        try {
            new SubscriptionManager().subscribe(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void editProfile(String newPassword, String newEmail, String newBirthday, String newImage) throws InvalidArgumentException, EntityDoesNotExistsException {
        int day, month, year;
        if(newPassword == null || newPassword.isEmpty())
            newPassword = user.getPassword();
        if(newEmail == null || newEmail.isEmpty())
            newEmail = user.getEmail();
        if(newBirthday == null || newBirthday.isEmpty()){
            day = user.getDayOfBirth();
            month = user.getMonthOfBirth();
            year = user.getYearOfBirth();
        } else{
            String[] date = newBirthday.split("/");
            day = Integer.parseInt(date[0]);
            month = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);
        }
        /*if(newImage == null || newImage.isEmpty())
            newImage = user.getImage();*/
        ClientUserProfile profile = new ClientUserProfile(user.getUsername(), newPassword, newEmail, day, month, year, user.getBalance(), user.getLeague(), user.getNumOfGamesPlayed(), user.getAmountEarnedInLeague());
        userRequestHandler.requestUserProfileUpdate(user.getUsername(), profile);

        user = userRequestHandler.requestUserProfileEntity(user.getUsername());
    }

    public void login(String username, String password) throws LoginException, EntityDoesNotExistsException, InvalidArgumentException {
        ClientUserDetails details = new ClientUserDetails(username, password);
        sessionRequestHandler.requestUserLogin(details);

        user = userRequestHandler.requestUserProfileEntity(username);
    }

    public void logout(String username) throws InvalidArgumentException {
        ClientUserDetails details = new ClientUserDetails(username, "");
        sessionRequestHandler.requestUserLogout(details);
        user = null;
    }

    public ClientUserProfile user(){
        return user;
    }

    void verifyStrings(String... strings) throws InvalidArgumentException {
        if(SystemUtils.hasNullOrEmptyOrSpecialChars(strings))
            throw new InvalidArgumentException("Null/Empty fields or invalid characters are not allowed.");
    }
}
