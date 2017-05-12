package TexasHoldem.communication;

import TexasHoldem.domain.user.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Created by user on 12/05/2017.
 */

@RestController
public class TestController {
    @RequestMapping("/user")
    public User getUser() {
        return new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
    }
}
