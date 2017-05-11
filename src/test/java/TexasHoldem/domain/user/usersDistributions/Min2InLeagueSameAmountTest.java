package TexasHoldem.domain.user.usersDistributions;

import TexasHoldem.domain.system.GameCenter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by hod on 06/05/2017.
 */
public class Min2InLeagueSameAmountTest {
    GameCenter gameCenter;
    DistributionAlgorithm distribute;

    @Before
    public void setUp() throws Exception {
        gameCenter = new GameCenter();
        distribute = new Min2InLeagueSameAmount(gameCenter);
    }

    @Test
    public void distibute_2NewUsersShouldStayThere() throws Exception {
        gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

        distribute.distribute(0);

        assertEquals(0, gameCenter.getUser("waldr").getCurrLeague());
        assertEquals(0, gameCenter.getUser("hodbub").getCurrLeague());

        gameCenter.deleteUser("waldr");
        gameCenter.deleteUser("hodbub");
    }

    @Test
    public void distibute_2Users2Leagues() throws Exception {
        gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.getUser("hodbub").setCurrLeague(1);

        distribute.distribute(1);

        assertEquals(1, gameCenter.getUser("waldr").getCurrLeague());
        assertEquals(1, gameCenter.getUser("hodbub").getCurrLeague());

        gameCenter.deleteUser("waldr");
        gameCenter.deleteUser("hodbub");
    }

    @Test
    public void distibute_3Users2Leagues() throws Exception {
        gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.getUser("hodbub").setCurrLeague(1);

        distribute.distribute(3);

        assertEquals(3, gameCenter.getUser("waldr").getCurrLeague());
        assertEquals(3, gameCenter.getUser("hodbub").getCurrLeague());
        assertEquals(3, gameCenter.getUser("achiadg").getCurrLeague());

        gameCenter.deleteUser("waldr");
        gameCenter.deleteUser("hodbub");
        gameCenter.deleteUser("achiadg");
    }

    @Test
    public void distibute_4Users2Leagues() throws Exception {
        gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.getUser("hodbub").setCurrLeague(3);

        distribute.distribute(3);

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
    public void distibute_4Users3Leagues() throws Exception {
        gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.getUser("hodbub").setCurrLeague(3);
        gameCenter.getUser("achiadg").setCurrLeague(2);

        distribute.distribute(3);

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
    public void distibute_5Users2Leagues3InHigheLeague() throws Exception {
        gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("talb", "1234", "baramit@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.getUser("hodbub").setCurrLeague(3);
        gameCenter.getUser("achiadg").setCurrLeague(3);
        gameCenter.getUser("talb").setCurrLeague(3);

        distribute.distribute(3);

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
    public void distibute_5Users4LeaguesMix() throws Exception {
        gameCenter.registerUser("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.registerUser("talb", "1234", "baramit@post.bgu.ac.il", LocalDate.now(), null);
        gameCenter.getUser("hodbub").setCurrLeague(3);
        gameCenter.getUser("achiadg").setCurrLeague(2);
        gameCenter.getUser("talb").setCurrLeague(2);
        gameCenter.getUser("ronenb").setCurrLeague(1);

        distribute.distribute(3);

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
    public void distibute_7Users3Leagues() throws Exception {
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

        distribute.distribute(2);

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