package TexasHoldem.common.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tal on 12/04/2017.
 */

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class GameException extends Exception {
    public GameException(String message){
        super(message);
    }
}
