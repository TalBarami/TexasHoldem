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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SessionHandler {
    private static SessionHandler instance;
    private static Logger logger = LoggerFactory.getLogger(SessionHandler.class);

    private StompSession stompSession;
    private String ipAddress;

    private ClientUserProfile user;
    private SessionRequestHandler sessionRequestHandler;
    private UserRequestHandler userRequestHandler;

    private List<UserUpdateCallback> updateCallbacks;

    private SessionHandler(){
        sessionRequestHandler = new SessionRequestHandler();
        userRequestHandler = new UserRequestHandler();
        stompSession = null;

        updateCallbacks = new ArrayList<>();
    }

    public static SessionHandler getInstance(){
        if(instance == null){
            instance = new SessionHandler();
        }
        return instance;
    }

    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public void register(String username, String password, String email, Calendar birthday, String localImagePath) throws InvalidArgumentException {
        ClientUserProfile profile = new ClientUserProfile(username, password, email, birthday.get(Calendar.DAY_OF_MONTH) +1, birthday.get(Calendar.MONTH), birthday.get(Calendar.YEAR), -1, -1, -1, -1);
        logger.info("Registering: {}", profile);
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
        logger.info("Edit profile: {}", user);
        logger.info("New profile: {}", profile);
        userRequestHandler.requestUserProfileUpdate(user.getUsername(), profile);

        user = userRequestHandler.requestUserProfileEntity(user.getUsername());
    }

    public void login(String username, String password) throws LoginException, EntityDoesNotExistsException, InvalidArgumentException, ExecutionException, InterruptedException {
        ClientUserLoginDetails details = new ClientUserLoginDetails(username, password);
        logger.info("Sending login request: {}", details);
        sessionRequestHandler.requestUserLogin(details);

        user = userRequestHandler.requestUserProfileEntity(username);
        stompSession = SubscriptionManager.subscribe(user.getUsername());
        SubscriptionManager.getStompSessionHandler().setUserUpdateCallback(this::updateUserDetails);
    }

    public void logout(String username) throws InvalidArgumentException {
        ClientUserLoginDetails details = new ClientUserLoginDetails(username, "");
        logger.info("Sending logout request: {}", details);
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

    public void updateUserDetails(ClientUserProfile profile){
        logger.info("Received user update callback. new profile: {}", profile);
        user = profile;
        updateCallbacks.forEach(c -> c.execute(profile));
        logger.info("Successfully finished executing {} user callbacks.", updateCallbacks.size());
    }

    public void addUpdateCallback(UserUpdateCallback callback){
        updateCallbacks.add(callback);
    }

    public ClientStompSessionHandler getSessionHandler(){
        return SubscriptionManager.getStompSessionHandler();
    }

}
