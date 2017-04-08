package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class PlayTurnTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testPlayTurnValidMove()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        boolean playedturn1 = this.playturn("achiadg" ,"achiadg-poker-game", "CHECK");
        boolean playedturn2 = this.playturn("hodbub" ,"hodbub-poker-game", "CHECK");
        boolean playedturn3 = this.playturn("hodbub" , "achiadg-poker-game","RAISE");
        boolean playedturn4 = this.playturn("ronenbu" , "hodbub-poker-game","CHECK");
        boolean playedturn5 = this.playturn("rotemw" , "achiadg-poker-game","CALL");
        boolean playedturn6 = this.playturn("achiadg" , "hodbub-poker-game","CHECK");
        assertTrue(playedturn1);
        assertTrue(playedturn2);
        assertTrue(playedturn3);
        assertTrue(playedturn4);
        assertTrue(playedturn5);
        assertTrue(playedturn6);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }


    @Test
    public void testPlayTurnInValidMove()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        boolean playedturn1 = this.playturn("achiadg" , "achiadg-poker-game", "RAISE");
        boolean playedturn2 = this.playturn("hodbub" , "hodbub-poker-game", "CHECK");
        boolean playedturn3 = this.playturn("hodbub" , "achiadg-poker-game","RAISE");
        boolean playedturn4 = this.playturn("ronenbu" , "hodbub-poker-game","CHECK");
        boolean playedturn5 = this.playturn("rotemw" , "achiadg-poker-game","CHECK");
        boolean playedturn6 = this.playturn("achiadg" , "hodbub-poker-game","CALL");
        assertTrue(playedturn1);
        assertTrue(playedturn2);
        assertTrue(playedturn3);
        assertTrue(playedturn4);
        assertFalse(playedturn5);
        assertFalse(playedturn6);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnInValidCharacters()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        boolean playedturn1 = this.playturn("achiadg" ,"achiadg-poker-game", "CHE\nCK");
        boolean playedturn2 = this.playturn("hodbub" ,"hodbub-poker-game", "CHE\rCK");
        boolean playedturn3 = this.playturn("hodbub" , "achiadg-poker-game","RAIS\tE");
        boolean playedturn4 = this.playturn("ronenbu" , "hodbub-poker-game","C\\\rHECK");
        boolean playedturn5 = this.playturn("rotemw" , "achiadg-poker-game","C\\\tALL");
        boolean playedturn6 = this.playturn("achiadg" , "hodbub-poker-game","C\rHECK");
        assertFalse(playedturn1);
        assertFalse(playedturn2);
        assertFalse(playedturn3);
        assertFalse(playedturn4);
        assertFalse(playedturn5);
        assertFalse(playedturn6);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnSqlInjection()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        boolean playedturn1 = this.playturn("achiadg" , "achiadg-poker-game", "SELECT * FROM USERS");
        assertFalse(playedturn1);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    private void createGames()
    {
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true);
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
