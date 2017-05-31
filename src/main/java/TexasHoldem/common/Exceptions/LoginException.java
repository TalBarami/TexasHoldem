package TexasHoldem.common.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by user on 12/05/2017.
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class LoginException extends Exception {
    public LoginException(String message) {
        super(message);
    }
}
