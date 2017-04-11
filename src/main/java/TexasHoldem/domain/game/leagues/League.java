package TexasHoldem.domain.game.leagues;

import TexasHoldem.domain.users.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hod on 11/04/2017.
 */
public class League {
    private int criteriaToMovingLeague = 10;
    private int defaultLeagueForNewUsers = 1;
    private HashMap<Integer, List<User>> usersInLeague;

    public League(int criteriaToMovingLeague, int defaultLeagueForNewUsers) {
        this.criteriaToMovingLeague = criteriaToMovingLeague;
        this.defaultLeagueForNewUsers = defaultLeagueForNewUsers;
        usersInLeague = new HashMap<>();
    }

    public void addNewUserToLegue(User user){
        usersInLeague.get(defaultLeagueForNewUsers).add(user);
    }

    public int getCriteriaToMovingLeague() {
        return criteriaToMovingLeague;
    }

    public void setCriteriaToMovingLeague(int criteriaToMovingLeague) {
        this.criteriaToMovingLeague = criteriaToMovingLeague;
    }

    public int getDefaultLeagueForNewUsers() {
        return defaultLeagueForNewUsers;
    }

    public void setDefaultLeagueForNewUsers(int defaultLeagueForNewUsers) {
        this.defaultLeagueForNewUsers = defaultLeagueForNewUsers;
    }
}
