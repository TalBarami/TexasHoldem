
package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.game.GamePolicy;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


/**
 * Created by אחיעד on 08/04/2017.
 */

public class PlayerChecksTest extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testPlayerChecksValid() {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        int potsize1 = this.getpotsize("achiadg-poker-game");
        int playerbalance1 = this.getplayerbalance("hodbub","achiadg-poker-game");
        boolean playedturn1 = this.playcheck("hodbub", "achiadg-poker-game");
        int potsize3 = this.getpotsize("achiadg-poker-game");
        int playerbalance3 = this.getplayerbalance("hodbub" ,"achiadg-poker-game");
        assertEquals(potsize1,potsize3);
        assertEquals(playerbalance1,playerbalance3);
        assertTrue(playedturn1);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayerChecksInValidCharecters() {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        boolean playedturn1 = this.playcheck("hodbub", "achiadg-po\nker-game");
        assertFalse(playedturn1);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    private void createGames()
    {
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT, 10000, 10000, 100, 2, 9, true);
    }

    public void deleteUsers() {
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        boolean deleteUser3 = this.deleteUser("rotemw");
        boolean deleteUser4 = this.deleteUser("ronenbu");
    }

    public void logoutUsers() {
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean userloggedout3 = this.logout("rotemw");
        boolean userloggedout4 = this.logout("ronenbu");
    }

    public void addBalance() {
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",20000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",40000);
        boolean addedbalance3 = this.addbalancetouserwallet("rotemw",20000);
        boolean addedbalance4 = this.addbalancetouserwallet("ronenbu",30000);
    }


    public void loginUsers() {
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        boolean userloggedin3 = this.login("rotemw","rotemwald123");
        boolean userloggedin4 = this.login("ronenbu","ronenbu123");
    }

    public void registerUsers()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean useradded3 = this.registerUser("rotemw","rotemwald123","waldr@gmail.com",LocalDate.of(1991,11,26),null);
        boolean useradded4 = this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",LocalDate.of(1991,4,26),null);
    }

    private void usersJoinsGames() {
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "achiadg-poker-game",false);
    }

    public void leaveGames() {
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("rotemw" , "YES","achiadg-poker-game");
        boolean closegame3 = this.leavegame("ronenbu" , "YES","achiadg-poker-game");
        boolean closegame4 = this.leavegame("hodbub","YES","achiadg-poker-game");
    }
}

