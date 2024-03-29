package Server.communication;

import MutualJsonObjects.ClientTransactionRequest;
import MutualJsonObjects.ClientUserProfile;
import MutualJsonObjects.ResponseMessage;
import Exceptions.ArgumentNotInBoundsException;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Server.communication.converters.UserClientUserProfileConverter;
import Server.domain.user.Transaction;
import Server.service.SessionManager;
import Server.service.UserService;
import org.apache.catalina.SessionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by user on 12/05/2017.
 */
@CrossOrigin
@RestController
public class UserController {
    private UserService service;
    private SessionManager manager;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
        this.manager = SessionManager.getInstance();
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
        String img = user.getImage();

        service.register(userName, password, email, dateOfBirth, img);
        return new ResponseMessage("Register succeeded", null);
    }

    @RequestMapping(method=PUT, value="/user/{username}")
    public ResponseMessage updateUser(@PathVariable("username") String oldUserName, @RequestBody ClientUserProfile user, @RequestHeader("SESSION_ID") String sessionID) throws InvalidArgumentException, EntityDoesNotExistsException {
        String userName = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        LocalDate dateOfBirth = LocalDate.of(user.getYearOfBirth(), user.getMonthOfBirth(), user.getDayOfBirth());
        String image = user.getImage();

        manager.validate(userName, sessionID);

        service.editProfile(oldUserName, userName, password, email, dateOfBirth, image);
        return new ResponseMessage("Edit user profile succeeded", null);
    }

    @RequestMapping(method=PUT, value="/user/{username}/balance")
    public ResponseMessage updateUserBalance(@PathVariable("username") String username, @RequestBody ClientTransactionRequest transaction, @RequestHeader("SESSION_ID") String sessionID) throws EntityDoesNotExistsException, ArgumentNotInBoundsException, InvalidArgumentException {
        manager.validate(username, sessionID);

        if (transaction.getAction() == Transaction.DEPOSIT) {
            service.deposit(username, transaction.getAmount());
        }

        return new ResponseMessage("Transaction completed successfully", null);
    }

    @RequestMapping(method=DELETE, value="/user/{username}")
    public ResponseMessage updateUser(@PathVariable("username") String userName) throws InvalidArgumentException, EntityDoesNotExistsException {
        service.deleteUser(userName);
        return new ResponseMessage("Delete user profile succeeded", null);
    }

    @RequestMapping(method = GET, value="/user/{username}")
    public ResponseMessage getUser(@PathVariable("username") String userName) throws InvalidArgumentException, EntityDoesNotExistsException {
        ClientUserProfile clientProfile = UserClientUserProfileConverter.convert(service.getUser(userName));
        return new ResponseMessage<ClientUserProfile>("User received successfully", clientProfile);
    }

    @RequestMapping(method = GET, value="/user")
    public ResponseMessage getAllUserNames() {
       List<String> users = service.getAllUserNames();
        return new ResponseMessage<>("All user name retrieved successfully.", users);
    }
}
