package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class LeaveGameTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }


    @Test
    public void testLeaveGameValidGameWithEnsurens()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true, 4);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true, 4);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        usersJoinsGames();
        boolean leavedgame1 = this.leavegame("hodbub" , "YES", "achiadg-poker-game");
        boolean leavedgame2 = this.leavegame("achiadg" , "YES", "hodbub-poker-game");
        boolean leavedgame3 = this.leavegame("rotemw" , "YES", "achiadg-poker-game");
        boolean leavedgame4 = this.leavegame("ronenbu" , "YES", "hodbub-poker-game");
        assertTrue(leavedgame1);
        assertTrue(leavedgame2);
        assertTrue(leavedgame3);
        assertTrue(leavedgame4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testLeaveGameValidGameWithNoEnsurens()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true, 4);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true, 4);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        usersJoinsGames();
        boolean leavedgame1 = this.leavegame("hodbub" , "NO", "achiadg-poker-game");
        boolean leavedgame2 = this.leavegame("achiadg" , "NO", "hodbub-poker-game");
        boolean leavedgame3 = this.leavegame("rotemw" , "NO", "achiadg-poker-game");
        boolean leavedgame4 = this.leavegame("ronenbu" , "NO", "hodbub-poker-game");
        assertFalse(leavedgame1);
        assertFalse(leavedgame2);
        assertFalse(leavedgame3);
        assertFalse(leavedgame4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testLeaveGameValidGameWithOneUser()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true, 4);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true, 4);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean leavedgame1 = this.leavegame("hodbub" , "YES", "hodbub-poker-game");
        boolean leavedgame2 = this.leavegame("achiadg" , "YES", "achiadg-poker-game");
        assertTrue(leavedgame1);
        assertTrue(leavedgame2);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testLeaveGameValidGameHeIsNotAbleTo()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true, 4);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true, 4);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean leavedgame1 = this.leavegame("hodbub" , "YES", "achiadg-poker-game");
        boolean leavedgame2 = this.leavegame("achiadg" , "YES", "hodbub-poker-game");
        boolean leavedgame3 = this.leavegame("ronenbu" , "YES", "achiadg-poker-game");
        boolean leavedgame4 = this.leavegame("rotemw" , "YES", "hodbub-poker-game");
        assertFalse(leavedgame1);
        assertFalse(leavedgame2);
        assertFalse(leavedgame3);
        assertFalse(leavedgame4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testLeaveGameInValidGame()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true, 4);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true, 4);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean leavedgame1 = this.leavegame("hodbub" , "YES", "hodbub-p\noker-game");
        boolean leavedgame2 = this.leavegame("achiadg" , "YES", "achiadg-poker-ga\tme");
        assertFalse(leavedgame1);
        assertFalse(leavedgame2);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    private void usersJoinsGames() {
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game");
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game");
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "hodbub-poker-game");
        boolean useraddedgame4 = this.joinexistinggame("achiadg" , "hodbub-poker-game");
    }

    public void deleteUsers() {
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        boolean deleteUser3 = this.deleteUser("rotemw");
        boolean deleteUser4 = this.deleteUser("ronenbu");
    }

    public  void setUserLeague()
    {
        boolean leaguechanged1 = this.setuserleague("hodbub","achiadg", 4);
        boolean leaguechanged2 = this.setuserleague("hodbub","hodbub", 4);
        boolean leaguechanged3 = this.setuserleague("hodbub","rotemw", 4);
        boolean leaguechanged4 = this.setuserleague("hodbub","ronenbu", 4);
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
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",new DateTime(1991,8,14,17,44));
        boolean useradded3 = this.registerUser("rotemw","rotemwald123","waldr@gmail.com",new DateTime(1991,5,7,12,31));
        boolean useradded4 = this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",new DateTime(1991,7,12,19,48));
    }
}
