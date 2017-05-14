package Client.common.exceptions;

import TexasHoldem.common.Exceptions.GameException;

public class ArgumentNotInBoundsException extends GameException {
    public ArgumentNotInBoundsException(String message){
        super(message);
    }
}