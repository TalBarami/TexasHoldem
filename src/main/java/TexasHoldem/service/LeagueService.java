package TexasHoldem.service;

import TexasHoldem.common.Exceptions.NoPermissionException;
import TexasHoldem.domain.system.GameCenter;

/**
 * Created by Tal on 05/05/2017.
 */
public class LeagueService {
    private GameCenter gameCenter;

    public LeagueService(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public void setDefaultLeague(String admin, int league) throws NoPermissionException {
        gameCenter.setDefaultLeague(admin, league);
    }

    public void setUserLeague(String admin, String username, int league) throws NoPermissionException {
        gameCenter.setUserLeague(admin, username, league);
    }

    public void setLeagueCriteria(String username, int league) throws NoPermissionException {
        gameCenter.setLeagueCriteria(username, league);
    }
}
