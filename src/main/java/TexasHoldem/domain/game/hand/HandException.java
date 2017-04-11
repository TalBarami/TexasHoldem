package TexasHoldem.domain.game.hand;

/**
 * Created by Tal on 08/04/2017.
 */
public class HandException extends RuntimeException {
    public HandException(){
        super("Error while calculating hand value");
    }
}
