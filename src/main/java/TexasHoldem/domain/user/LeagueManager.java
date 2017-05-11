package TexasHoldem.domain.user;

import TexasHoldem.domain.user.usersDistributions.DistributionAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hod on 11/04/2017.
 */
public class LeagueManager {
    public final static int defaultLeagueForNewUsers = 0; //represent unknown league can play in each game

    private int criteriaToMovingLeague = 10;
    private int numOfGamesNewPlayerNeedPlayToChangeLeague = 10;
    private int maxLeague;
    private List<User> userList;

    public LeagueManager(List<User> userList) {
        maxLeague = 0;
        this.userList = userList;
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

    public List<User> getAllUsersInList() {
        return userList;
    }

    public List<User> getUsersByLeague(int leagueNum) {
        List<User> usersInLeague = new ArrayList<>();
        for(User user : userList.values()){
            if(user.getCurrLeague() == leagueNum)
                usersInLeague.add(user);
        }
        return usersInLeague;
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
