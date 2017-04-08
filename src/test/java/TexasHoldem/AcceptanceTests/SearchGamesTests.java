package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class SearchGamesTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testSearchGamesByPlayerName()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebyplayername("achiadg");
        boolean gamesfound2 = this.searchgamebyplayername("hodbub");
        boolean gamesfound3 = this.searchgamebyplayername("rotemw");
        boolean gamesfound4 = this.searchgamebyplayername("ronenbu");
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertFalse(gamesfound3);
        assertFalse(gamesfound4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    //TODO
    public void testSearchGamesByPotSize()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "pot limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "pot limit" , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebyplayername("achiadg");
        boolean gamesfound2 = this.searchgamebyplayername("hodbub");
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchGamesByTypePolicy()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebytypepolicy("limit");
        boolean gamesfound2 = this.searchgamebytypepolicy("no limit");
        boolean gamesfound3 = this.searchgamebytypepolicy("pot limit");
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertFalse(gamesfound3);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchGamesByBuyIn()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebybuyin(10000);
        boolean gamesfound2 = this.searchgamebybuyin(300);
        boolean gamesfound3 = this.searchgamebybuyin(679);
        boolean gamesfound4 = this.searchgamebybuyin(456);
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertFalse(gamesfound3);
        assertFalse(gamesfound4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchGamesByChipPolicy()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebychippolicy(10000);
        boolean gamesfound2 = this.searchgamebychippolicy(300);
        boolean gamesfound3 = this.searchgamebychippolicy(679);
        boolean gamesfound4 = this.searchgamebychippolicy(456);
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertFalse(gamesfound3);
        assertFalse(gamesfound4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchGamesByMinimumBet()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 3, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebyminbet(100);
        boolean gamesfound2 = this.searchgamebyminbet(2);
        boolean gamesfound3 = this.searchgamebyminbet(5);
        boolean gamesfound4 = this.searchgamebyminbet(14);
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertFalse(gamesfound3);
        assertFalse(gamesfound4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchGamesByMinimumPlayers()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebyminplayers(2);
        boolean gamesfound2 = this.searchgamebyminplayers(3);
        boolean gamesfound3 = this.searchgamebyminplayers(5);
        boolean gamesfound4 = this.searchgamebyminplayers(4);
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertFalse(gamesfound3);
        assertFalse(gamesfound4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchGamesByMaximumPlayers()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", "no limit" , 300, 300, 2, 3, 8, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean gamesfound1 = this.searchgamebymaxplayers(9);
        boolean gamesfound2 = this.searchgamebymaxplayers(8);
        boolean gamesfound3 = this.searchgamebymaxplayers(5);
        boolean gamesfound4 = this.searchgamebymaxplayers(4);
        assertTrue(gamesfound1);
        assertTrue(gamesfound2);
        assertFalse(gamesfound3);
        assertFalse(gamesfound4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testSearchGamesBySpectatingAvailable()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", "limit" , 10000, 10000, 100, 2, 9, true);
        assertTrue(gamecreated1);
        boolean gamesfound1 = this.searchgamebyspectateisvalid(true);
        boolean gamesfound2 = this.searchgamebyspectateisvalid(false);
        assertTrue(gamesfound1);
        assertFalse(gamesfound2);
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
}