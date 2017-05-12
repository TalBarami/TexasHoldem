package TexasHoldem.domain.user;

import TexasHoldem.domain.user.usersDistributions.DistributionAlgorithm;

import java.util.List;

/**
 * Created by hod on 11/04/2017.
 */
public class LeagueManager {
    public final static int defaultLeagueForNewUsers = 0; //represent unknown league can play in each game

    private int criteriaToMovingLeague = 10;
    private int numOfGamesNewPlayerNeedPlayToChangeLeague = 10;
    private Integer maxLeague;

    public LeagueManager() {
        maxLeague = 0;
    }

    public void addNewUserToLeague(User user){
        putUserInLeague(user, defaultLeagueForNewUsers);
    }

    //can also relegate
    public void updateUserLeague(User user)
    {
        if(isLeagueNeedsToUpdate(user))
        {
            int newLeagueForUser = user.getCurrLeague() + user.getAmountEarnedInLeague()/criteriaToMovingLeague;
            newLeagueForUser = checkIfLegalLeague(newLeagueForUser);
            putUserInLeague(user, newLeagueForUser);
        }
    }

    private boolean isLeagueNeedsToUpdate(User user) {
        return (Math.abs(user.getAmountEarnedInLeague()) >= criteriaToMovingLeague && user.getCurrLeague() != 0) ||
                (user.getCurrLeague() == 0 && user.getNumOfGamesPlayed() == numOfGamesNewPlayerNeedPlayToChangeLeague);
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

    public void updateMaxLeagueUserDeleted(User userToDel, List<User> allUsersInList) {
        synchronized (maxLeague) {
            if(userToDel.getCurrLeague() == maxLeague){
                maxLeague = findMaxLeagueInSystem(allUsersInList);
            }
        }
    }

    private Integer findMaxLeagueInSystem(List<User> allUsersInList) {
        int max = 0;
        for(User user: allUsersInList){
            if(user.getCurrLeague() > maxLeague)
                max = user.getCurrLeague();
        }

        return max;
    }

    public void redistributeUsersInLeagues(DistributionAlgorithm da) {
        da.distribute(maxLeague);
    }

    public void removeUserFromLeague(User user){
        user.setCurrLeague(0);
    }

    private int checkIfLegalLeague(int newLeagueForUser) {
        return ((newLeagueForUser < 1) ? 1 : newLeagueForUser);
    }

    public int getCriteriaToMovingLeague() {
        return criteriaToMovingLeague;
    }

    public int getDefaultLeagueForNewUsers() {
        return defaultLeagueForNewUsers;
    }

    public int getNumOfGamesNewPlayerNeedPlayToChangeLeague() {
        return numOfGamesNewPlayerNeedPlayToChangeLeague;
    }

    public void setMaxLeague(int maxLeague) {
        this.maxLeague = maxLeague;
    }

    public int getMaxLeague() {
        return maxLeague;
    }

    //    public void moveUserToLeague(User userToMove, int newLeague){
//        putUserInLeague(userToMove, newLeague);
//    }
//    public boolean checkIfHasPermissions(User user){
//        return user.getCurrLeague() == maxLeague;
//    }
//    public void setCriteriaToMovingLeague(int criteriaToMovingLeague) {
//        this.criteriaToMovingLeague = criteriaToMovingLeague;
//    }
//    public void setDefaultLeagueForNewUsers(int defaultLeagueForNewUsers) {
//        this.defaultLeagueForNewUsers = defaultLeagueForNewUsers;
//    }
}
