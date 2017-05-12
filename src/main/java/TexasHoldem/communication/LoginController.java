package TexasHoldem.communication;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.communication.entities.ClientLoginDetails;
import TexasHoldem.communication.entities.ResponseMessage;
import TexasHoldem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by user on 12/05/2017.
 */

@RestController
public class LoginController {
    private UserService service;

    @Autowired
    public LoginController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method=POST, value="/login")
    public ResponseMessage loginUser(@RequestBody ClientLoginDetails loginDetails) throws LoginException, EntityDoesNotExistsException, InvalidArgumentException {
        String userName = loginDetails.getUsername();
        String password = loginDetails.getPassword();

        service.login(userName, password);
        return new ResponseMessage("Login succeeded", null);
    }
}
