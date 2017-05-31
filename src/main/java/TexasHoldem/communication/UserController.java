package TexasHoldem.communication;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.communication.converters.UserClientUserProfileConverter;
import TexasHoldem.communication.entities.*;
import TexasHoldem.domain.user.Transaction;
import TexasHoldem.notification.UserNotificationController;
import TexasHoldem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by user on 12/05/2017.
 */

@RestController
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method=POST, value="/user/{username}")
    public ResponseMessage registerUser(@PathVariable("username") String userNameParam, @RequestBody ClientUserProfile user) throws InvalidArgumentException {
        String userName = user.getUsername();

        if (!userNameParam.equals(userName)) {
            throw new InvalidArgumentException("User name to register is not compatible with request data.");
        }

        String password = user.getPassword();
        String email = user.getEmail();
        LocalDate dateOfBirth = LocalDate.of(user.getYearOfBirth(), user.getMonthOfBirth(), user.getDayOfBirth());
        BufferedImage img = null;

        service.register(userName, password, email, dateOfBirth, img);
        return new ResponseMessage("Register succeeded", null);
    }

    @RequestMapping(method=PUT, value="/user/{username}")
    public ResponseMessage updateUser(@PathVariable("username") String oldUserName, @RequestBody ClientUserProfile user) throws InvalidArgumentException, EntityDoesNotExistsException {
        String userName = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        LocalDate dateOfBirth = LocalDate.of(user.getYearOfBirth(), user.getMonthOfBirth(), user.getDayOfBirth());
        BufferedImage img = null;

        // TODO :: Verify username and password
        service.editProfile(oldUserName, userName, password, email, dateOfBirth);
        return new ResponseMessage("Edit user profile succeeded", null);
    }

    @RequestMapping(method=PUT, value="/user/{username}/balance")
    public ResponseMessage updateUserBalance(@PathVariable("username") String username, @RequestBody ClientTransactionRequest transaction) throws EntityDoesNotExistsException, ArgumentNotInBoundsException, InvalidArgumentException {
        if (transaction.getAction() == Transaction.DEPOSIT) {
            service.deposit(username, transaction.getAmount());
        }

        // TODO :: Add withdraw support
        // TODO :: Verify username and password
        new UserNotificationController().sendMessageToUser("waldr");
        return new ResponseMessage("Transaction completed successfully", null);
    }

    @RequestMapping(method=DELETE, value="/user/{username}")
    public ResponseMessage updateUser(@PathVariable("username") String userName) throws InvalidArgumentException, EntityDoesNotExistsException {
        // TODO :: Verify username and password
        service.deleteUser(userName);
        return new ResponseMessage("Delete user profile succeeded", null);
    }

    @RequestMapping(method = GET, value="/user/{username}")
    public ResponseMessage getActiveGames(@PathVariable("username") String userName) throws InvalidArgumentException, EntityDoesNotExistsException {
        ClientUserProfile clientProfile = UserClientUserProfileConverter.convert(service.getUser(userName));
        return new ResponseMessage<ClientUserProfile>("User received successfully", clientProfile);
    }
}
