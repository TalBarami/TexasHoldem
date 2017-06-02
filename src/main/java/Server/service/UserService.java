package Server.service;

import Exceptions.ArgumentNotInBoundsException;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;
import Server.domain.system.GameCenter;
import Server.domain.user.User;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

import static Server.service.TexasHoldemService.verifyObjects;
import static Server.service.TexasHoldemService.verifyPositiveNumbers;
import static Server.service.TexasHoldemService.verifyStrings;

/**
 * Created by Tal on 05/05/2017.
 */
public class UserService {
    private GameCenter gameCenter;

    public UserService(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public void register(String username, String pass, String email, LocalDate date, BufferedImage img) throws InvalidArgumentException {
        verifyStrings(username, pass, email);
        verifyObjects(date);
        gameCenter.registerUser(username, pass, email, date, img);
    }

    public void deleteUser(String username) throws EntityDoesNotExistsException, InvalidArgumentException {
        verifyStrings(username);
        gameCenter.deleteUser(username);
    }

    public void login(String username,String pass) throws LoginException, InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username);
        gameCenter.login(username, pass);
    }

    public void logout(String username) throws InvalidArgumentException {
        verifyStrings(username);
        gameCenter.logout(username);
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(originalUserName, newUserName, pass, email);
        verifyObjects(date);
        gameCenter.editProfile(originalUserName, newUserName, pass, email, date);
    }

    public void deposit(String username, int amount) throws ArgumentNotInBoundsException, InvalidArgumentException, EntityDoesNotExistsException {
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
}
