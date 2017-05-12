package TexasHoldem.IntegrationTests;

import TexasHoldem.domain.system.GameCenter;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.usersDistributions.DistributionAlgorithm;
import TexasHoldem.domain.user.usersDistributions.Min2InLeagueSameAmount;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by hod on 12/05/2017.
 */
public class LeagueManagerDistributionAlgoTests {
        GameCenter gameCenter;
        DistributionAlgorithm distribute;
        LeagueManager leagueManager;

        @Before
        public void setUp() throws Exception {
            gameCenter = new GameCenter();
            distribute = new Min2InLeagueSameAmount(gameCenter.getUserDbWindowForDistributionAlgorithm());
            leagueManager = gameCenter.getLeagueManager();
        }

        @Test
        public void distribute_2NewUsersShouldStayThere() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(0, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(0, gameCenter.getUser("hodbub").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
        }

        @Test
        public void distribute_2Users2Leagues() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.getUser("hodbub").setCurrLeague(1);

            leagueManager.setMaxLeague(1);
            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(1, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(1, gameCenter.getUser("hodbub").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
        }

        @Test
        public void distribute_3Users2Leagues() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.getUser("hodbub").setCurrLeague(1);

            leagueManager.setMaxLeague(3);
            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(3, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(3, gameCenter.getUser("hodbub").getCurrLeague());
            assertEquals(3, gameCenter.getUser("achiadg").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
            gameCenter.deleteUser("achiadg");
        }

        @Test
        public void distribute_4Users2Leagues() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.getUser("hodbub").setCurrLeague(3);

            leagueManager.setMaxLeague(3);
            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(2, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(3, gameCenter.getUser("hodbub").getCurrLeague());
            assertEquals(2, gameCenter.getUser("achiadg").getCurrLeague());
            assertEquals(3, gameCenter.getUser("ronenb").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
            gameCenter.deleteUser("achiadg");
            gameCenter.deleteUser("ronenb");

        }

        @Test
        public void distribute_4Users3Leagues() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.getUser("hodbub").setCurrLeague(3);
            gameCenter.getUser("achiadg").setCurrLeague(2);

            leagueManager.setMaxLeague(3);
            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(2, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(3, gameCenter.getUser("hodbub").getCurrLeague());
            assertEquals(3, gameCenter.getUser("achiadg").getCurrLeague());
            assertEquals(2, gameCenter.getUser("ronenb").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
            gameCenter.deleteUser("achiadg");
            gameCenter.deleteUser("ronenb");

        }

        @Test
        public void distribute_5Users2Leagues3InHigheLeague() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("talb", "1234", "baramit@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.getUser("hodbub").setCurrLeague(3);
            gameCenter.getUser("achiadg").setCurrLeague(3);
            gameCenter.getUser("talb").setCurrLeague(3);

            leagueManager.setMaxLeague(3);
            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(2, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(3, gameCenter.getUser("hodbub").getCurrLeague());
            assertEquals(3, gameCenter.getUser("achiadg").getCurrLeague());
            assertEquals(2, gameCenter.getUser("ronenb").getCurrLeague());
            assertEquals(3, gameCenter.getUser("talb").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
            gameCenter.deleteUser("achiadg");
            gameCenter.deleteUser("ronenb");
            gameCenter.deleteUser("talb");

        }

        @Test
        public void distribute_5Users4LeaguesMix() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("talb", "1234", "baramit@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.getUser("hodbub").setCurrLeague(3);
            gameCenter.getUser("achiadg").setCurrLeague(2);
            gameCenter.getUser("talb").setCurrLeague(2);
            gameCenter.getUser("ronenb").setCurrLeague(1);

            leagueManager.setMaxLeague(3);
            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(2, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(3, gameCenter.getUser("hodbub").getCurrLeague());
            assertEquals(3, gameCenter.getUser("achiadg").getCurrLeague());
            assertEquals(2, gameCenter.getUser("ronenb").getCurrLeague());
            assertEquals(3, gameCenter.getUser("talb").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
            gameCenter.deleteUser("achiadg");
            gameCenter.deleteUser("ronenb");
            gameCenter.deleteUser("talb");
        }

        @Test
        public void distribute_7Users3Leagues() throws Exception {
            gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("talb", "1234", "baramit@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("avig", "1234", "avig@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.registerUser("yossih", "1234", "yossih@post.bgu.ac.il", LocalDate.now(), null);
            gameCenter.getUser("hodbub").setCurrLeague(2);
            gameCenter.getUser("achiadg").setCurrLeague(1);
            gameCenter.getUser("talb").setCurrLeague(2);

            leagueManager.setMaxLeague(2);
            leagueManager.redistributeUsersInLeagues(distribute);

            assertEquals(0, gameCenter.getUser("waldr").getCurrLeague());
            assertEquals(2, gameCenter.getUser("hodbub").getCurrLeague());
            assertEquals(2, gameCenter.getUser("achiadg").getCurrLeague());
            assertEquals(1, gameCenter.getUser("ronenb").getCurrLeague());
            assertEquals(2, gameCenter.getUser("talb").getCurrLeague());
            assertEquals(0, gameCenter.getUser("avig").getCurrLeague());
            assertEquals(1, gameCenter.getUser("yossih").getCurrLeague());

            gameCenter.deleteUser("waldr");
            gameCenter.deleteUser("hodbub");
            gameCenter.deleteUser("achiadg");
            gameCenter.deleteUser("ronenb");
            gameCenter.deleteUser("talb");
            gameCenter.deleteUser("avig");
            gameCenter.deleteUser("yossih");
        }

    }
