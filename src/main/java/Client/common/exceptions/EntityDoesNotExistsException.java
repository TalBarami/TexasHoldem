package Client.common.exceptions;

import TexasHoldem.common.Exceptions.GameException;

/**
 * Created by Tal on 12/04/2017.
 */
public class EntityDoesNotExistsException extends GameException {
    public EntityDoesNotExistsException(String message){
        super(message);
    }
}
