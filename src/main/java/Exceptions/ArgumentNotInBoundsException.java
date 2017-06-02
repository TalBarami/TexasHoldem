package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
public class ArgumentNotInBoundsException extends GameException {
    public ArgumentNotInBoundsException(String message){
        super(message);
    }
}