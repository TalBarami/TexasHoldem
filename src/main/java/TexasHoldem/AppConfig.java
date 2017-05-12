package TexasHoldem;

import TexasHoldem.service.TexasHoldemService;
import TexasHoldem.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by user on 12/05/2017.
 */
@Configuration
public class AppConfig {
    public TexasHoldemService service;

    public AppConfig() {
        service = new TexasHoldemService();
    }

    @Bean
    public UserService getUserServiceBean(){
        return service.userService();
    }
}
