package TexasHoldem.common.Exceptions;

public class GameIsFullException extends Exception {
    public GameIsFullException(String message){
        super(message);
    }
}
