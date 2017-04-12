package TexasHoldem.domain.user;

import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        leagueManager.addNewUserToLegue(user);
        assertEquals(leagueManager.getDefaultLeagueForNewUsers(), user.getCurrLeague());
        leagueManager.removeUserFromLeague(user);
        assertEquals(0, user.getCurrLeague());
    }

    @Test
    public void updateUserLeague() throws Exception {
        leagueManager.addNewUserToLegue(user);
        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague());
        leagueManager.updateUserLeague(user);
        assertEquals(leagueManager.getDefaultLeagueForNewUsers() + 1, user.getCurrLeague());
        assertEquals(0, user.getAmountEarnedInLeague());

        int currLeague = user.getCurrLeague();
        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague() - 1);
        leagueManager.updateUserLeague(user);
        assertEquals(currLeague, user.getCurrLeague());

        leagueManager.removeUserFromLeague(user);
    }

    @Test
    public void checkIfHasPermissions() throws Exception {
        leagueManager.addNewUserToLegue(user);
        user.setAmountEarnedInLeague(leagueManager.getCriteriaToMovingLeague());
        leagueManager.updateUserLeague(user);
        assertTrue(leagueManager.checkIfHasPermissions(user));
        User user2 = new User("Rotem", "1234", "waldr@gmail.com", null, null);
        leagueManager.addNewUserToLegue(user2);
        assertFalse(leagueManager.checkIfHasPermissions(user2));
    }

    @Test
    public void moveUserToLeague() throws Exception {
        leagueManager.moveUserToLeague(user, 10);
        assertEquals(10, user.getCurrLeague());
    }

    @Test
    public void setCriteriaToMovingLeague() throws Exception {
        int currentCriteria = leagueManager.getCriteriaToMovingLeague();
        leagueManager.setCriteriaToMovingLeague(150);
        assertEquals(150, leagueManager.getCriteriaToMovingLeague());
        leagueManager.setCriteriaToMovingLeague(currentCriteria);
    }

    @Test
    public void setDefaultLeagueForNewUsers() throws Exception {
        int currentDefaultLeague = leagueManager.getDefaultLeagueForNewUsers();
        leagueManager.setDefaultLeagueForNewUsers(150);
        assertEquals(150, leagueManager.getDefaultLeagueForNewUsers());
        leagueManager.setCriteriaToMovingLeague(currentDefaultLeague);
    }

}