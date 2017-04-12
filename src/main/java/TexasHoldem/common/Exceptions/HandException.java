package TexasHoldem.common.Exceptions;

/**
 * Created by Tal on 08/04/2017.
 */
public class HandException extends GameException {
    public HandException(){
        super("Error while calculating hand value");
    }
}
