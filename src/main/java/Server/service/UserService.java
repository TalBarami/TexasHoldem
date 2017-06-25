package Server.service;

import Exceptions.ArgumentNotInBoundsException;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;
import Server.domain.system.GameCenter;
import Server.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static Server.service.TexasHoldemService.*;

/**
 * Created by Tal on 05/05/2017.
 */
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private GameCenter gameCenter;

    public UserService(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public void register(String username, String pass, String email, LocalDate date, String img) throws InvalidArgumentException {
        logger.info("Received registration request: username={}, pass={}, email={}, date={}, img={}", username, pass, email, date, img);
        verifyStrings(username, pass, email);
        verifyObjects(date);
        gameCenter.registerUser(username, pass, email, date, img);
    }

    public void deleteUser(String username) throws EntityDoesNotExistsException, InvalidArgumentException {
        logger.info("Received deletion request: username={}", username);
        verifyStrings(username);
        gameCenter.deleteUser(username);
    }

    public void login(String username,String pass) throws LoginException, InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("Received login request: username={}, pass={}", username, pass);
        verifyStrings(username);
        gameCenter.login(username, pass);
    }

    public void logout(String username) throws InvalidArgumentException {
        logger.info("Received logout request: username={}", username);
        verifyStrings(username);
        gameCenter.logout(username);
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("Received edit profile request: original usernamme={}, new username={}, pass={}, email={}, date={}", originalUserName, newUserName, pass, email, date);
        verifyStrings(originalUserName, newUserName, email);
        verifyPassword(pass);
        verifyObjects(date);
        gameCenter.editProfile(originalUserName, newUserName, pass, email, date);
    }

    public void deposit(String username, int amount) throws ArgumentNotInBoundsException, InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("Received deposit request: original usernamme={}, amount={}", username, amount);
        verifyStrings(username);
        verifyPositiveNumbers(amount);
        gameCenter.depositMoney(username, amount);
    }

    public int getUserLeague(String username) throws InvalidArgumentException, EntityDoesNotExistsException {
        return getUser(username).getCurrLeague();
    }

    public User getUser(String username) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username);
        return gameCenter.getUser(username);
    }

    public List<String> getAllUserNames(){
        List<String> users = gameCenter.getAllUserNames();
        Collections.sort(users);
        return users;
    }
}
