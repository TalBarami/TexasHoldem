package TexasHoldem.communication;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.common.Exceptions.LoginException;
import TexasHoldem.communication.entities.ClientUserDetails;
import TexasHoldem.communication.entities.ResponseMessage;
import TexasHoldem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by user on 12/05/2017.
 */
@CrossOrigin
@RestController
public class SessionController {
    private UserService service;

    @Autowired
    public SessionController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method=POST, value="/session")
    public ResponseMessage loginUser(@RequestBody ClientUserDetails loginDetails) throws LoginException, EntityDoesNotExistsException, InvalidArgumentException {
        String userName = loginDetails.getUsername();
        String password = loginDetails.getPassword();

        service.login(userName, password);
        return new ResponseMessage("Login succeeded", null);
    }

    @RequestMapping(method=DELETE, value="/session")
    public ResponseMessage logoutUser(@RequestBody ClientUserDetails logoutDetails) throws InvalidArgumentException {
        String userName = logoutDetails.getUsername();
        String password = logoutDetails.getPassword();

        // TODO :: Verify username and password
        service.logout(userName);
        return new ResponseMessage("Logout succeeded", null);
    }
}
