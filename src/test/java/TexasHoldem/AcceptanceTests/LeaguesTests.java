package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 11/04/2017.
 */
public class LeaguesTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testSetDeafaultLeagueForNewUsersValid()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean leaguechanged1 = this.setuserleague("hodbub","achiadg", 1);
        boolean leaguechanged2 = this.setuserleague("hodbub","hodbub", 1);
        boolean leaguechanged3 = this.setuserleague("hodbub","rotemw", 1);
        boolean leaguechanged4 = this.setuserleague("hodbub","ronenbu", 1);
        assertTrue(leaguechanged1);
        assertTrue(leaguechanged2);
        assertTrue(leaguechanged3);
        assertTrue(leaguechanged4);
        createGames();
        usersJoinsGames();
        leavegame();
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSetCriteriaForMovingBetweenLeaguesValid()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean criteriachanged1 = this.setcriteriatomoveleague("hodbub", 2000);
        assertTrue(criteriachanged1);
        addBalance2();
        boolean criteriachanged2 = this.setcriteriatomoveleague("achiadg", 3000);
        assertTrue(criteriachanged2);
        createGames();
        usersJoinsGames();
        leavegame();
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }


    @Test
    public void testMoveUserBetweenLeaguesValid()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean criteriachanged1 = this.setcriteriatomoveleague("hodbub", 2000);
        assertTrue(criteriachanged1);
        createGames();
        usersJoinsGames();
        leavegame();
        addBalance();
        boolean leaguechanged1 = this.setuserleague("hodbub","achiadg", 2);
        boolean leaguechanged2 = this.setuserleague("hodbub","hodbub", 2);
        boolean leaguechanged3 = this.setuserleague("hodbub","rotemw", 2);
        boolean leaguechanged4 = this.setuserleague("hodbub","ronenbu", 2);
        assertTrue(leaguechanged1);
        assertTrue(leaguechanged2);
        assertTrue(leaguechanged3);
        assertTrue(leaguechanged4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSetCriteriaForMovingBetweenLeaguesInValid()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean leaguechanged1 = this.setuserleague("achiadg","achiadg", 1);
        boolean leaguechanged2 = this.setuserleague("rotemw","hodbub", 1);
        boolean leaguechanged3 = this.setuserleague("ronenbu","rotemw", 1);
        boolean leaguechanged4 = this.setuserleague("hodbub","ronenbu", 1);
        assertFalse(leaguechanged1);
        assertFalse(leaguechanged2);
        assertFalse(leaguechanged3);
        assertTrue(leaguechanged4);
        createGames();
        usersJoinsGames();
        leavegame();
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSetDeafaultLeagueForNewUsersInValid()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean criteriachanged1 = this.setcriteriatomoveleague("achiadg", 2000);
        assertFalse(criteriachanged1);
        addBalance2();
        boolean criteriachanged2 = this.setcriteriatomoveleague("hodbub", 3000);
        assertFalse(criteriachanged2);
        createGames();
        usersJoinsGames();
        leavegame();
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testMoveUserBetweenLeaguesInValid()
    {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        boolean criteriachanged1 = this.setcriteriatomoveleague("hodbub", 2000);
        assertTrue(criteriachanged1);
        createGames();
        usersJoinsGames();
        leavegame();
        boolean leaguechanged1 = this.setuserleague("hodbub","achiadg", 2);
        boolean leaguechanged2 = this.setuserleague("hodbub","hodbub", 2);
        boolean leaguechanged3 = this.setuserleague("hodbub","rotemw", 2);
        boolean leaguechanged4 = this.setuserleague("hodbub","ronenbu", 2);
        assertFalse(leaguechanged1);
        assertFalse(leaguechanged2);
        assertFalse(leaguechanged3);
        assertFalse(leaguechanged4);
        boolean leaguechanged5 = this.setuserleague("achiadg","achiadg", 2);
        boolean leaguechanged6 = this.setuserleague("rotemw","hodbub", 2);
        boolean leaguechanged7 = this.setuserleague("ronenbu","rotemw", 2);
        boolean leaguechanged8 = this.setuserleague("hodbub","ronenbu", 2);
        assertFalse(leaguechanged5);
        assertFalse(leaguechanged6);
        assertFalse(leaguechanged7);
        assertTrue(leaguechanged8);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }



    private void leavegame()
    {
        boolean leavedgame1 = this.leavegame("hodbub" , "YES", "achiadg-poker-game");
        boolean leavedgame2 = this.leavegame("achiadg" , "YES", "hodbub-poker-game");
        boolean leavedgame3 = this.leavegame("rotemw" , "YES", "achiadg-poker-game");
        boolean leavedgame4 = this.leavegame("ronenbu" , "YES", "hodbub-poker-game");
    }


    private void addBalance2() {
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",500);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",300);
        boolean addedbalance3 = this.addbalancetouserwallet("rotemw",300);
        boolean addedbalance4 = this.addbalancetouserwallet("ronenbu",200);
    }

    private void createGames()
    {
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 100, 10000, 100, 2, 9, true, 1);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true, 1);
    }

    private void usersJoinsGames() {
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game");
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game");
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "hodbub-poker-game");
        boolean useraddedgame4 = this.joinexistinggame("achiadg" , "hodbub-poker-game");
    }

    private void deleteUsers() {
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        boolean deleteUser3 = this.deleteUser("rotemw");
        boolean deleteUser4 = this.deleteUser("ronenbu");
    }

    private  void setUserLeague()
    {
        boolean leaguechanged1 = this.setuserleague("hodbub","achiadg", 1);
        boolean leaguechanged2 = this.setuserleague("hodbub","hodbub", 1);
        boolean leaguechanged3 = this.setuserleague("hodbub","rotemw", 1);
        boolean leaguechanged4 = this.setuserleague("hodbub","ronenbu", 1);
    }


    private void logoutUsers() {
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean userloggedout3 = this.logout("rotemw");
        boolean userloggedout4 = this.logout("ronenbu");
    }




    private void addBalance() {
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",1000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",1100);
        boolean addedbalance3 = this.addbalancetouserwallet("rotemw",1000);
        boolean addedbalance4 = this.addbalancetouserwallet("ronenbu",1000);
    }


    private void loginUsers() {
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        boolean userloggedin3 = this.login("rotemw","rotemwald123");
        boolean userloggedin4 = this.login("ronenbu","ronenbu123");
    }



    private void registerUsers()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",new DateTime(1991,8,14,17,44));
        boolean useradded3 = this.registerUser("rotemw","rotemwald123","waldr@gmail.com",new DateTime(1991,5,7,12,31));
        boolean useradded4 = this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",new DateTime(1991,7,12,19,48));
    }
}