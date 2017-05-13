package TexasHoldem.common.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class InvalidArgumentException extends GameException {
    public InvalidArgumentException(String message){
        super(message);
    }
}
