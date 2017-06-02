package Server.communication;

import MutualJsonObjects.ClientTransactionRequest;
import MutualJsonObjects.ClientUserProfile;
import MutualJsonObjects.ResponseMessage;
import Exceptions.ArgumentNotInBoundsException;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Server.communication.converters.UserClientUserProfileConverter;
import Server.domain.user.Transaction;
import Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by user on 12/05/2017.
 */
@CrossOrigin
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
