package TexasHoldem.domain.game.leagues;

import TexasHoldem.domain.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hod on 11/04/2017.
 */
public class LeagueManager {
    private int criteriaToMovingLeague = 10;
    private int defaultLeagueForNewUsers = 1;
    private int maxLeague;
    private HashMap<Integer, List<User>> usersInLeague;

    public LeagueManager() {
        maxLeague = 1;
        usersInLeague = new HashMap<>();
    }

    public void addNewUserToLegue(User user){
        createNewLeagueIfNeed(defaultLeagueForNewUsers);
        usersInLeague.get(defaultLeagueForNewUsers).add(user);
        updateMaxLeagueIfNeed(defaultLeagueForNewUsers);
        user.setCurrLeague(defaultLeagueForNewUsers);
    }

    public void updateUserLeague(User user)
    {
        if(user.getAmountEarnedInLeague() >= criteriaToMovingLeague)
        {
            usersInLeague.get(user.getCurrLeague()).remove(user);

            int newLeagueForUser = user.getCurrLeague() + user.getAmountEarnedInLeague()/criteriaToMovingLeague;
            newLeagueForUser = checkIfLegalLeague(newLeagueForUser);
            createNewLeagueIfNeed(newLeagueForUser);
            usersInLeague.get(newLeagueForUser).add(user);
            user.setCurrLeague(newLeagueForUser);
            user.setAmountEarnedInLeague(0);

            updateMaxLeagueIfNeed(newLeagueForUser);
        }
    }

    private void updateMaxLeagueIfNeed(int leagueNumber) {
        if(maxLeague < leagueNumber)
            maxLeague = leagueNumber;
    }

    private void createNewLeagueIfNeed(int newLeagueForUser) {
        if(!usersInLeague.containsKey(newLeagueForUser))
            usersInLeague.put(newLeagueForUser, new ArrayList<User>());
    }

    public void removeUserFromLeague(User user){
        if(usersInLeague.containsKey(user.getCurrLeague())) {
            usersInLeague.get(user.getCurrLeague()).remove(user);
            user.setCurrLeague(0);
        }
    }

    private int checkIfLegalLeague(int newLeagueForUser) {
        return ((newLeagueForUser < 1) ? 1 : newLeagueForUser);
    }

    public boolean checkIfHasPermissions(User user){
        return usersInLeague.get(maxLeague).contains(user);
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
