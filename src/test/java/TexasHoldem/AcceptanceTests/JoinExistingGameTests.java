package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.game.GamePolicy;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

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
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT, 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "hodbub-poker-game",false);
        boolean useraddedgame4 = this.joinexistinggame("rotemw" , "hodbub-poker-game",false);
        assertTrue(useraddedgame1);
        assertTrue(useraddedgame2);
        assertTrue(useraddedgame3);
        assertTrue(useraddedgame4);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameInValidGameName()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT, 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "",false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "ach\niadg-poker-game",false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "rotemw-poker-game",false);
        boolean useraddedgame4 = this.joinexistinggame("rotemw" , "ronenbu-poker-game",false);
        assertFalse(useraddedgame1);
        assertFalse(useraddedgame2);
        assertFalse(useraddedgame3);
        assertFalse(useraddedgame4);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatIsFull()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", GamePolicy.NOLIMIT , 10000, 10000, 100, 2, 3, true);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "achiadg-poker-game",false);
        assertTrue(useraddedgame1);
        assertTrue(useraddedgame2);
        assertFalse(useraddedgame3);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","achiadg-poker-game");
        boolean closegame3 = this.leavegame("rotemw","YES","achiadg-poker-game");
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatBuyInIsGreaterThanBalance()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 43000, 10000, 100, 2, 3, true);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
        assertFalse(useraddedgame1);
        assertFalse(useraddedgame2);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatNoLeagueMatch()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 5000, 10000, 100, 2, 6, true);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "achiadg-poker-game",false);
        assertTrue(useraddedgame1);
        assertTrue(useraddedgame2);
        assertTrue(useraddedgame3);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","achiadg-poker-game");
        boolean closegame3 = this.leavegame("rotemw","YES","achiadg-poker-game");
        boolean closegame4 = this.leavegame("ronenbu","YES","achiadg-poker-game");
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testJoinExistingGameThatGameNameIsSqlInjection()
    {
        registerUsers();
        loginUsers();
        addBalance();
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 20000, 90000, 100, 2, 4, true);
        assertTrue(gamecreated1);
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiad-poker-game ; SELECT * FROM GAMES WHERE GAMENAME = achiad-poker-game",false);
        assertFalse(useraddedgame1);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        logoutUsers();
        deleteUsers();
    }

    public void leaveGames() {
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
        boolean closegame3 = this.leavegame("hodbub" ,"YES", "achiadg-poker-game");
        boolean closegame4 = this.leavegame("rotemw" , "YES","achiadg-poker-game");
        boolean closegame5 = this.leavegame("ronenbu" , "YES","hodbub-poker-game");
        boolean closegame6 = this.leavegame("rotemw" , "YES","hodbub-poker-game");
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
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",45000);
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
