package TexasHoldem.domain.user.usersDistributions;

import TexasHoldem.domain.system.GameCenter;
import TexasHoldem.domain.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hod on 06/05/2017.
 */
public class Min2InLeagueSameAmount implements DistributionAlgorithm {
    private GameCenter gameCenter;

    public Min2InLeagueSameAmount(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public void distibute(int maxLeague){
        //TODO :: get game center as singletone
        int numOfUsersToCheck = gameCenter.getAllUsersInList().size();
        if(numOfUsersToCheck == 1)
            return;

        int numOfLeagues = getNumOfLeagues(numOfUsersToCheck, maxLeague);
        int numOfUsersInEachLeague = numOfUsersToCheck/numOfLeagues;

        for(int i = maxLeague; i > maxLeague - numOfLeagues; i--){
            List<User> usersInLeague = gameCenter.getUsersByLeague(i);
            int numOfUsersInCurrLeague = getNumOfUsersInCurrLeague(numOfUsersToCheck, numOfLeagues, numOfUsersInEachLeague);
            numOfUsersToCheck -= numOfUsersInCurrLeague;
            int diffrence = usersInLeague.size() - numOfUsersInCurrLeague;

            if(diffrence == 0)
                continue;
            else if(diffrence > 0)
                moveSomeUsersToLeague(usersInLeague, diffrence, i-1);
            else{
                usersInLeague = findUsersToMoveToThisLeague(Math.abs(diffrence), i-1);
                moveSomeUsersToLeague(usersInLeague, Math.abs(diffrence), i);
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
        if((numOfUsers - (numOfUsersInEachLeague + 1))/numOfUsersInEachLeague == numOfLeagues-1)
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
            usersToMove.addAll(gameCenter.getUsersByLeague(leagueToLook));
            leagueToLook--;
        }
        return usersToMove;
    }
}
