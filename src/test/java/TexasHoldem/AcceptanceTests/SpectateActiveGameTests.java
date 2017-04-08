package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class SpectateActiveGameTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testSpectateActiveValidGame()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean spectategameisvalid1 = this.spectateactivegame("hodbub" , "achiadg-poker-game");
        boolean spectategameisvalid2 = this.spectateactivegame("achiadg" , "hodbub-poker-game");
        boolean spectategameisvalid3 = this.spectateactivegame("rotemw" , "achiadg-poker-game");
        boolean spectategameisvalid4 = this.spectateactivegame("ronenbu" , "hodbub-poker-game");
        assertTrue(spectategameisvalid1);
        assertTrue(spectategameisvalid2);
        assertTrue(spectategameisvalid3);
        assertTrue(spectategameisvalid4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSpectateActiveInValidGame()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, false);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean spectategameisvalid1 = this.spectateactivegame("hodbub" , "achiadg-poker-game");
        boolean spectategameisvalid2 = this.spectateactivegame("achiadg" , "hodbub-poker-game");
        boolean spectategameisvalid3 = this.spectateactivegame("rotemw" , "achiadg-poker-game");
        boolean spectategameisvalid4 = this.spectateactivegame("ronenbu" , "hodbub-poker-game");
        assertFalse(spectategameisvalid1);
        assertFalse(spectategameisvalid2);
        assertFalse(spectategameisvalid3);
        assertFalse(spectategameisvalid4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSpectateActiveInValidGameName()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean spectategameisvalid1 = this.spectateactivegame("hodbub" , "achiadg-poker-ga\nme");
        boolean spectategameisvalid2 = this.spectateactivegame("achiadg" , "hodb\tub-poker-game");
        boolean spectategameisvalid3 = this.spectateactivegame("rotemw" , "achiadg-po\\\nker-game");
        boolean spectategameisvalid4 = this.spectateactivegame("ronenbu" , "h\rodbub-poker-game");
        assertFalse(spectategameisvalid1);
        assertFalse(spectategameisvalid2);
        assertFalse(spectategameisvalid3);
        assertFalse(spectategameisvalid4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }


    @Test
    public void testSpectateActiveSqlInjection()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, true);
        assertTrue(gamecreated1);
        boolean spectategameisvalid1 = this.spectateactivegame("hodbub" , "SELECT * FROM GAMES WHERE GAMENAME = achiad-poker-game");
        assertFalse(spectategameisvalid1);
        boolean closegame1 = this.closegame("hodbub-poker-game");
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
}
