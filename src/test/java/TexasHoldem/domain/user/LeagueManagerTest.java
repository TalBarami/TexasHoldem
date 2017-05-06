package TexasHoldem.domain.user;

import TexasHoldem.domain.game.Round;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by hod on 11/04/2017.
 */
public class LeagueManagerTest {
    User user;
    LeagueManager leagueManager;
    @Before
    public void setUp() throws Exception {
        user = new User("hod", "1234", "hod.bub@gmail.com", null, null);
        leagueManager = new LeagueManager();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addAndRemoveNewUserToLegue() throws Exception {
        leagueManager.addNewUserToLeague(user);
        assertEquals(leagueManager.getDefaultLeagueForNewUsers(), user.getCurrLeague());
        leagueManager.removeUserFromLeague(user);
        assertEquals(0, user.getCurrLeague());
    }

    @Test
    public void updateUserLeague() throws Exception {
        leagueManager.addNewUserToLeague(user);
        user.setCurrLeague(1);
        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague());
        leagueManager.updateUserLeague(user);
        assertEquals(2, user.getCurrLeague());
        assertEquals(0, user.getAmountEarnedInLeague());

        leagueManager.removeUserFromLeague(user);
    }

    @Test
    public void updateUserLeague2LeaguesAtOnce() throws Exception {
        leagueManager.addNewUserToLeague(user);
        user.setCurrLeague(1);
        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague()*2);
        leagueManager.updateUserLeague(user);
        assertEquals(3, user.getCurrLeague());
        assertEquals(0, user.getAmountEarnedInLeague());

        leagueManager.removeUserFromLeague(user);
    }

    @Test
    public void updateUserLeagueNotUpdatedLeague() throws Exception {
        leagueManager.addNewUserToLeague(user);
        user.setCurrLeague(1);

        int currLeague = user.getCurrLeague();
        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague() - 1);
        leagueManager.updateUserLeague(user);
        assertEquals(currLeague, user.getCurrLeague());

        leagueManager.removeUserFromLeague(user);
    }

    @Test
    public void updateUserLeagueRelegation2Leagues() throws Exception {
        leagueManager.addNewUserToLeague(user);
        user.setCurrLeague(3);
        user.setAmountEarnedInLeague(- leagueManager.getCriteriaToMovingLeague()*2);
        leagueManager.updateUserLeague(user);
        assertEquals(1, user.getCurrLeague());

        leagueManager.removeUserFromLeague(user);
    }

    @Test
    public void updateUserLeagueNoUpdateNewUserFirstGame() throws Exception {
        leagueManager.addNewUserToLeague(user);
        user.updateGamesPlayed(); //played only one game
        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague()*2);
        leagueManager.updateUserLeague(user);
        assertEquals(0, user.getCurrLeague());

        leagueManager.removeUserFromLeague(user);
    }

    @Test
    public void updateUserLeagueUpdateNewUserPlayedEnough() throws Exception {
        leagueManager.addNewUserToLeague(user);

        for(int i = 0; i < leagueManager.getNumOfGamesNewPlayerNeedPlayToChangeLeague(); i++)
            user.updateGamesPlayed();

        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague()*2);
        leagueManager.updateUserLeague(user);
        assertEquals(2, user.getCurrLeague());

        leagueManager.removeUserFromLeague(user);
    }

    @Test
    public void updateUserLeagueUpdateNewUserPlayedEnoughMovingToFirstLeague() throws Exception {
        leagueManager.addNewUserToLeague(user);

        for(int i = 0; i < leagueManager.getNumOfGamesNewPlayerNeedPlayToChangeLeague(); i++)
            user.updateGamesPlayed();

        user.setAmountEarnedInLeague(0);
        leagueManager.updateUserLeague(user);
        assertEquals(1, user.getCurrLeague());

        leagueManager.removeUserFromLeague(user);
    }
}