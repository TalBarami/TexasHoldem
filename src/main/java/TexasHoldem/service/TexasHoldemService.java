package TexasHoldem.service;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.common.SystemUtils;
import TexasHoldem.domain.system.GameCenter;

/**
 * Created by Tal on 12/04/2017.
 */
public class TexasHoldemService {
    private GameCenter gameCenter;
    private GameService gameService;
    private UserService userService;
    private SearchService searchService;
   // private LeagueService leagueService;

    public TexasHoldemService(){
        gameCenter = new GameCenter();
        gameService = new GameService(gameCenter);
        userService = new UserService(gameCenter);
        searchService = new SearchService(gameCenter);
     //   leagueService = new LeagueService(gameCenter);
    }

    public GameService gameService(){
        return gameService;
    }

    public UserService userService(){
        return userService;
    }

    public SearchService searchService(){
        return searchService;
    }

   /* public LeagueService leagueService(){
        return leagueService;
    }
    */

    static void verifyStrings(String... strings) throws InvalidArgumentException {
        if(SystemUtils.hasNullOrEmptyOrSpecialChars(strings))
            throw new InvalidArgumentException("Null/Empty fields or invalid characters are not allowed.");
    }

    static void verifyObjects(Object... objects) throws InvalidArgumentException{
        for(Object o : objects){
            if(o == null)
                throw new InvalidArgumentException("Null fields are not allowed.");
        }
    }

    static void verifyPositiveNumbers(int... ints) throws InvalidArgumentException{
        if(!SystemUtils.arePositive(ints))
            throw new InvalidArgumentException("Numeric values must be positive.");
    }

    public GameCenter getGameCenter() {
        return gameCenter;
    }
}
