package Server.communication;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;
import MutualJsonObjects.ClientUserLoginDetails;
import MutualJsonObjects.ResponseMessage;
import Server.service.UserService;
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
    public ResponseMessage loginUser(@RequestBody ClientUserLoginDetails loginDetails) throws LoginException, EntityDoesNotExistsException, InvalidArgumentException {
        String userName = loginDetails.getUsername();
        String password = loginDetails.getPassword();

        service.login(userName, password);
        return new ResponseMessage("Login succeeded", null);
    }

    @RequestMapping(method=DELETE, value="/session")
    public ResponseMessage logoutUser(@RequestBody ClientUserLoginDetails logoutDetails) throws InvalidArgumentException {
        String userName = logoutDetails.getUsername();
        String password = logoutDetails.getPassword();

        // TODO :: Verify username and password
        service.logout(userName);
        return new ResponseMessage("Logout succeeded", null);
    }
}
