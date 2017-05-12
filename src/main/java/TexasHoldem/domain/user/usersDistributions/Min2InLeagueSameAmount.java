package TexasHoldem.domain.user.usersDistributions;

import TexasHoldem.data.users.IUsersForDistributionAlgorithm;
import TexasHoldem.domain.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hod on 06/05/2017.
 */
public class Min2InLeagueSameAmount implements DistributionAlgorithm {
    private IUsersForDistributionAlgorithm userDbWindow;

    public Min2InLeagueSameAmount(IUsersForDistributionAlgorithm userDbWindow) {
        this.userDbWindow = userDbWindow;
    }

    @Override
    public void distribute(int maxLeague){
        int numOfUsersToCheck = userDbWindow.getAllUsersInList().size();
        if(numOfUsersToCheck == 1)
            return;

        int numOfLeagues = getNumOfLeagues(numOfUsersToCheck, maxLeague);
        int numOfUsersInEachLeague = numOfUsersToCheck/numOfLeagues;

        for(int i = maxLeague; i > maxLeague - numOfLeagues; i--){
            List<User> usersInLeague = userDbWindow.getUsersByLeague(i);
            int numOfUsersInCurrLeague = getNumOfUsersInCurrLeague(numOfUsersToCheck, numOfLeagues, numOfUsersInEachLeague);
            numOfUsersToCheck -= numOfUsersInCurrLeague;
            int difference = usersInLeague.size() - numOfUsersInCurrLeague;

            if(difference == 0)
                continue;
            else if(difference > 0)
                moveSomeUsersToLeague(usersInLeague, difference, i-1);
            else{
                usersInLeague = findUsersToMoveToThisLeague(Math.abs(difference), i-1);
                moveSomeUsersToLeague(usersInLeague, Math.abs(difference), i);
            }
        }
    }

    private int getNumOfLeagues(int numOfUsers, int maxLeague){
        int numOfLeagues = maxLeague + 1;
        int numOfUsersInEachLeague = numOfUsers/numOfLeagues;
        while(numOfUsersInEachLeague < 2)
        {
            numOfLeagues --;
            numOfUsersInEachLeague = numOfUsers/numOfLeagues;
        }
        return numOfLeagues;
    }

    private int getNumOfUsersInCurrLeague(int numOfUsers, int numOfLeagues, int numOfUsersInEachLeague) {
        //checks if this league can have one more player in it so numOfLeagus will stay the same
        if(numOfUsers > numOfUsersInEachLeague && (numOfUsers - (numOfUsersInEachLeague + 1))/numOfUsersInEachLeague == numOfLeagues-1)
            return numOfUsersInEachLeague+1;
        return numOfUsersInEachLeague;
    }

    private void moveSomeUsersToLeague(List<User> usersInLeague, int numToMove, int newLeagueNumber) {
        for(int i = 0; i < numToMove; i++){
            usersInLeague.get(i).setCurrLeague(newLeagueNumber);
        }
    }

    private List<User> findUsersToMoveToThisLeague(int numOfUsersTofind, int leagueToStartLooking) {
        List<User> usersToMove = new ArrayList<>();
        int leagueToLook = leagueToStartLooking;
        while (usersToMove.size() < numOfUsersTofind){
            usersToMove.addAll(userDbWindow.getUsersByLeague(leagueToLook));
            leagueToLook--;
        }
        return usersToMove;
    }
}
