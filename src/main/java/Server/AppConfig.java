package Server;

import Server.notification.WebAgentSessionRegistry;
import Server.service.*;
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
    public WebAgentSessionRegistry webAgentSessionRegistry;
    private final int weekTime = 604800000;

    public AppConfig() {
        service = new TexasHoldemService();
        webAgentSessionRegistry = new WebAgentSessionRegistry();

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

    @Bean("SearchService")
    public SearchService getSearchServiceBean(){
        return service.searchService();
    }

    @Bean
    public StatisticsService getStatisticsServiceBean(){
        return service.statisticsService();
    }

    @Bean
    public WebAgentSessionRegistry getWebAgentSessionRegistryBean(){
        return webAgentSessionRegistry;
    }

    @Bean
    public SpringApplicationContext getSpringApplicationContextBean() {
        return new SpringApplicationContext();
    }
}
