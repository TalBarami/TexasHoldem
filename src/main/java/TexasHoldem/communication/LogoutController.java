package TexasHoldem.communication;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.communication.entities.ClientLoginDetails;
import TexasHoldem.communication.entities.ResponseMessage;
import TexasHoldem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * Created by user on 12/05/2017.
 */

@RestController
public class LogoutController {
    private UserService service;

    @Autowired
    public LogoutController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method=DELETE, value="/logout/{username}")
    public ResponseMessage logoutUser(@PathVariable("username") String userName) throws InvalidArgumentException {
        service.logout(userName);
        return new ResponseMessage("Logout succeeded", null);
    }
}
