package TexasHoldem;

import TexasHoldem.service.GameService;
import TexasHoldem.service.SearchService;
import TexasHoldem.service.TexasHoldemService;
import TexasHoldem.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 12/05/2017.
 */
@Configuration
public class AppConfig {
    public TexasHoldemService service;
    private final int weekTime = 604800000;

    public AppConfig() {
        service = new TexasHoldemService();
        Timer t = new Timer();
        // This task is scheduled to run every week
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                service.getGameCenter().redistributeUsersInLeagues();
            }
        }, weekTime, weekTime);
    }

    @Bean
    public UserService getUserServiceBean(){
        return service.userService();
    }

    @Bean
    public GameService getGameServiceBean(){
        return service.gameService();
    }

    @Bean
    public SearchService getSearchServiceBean(){
        return service.searchService();
    }
}
