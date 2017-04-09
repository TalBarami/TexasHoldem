package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class JoinExistingGameTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testJoinExistingGameValid()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true, 4);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, false, 4);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game");
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game");
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "hodbub-poker-game");
        boolean useraddedgame4 = this.joinexistinggame("rotemw" , "hodbub-poker-game");
        assertTrue(useraddedgame1);
        assertTrue(useraddedgame2);
        assertTrue(useraddedgame3);
        assertTrue(useraddedgame4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatIsFull()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 3, true, 4);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game");
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game");
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "achiadg-poker-game");
        assertTrue(useraddedgame1);
        assertTrue(useraddedgame2);
        assertFalse(useraddedgame3);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        assertTrue(closegame1);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatBuyInIsGreaterThanBalance()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 90000, 10000, 100, 2, 3, true, 4);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game");
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game");
        assertFalse(useraddedgame1);
        assertFalse(useraddedgame2);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        assertTrue(closegame1);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatNoLeagueMatch()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean leaguechanged2 = this.setuserleague("hodbub", 3);
        boolean leaguechanged3 = this.setuserleague("rotemw", 6);
        boolean leaguechanged4 = this.setuserleague("ronenbu", 7);
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 90000, 10000, 100, 2, 3, true, 4);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game");
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game");
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "achiadg-poker-game");
        assertFalse(useraddedgame1);
        assertFalse(useraddedgame2);
        assertFalse(useraddedgame3);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        assertTrue(closegame1);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatGameNameIsSqlInjection()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 90000, 10000, 100, 2, 3, true, 4);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "SELECT * FROM GAMES WHERE GAMENAME = achiad-poker-game");
        assertFalse(useraddedgame1);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        assertTrue(closegame1);
        logoutUsers();
        deleteUsers();
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
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",new DateTime(1991,8,14,17,44));
        boolean useradded3 = this.registerUser("rotemw","rotemwald123","waldr@gmail.com",new DateTime(1991,5,7,12,31));
        boolean useradded4 = this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",new DateTime(1991,7,12,19,48));
    }

    public  void setUserLeague()
    {
        boolean leaguechanged1 = this.setuserleague("achiadg", 4);
        boolean leaguechanged2 = this.setuserleague("hodbub", 4);
        boolean leaguechanged3 = this.setuserleague("rotemw", 4);
        boolean leaguechanged4 = this.setuserleague("ronenbu", 4);
    }

}
