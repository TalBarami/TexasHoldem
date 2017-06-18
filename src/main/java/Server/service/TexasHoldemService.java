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

        try {
            userService.register("user1", "123", "tal@gmail", LocalDate.now(), null);
            userService.register("user2", "123", "ronen@gmail", LocalDate.now().plusDays(2), null);
            userService.register("user3", "123", "achiad@gmail", LocalDate.now().plusDays(3), null);
            userService.register("user4", "123", "hod@gmail", LocalDate.now().plusDays(4), null);
            userService.register("user5", "123", "rotem@gmail", LocalDate.now().plusDays(5), null);
        } catch(InvalidArgumentException e){
            System.out.println("ERROR! INVALID ARGUMENT!");
        }
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
