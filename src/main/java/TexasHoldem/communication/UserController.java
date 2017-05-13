package TexasHoldem.communication;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.communication.entities.ClientUser;
import TexasHoldem.communication.entities.ResponseMessage;
import TexasHoldem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.time.LocalDate;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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

    @RequestMapping(method=POST, value="/user")
    public ResponseMessage registerUser(@RequestBody ClientUser user) throws InvalidArgumentException {
        String userName = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        LocalDate dateOfBirth = LocalDate.of(user.getYearOfBirth(), user.getMonthOfBirth(), user.getDayOfBirth());
        BufferedImage img = null;

        service.register(userName, password, email, dateOfBirth, img);
        return new ResponseMessage("Register succeeded", null);
    }

    @RequestMapping(method=PUT, value="/user/{username}")
    public ResponseMessage updateUser(@PathVariable("username") String oldUserName, @RequestBody ClientUser user) throws InvalidArgumentException, EntityDoesNotExistsException {
        String userName = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        LocalDate dateOfBirth = LocalDate.of(user.getYearOfBirth(), user.getMonthOfBirth(), user.getDayOfBirth());
        BufferedImage img = null;

        service.editProfile(oldUserName, userName, password, email, dateOfBirth);
        return new ResponseMessage("Edit user profile succeeded", null);
    }

    @RequestMapping(method=DELETE, value="/user/{username}")
    public ResponseMessage updateUser(@PathVariable("username") String userName) throws InvalidArgumentException, EntityDoesNotExistsException {
        service.deleteUser(userName);
        return new ResponseMessage("Delete user profile succeeded", null);
    }
}
