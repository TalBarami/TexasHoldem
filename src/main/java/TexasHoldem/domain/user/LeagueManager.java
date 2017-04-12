package TexasHoldem.domain.user;

import TexasHoldem.domain.user.User;

/**
 * Created by hod on 11/04/2017.
 */
public class LeagueManager {
    private int criteriaToMovingLeague = 10;
    private int defaultLeagueForNewUsers = 1;
    private int maxLeague;

    public LeagueManager() {
        maxLeague = 1;
    }

    public void addNewUserToLegue(User user){
        putUserInLeague(user, defaultLeagueForNewUsers);
    }

    public void updateUserLeague(User user)
    {
        if(user.getAmountEarnedInLeague() >= criteriaToMovingLeague)
        {
            int newLeagueForUser = user.getCurrLeague() + user.getAmountEarnedInLeague()/criteriaToMovingLeague;
            newLeagueForUser = checkIfLegalLeague(newLeagueForUser);
            putUserInLeague(user, newLeagueForUser);
        }
    }

    private void putUserInLeague(User user, int newLeagueForUser) {
        user.setCurrLeague(newLeagueForUser);
        user.setAmountEarnedInLeague(0);
        updateMaxLeagueIfNeed(newLeagueForUser);
    }

    private void updateMaxLeagueIfNeed(int leagueNumber) {
        if(maxLeague < leagueNumber)
            maxLeague = leagueNumber;
    }

    public void removeUserFromLeague(User user){
        user.setCurrLeague(0);
    }

    public void moveUserToLeague(User userToMove, int newLeague){
        putUserInLeague(userToMove, newLeague);
    }

    private int checkIfLegalLeague(int newLeagueForUser) {
        return ((newLeagueForUser < 1) ? 1 : newLeagueForUser);
    }

    public boolean checkIfHasPermissions(User user){
        return user.getCurrLeague() == maxLeague;
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
