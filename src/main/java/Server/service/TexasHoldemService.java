package Server.service;

import Exceptions.InvalidArgumentException;
import Server.common.SystemUtils;
import Server.domain.system.GameCenter;

import java.time.LocalDate;

/**
 * Created by Tal on 12/04/2017.
 */
public class TexasHoldemService {
    private GameCenter gameCenter;
    private GameService gameService;
    private UserService userService;
    private SearchService searchService;
    private StatisticsService statisticsService;
   // private LeagueService leagueService;

    public TexasHoldemService(){
        gameCenter = new GameCenter();
        gameService = new GameService(gameCenter);
        userService = new UserService(gameCenter);
        searchService = new SearchService(gameCenter);
        statisticsService = new StatisticsService(gameCenter);
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

    public StatisticsService statisticsService(){return statisticsService;}

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
