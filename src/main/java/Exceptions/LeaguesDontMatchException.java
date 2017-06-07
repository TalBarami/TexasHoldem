package Exceptions;

public class LeaguesDontMatchException extends GameException {
    public LeaguesDontMatchException(String message){
        super(message);
    }
}