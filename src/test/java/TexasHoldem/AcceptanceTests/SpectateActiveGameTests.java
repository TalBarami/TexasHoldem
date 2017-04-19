package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.game.GamePolicy;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

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
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game",  GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, true);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean spectategameisvalid1 = this.spectateactivegame("hodbub" , "achiadg-poker-game",true);
        boolean spectategameisvalid2 = this.spectateactivegame("achiadg" , "hodbub-poker-game",true);
        boolean spectategameisvalid3 = this.spectateactivegame("rotemw" , "achiadg-poker-game",true);
        boolean spectategameisvalid4 = this.spectateactivegame("ronenbu" , "hodbub-poker-game",true);
        assertTrue(spectategameisvalid1);
        assertTrue(spectategameisvalid2);
        assertTrue(spectategameisvalid3);
        assertTrue(spectategameisvalid4);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSpectateActiveInValidGame()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 10000, 10000, 100, 2, 9, false);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game",  GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean spectategameisvalid1 = this.spectateactivegame("hodbub" , "achiadg-poker-game",true);
        boolean spectategameisvalid2 = this.spectateactivegame("achiadg" , "hodbub-poker-game",true);
        boolean spectategameisvalid3 = this.spectateactivegame("rotemw" , "achiadg-poker-game",true);
        boolean spectategameisvalid4 = this.spectateactivegame("ronenbu" , "hodbub-poker-game",true);
        assertFalse(spectategameisvalid1);
        assertFalse(spectategameisvalid2);
        assertFalse(spectategameisvalid3);
        assertFalse(spectategameisvalid4);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSpectateActiveInValidGameName()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game",  GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, true);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean spectategameisvalid1 = this.spectateactivegame("hodb\nub" , "achiadg-poker-game",true);
        boolean spectategameisvalid2 = this.spectateactivegame("ac\rhiadg" , "hodbub-poker-game",true);
        boolean spectategameisvalid3 = this.spectateactivegame("rotemw" , "achiadg-poke\tr-game",true);
        boolean spectategameisvalid4 = this.spectateactivegame("ronenbu" , "h\rodbub-poker-game",true);
        assertFalse(spectategameisvalid1);
        assertFalse(spectategameisvalid2);
        assertFalse(spectategameisvalid3);
        assertFalse(spectategameisvalid4);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
        logoutUsers();
        deleteUsers();
    }


    @Test
    public void testSpectateActiveSqlInjection()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("hodbub","hodbub-poker-game",  GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, true);
        assertTrue(gamecreated1);
        boolean spectategameisvalid1 = this.spectateactivegame("achiadg" , "SELECT * FROM USERS WHERE username = achiadg-poker-game",true);
        assertFalse(spectategameisvalid1);
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
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
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean useradded3 = this.registerUser("rotemw","rotemwald123","waldr@gmail.com",LocalDate.of(1991,11,26),null);
        boolean useradded4 = this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",LocalDate.of(1991,4,26),null);
    }

    public void leaveGames() {
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
        boolean closegame3 = this.leavegame("hodbub" ,"YES", "achiadg-poker-game");
        boolean closegame4 = this.leavegame("rotemw" , "YES","achiadg-poker-game");
        boolean closegame5 = this.leavegame("ronenbu" , "YES","hodbub-poker-game");
        boolean closegame6 = this.leavegame("achiadg" , "YES","hodbub-poker-game");
    }

}
