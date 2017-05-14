package TexasHoldem.communication;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.communication.converters.UserClientUserProfileConverter;
import TexasHoldem.communication.entities.ClientGameDetails;
import TexasHoldem.communication.entities.ClientGamePreferences;
import TexasHoldem.communication.entities.ClientUserProfile;
import TexasHoldem.communication.entities.ResponseMessage;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GamePolicy;
import TexasHoldem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

    @RequestMapping(method=DELETE, value="/user/{username}")
    public ResponseMessage updateUser(@PathVariable("username") String userName) throws InvalidArgumentException, EntityDoesNotExistsException {
        // TODO :: Verify username and password
        service.deleteUser(userName);
        return new ResponseMessage("Delete user profile succeeded", null);
    }

    @RequestMapping(method = GET, value="/user/{username}")
    public ResponseMessage getActiveGames(@PathVariable("username") String userName) throws InvalidArgumentException {
        ClientUserProfile clientProfile = UserClientUserProfileConverter.convert(service.getUser(userName));
        return new ResponseMessage<ClientUserProfile>("User received successfully", clientProfile);
    }
}
