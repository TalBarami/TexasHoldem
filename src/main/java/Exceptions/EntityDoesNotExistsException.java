package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tal on 12/04/2017.
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class EntityDoesNotExistsException extends GameException {
    public EntityDoesNotExistsException(String message){
        super(message);
    }
}
