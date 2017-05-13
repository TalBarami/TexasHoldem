package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.game.GamePolicy;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class FindAllAvailableGamesToJoinTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testSearchAllGamesToJoinWhenThereAreAvailableGames()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 10000, 10000, 100, 2, 5, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game",  GamePolicy.NOLIMIT , 300, 300, 2, 2, 5, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchavailablegamestojoin("achiadg");
        boolean gamesfound2 = this.searchavailablegamestojoin("hodbub");
        boolean gamesfound3 = this.searchavailablegamestojoin("rotemw");
        boolean gamesfound4 = this.searchavailablegamestojoin("ronenbu");
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertTrue(gamesfound3);
        assertTrue(gamesfound4);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchAllGamesToJoinWhenThereAreNoAvailableGames()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",200000);
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 100000, 10000, 100, 2, 5, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game",  GamePolicy.NOLIMIT , 300, 400, 2, 2, 3, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        usersJoinsGames();
        boolean gamesfound1 = this.searchavailablegamestojoin("achiadg");
        boolean gamesfound2 = this.searchavailablegamestojoin("hodbub");
        boolean gamesfound3 = this.searchavailablegamestojoin("rotemw");
        boolean gamesfound4 = this.searchavailablegamestojoin("ronenbu");
        assertFalse(gamesfound1);
        assertFalse(gamesfound2);
        assertFalse(gamesfound3);
        assertFalse(gamesfound4);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    public void leaveGames() {
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
        boolean closegame4 = this.leavegame("rotemw" , "YES","hodbub-poker-game");
        boolean closegame5 = this.leavegame("ronenbu" , "YES","hodbub-poker-game");
    }

    private void usersJoinsGames() {
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "hodbub-poker-game",false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "hodbub-poker-game",false);
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
}
