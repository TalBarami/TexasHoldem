package Client.domain;

import Client.domain.callbacks.UserUpdateCallback;
import Client.notification.ClientStompSessionHandler;
import Client.notification.SubscriptionManager;
import MutualJsonObjects.ClientUserLoginDetails;
import MutualJsonObjects.ClientUserProfile;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;

import Client.communication.SessionRequestHandler;
import Client.communication.UserRequestHandler;
import Server.common.SystemUtils;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SessionManager {
    private static SessionManager instance;

    private StompSession stompSession;
    private String ipAddress;

    private ClientUserProfile user;
    private SessionRequestHandler sessionRequestHandler;
    private UserRequestHandler userRequestHandler;

    private List<UserUpdateCallback> updateCallbacks;

    private SessionManager(){
        sessionRequestHandler = new SessionRequestHandler();
        userRequestHandler = new UserRequestHandler();
        stompSession = null;

        updateCallbacks = new ArrayList<>();
    }

    public static SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public void register(String username, String password, String email, String birthday, String localImagePath) throws InvalidArgumentException {
        verifyStrings(username, password, email, birthday, localImagePath);
        String[] date = birthday.split("/");
        ClientUserProfile profile = new ClientUserProfile(username, password, email, Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), -1, -1, -1, -1);
        userRequestHandler.requestUserProfileRegistration(profile);

        user = userRequestHandler.requestUserProfileEntity(username);
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
        ClientUserProfile profile = new ClientUserProfile(user.getUsername(), newPassword, newEmail, day, month, year, user.getBalance(), user.getCurrLeague(), user.getNumOfGamesPlayed(), user.getAmountEarnedInLeague());
        userRequestHandler.requestUserProfileUpdate(user.getUsername(), profile);

        user = userRequestHandler.requestUserProfileEntity(user.getUsername());
    }

    public void login(String username, String password) throws LoginException, EntityDoesNotExistsException, InvalidArgumentException, ExecutionException, InterruptedException {
        ClientUserLoginDetails details = new ClientUserLoginDetails(username, password);
        sessionRequestHandler.requestUserLogin(details);

        user = userRequestHandler.requestUserProfileEntity(username);
        stompSession = SubscriptionManager.subscribe(user.getUsername());
        SubscriptionManager.getStompSessionHandler().setUserUpdateCallback(this::updateUserDetails);
    }

    public void logout(String username) throws InvalidArgumentException {
        ClientUserLoginDetails details = new ClientUserLoginDetails(username, "");
        sessionRequestHandler.requestUserLogout(details);
        user = null;

        if (stompSession != null && stompSession.isConnected()) {
            stompSession.disconnect();
        }
        stompSession = null;
    }

    public ClientUserProfile user(){
        return user;
    }

    void verifyStrings(String... strings) throws InvalidArgumentException {
        if(SystemUtils.hasNullOrEmptyOrSpecialChars(strings))
            throw new InvalidArgumentException("Null/Empty fields or invalid characters are not allowed.");
    }

    public void updateUserDetails(ClientUserProfile profile){
        user = profile;
        updateCallbacks.parallelStream().forEach(c -> c.execute(profile));
    }

    public void addUpdateCallback(UserUpdateCallback callback){
        updateCallbacks.add(callback);
    }

    public ClientStompSessionHandler getSessionHandler(){
        return SubscriptionManager.getStompSessionHandler();
    }
}
