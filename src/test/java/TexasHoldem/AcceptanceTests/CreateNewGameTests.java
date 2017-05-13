package TexasHoldem.AcceptanceTests;


import TexasHoldem.domain.game.GamePolicy;
import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class CreateNewGameTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testCreateNewGameValid()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        assertTrue(userloggedin1);
        assertTrue(userloggedin2);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",20000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",1000);
        assertTrue(addedbalance1);
        assertTrue(addedbalance2);
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", GamePolicy.NOLIMIT , 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, false);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean closegame2 = this.leavegame("hodbub","YES","hodbub-poker-game");
        assertTrue(gamecreated1);
        assertTrue(gamecreated2);
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testCreateNewGameThatGameNameIsTaken()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        assertTrue(userloggedin1);
        assertTrue(userloggedin2);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",20000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",1000);
        assertTrue(addedbalance1);
        assertTrue(addedbalance2);
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT, 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","achiadg-poker-game", GamePolicy.NOLIMIT  , 300, 300, 2, 2, 9, false);
        assertTrue(gamecreated1);
        assertFalse(gamecreated2);
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game");
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testCreateNewGameNumberOfPlayersAreInvalid()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        assertTrue(userloggedin1);
        assertTrue(userloggedin2);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",20000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",1000);
        assertTrue(addedbalance1);
        assertTrue(addedbalance2);
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game", GamePolicy.NOLIMIT , 10000, 10000, 100, 1, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game", GamePolicy.POTLIMIT , 300, 300, 2, 2, 12, false);
        assertFalse(gamecreated1);
        assertFalse(gamecreated2);
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testCreateNewGameThatIsNull()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        assertTrue(userloggedin1);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",20000);
        assertTrue(addedbalance1);
        boolean gamecreated1 = this.createnewgame("achiadg",null, GamePolicy.POTLIMIT, 10000, 10000, 100, 2, 9, true);
        assertFalse(gamecreated1);
        boolean userloggedout1 = this.logout("achiadg");
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
    }

    @Test
    public void testCreateNewGameThatBuyInIsGreaterThanAmount()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        assertTrue(userloggedin1);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",20000);
        assertTrue(addedbalance1);
        boolean gamecreated1 = this.createnewgame("achiadg","achiad-poker-game", GamePolicy.POTLIMIT , 21000, 10000, 100, 2, 9, true);
        assertFalse(gamecreated1);
        boolean userloggedout1 = this.logout("achiadg");
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
    }

    @Test
    public void testCreateNewGameThatOneCharInGamePreferencesIsIllegal()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        assertTrue(userloggedin1);
        assertTrue(userloggedin2);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg", 20000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",1000);
        assertTrue(addedbalance1);
        assertTrue(addedbalance2);
        boolean gamecreated1 = this.createnewgame("achiadg", "achiadg-poke\tr-game", GamePolicy.POTLIMIT , 10000, 10000, 100, 1, 9, true);
        boolean gamecreated2 = this.createnewgame("achiadg","a\rchiad-poker-game", GamePolicy.POTLIMIT , 300, 300, 2, 2, 12, false);
        boolean gamecreated3 = this.createnewgame("hodbub","new-p\\\noker-game", GamePolicy.POTLIMIT , 10000, 10000, 100, 1, 9, true);
        boolean gamecreated4 = this.createnewgame("hodbub","fun-poker-gam\\\re", GamePolicy.POTLIMIT , 300, 300, 2, 2, 12, false);
        assertFalse(gamecreated1);
        assertFalse(gamecreated2);
        assertFalse(gamecreated3);
        assertFalse(gamecreated4);
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testCreateNewGameThatOneCharInGamePreferencesIsIllegalMore()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        assertTrue(userloggedin1);
        assertTrue(userloggedin2);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg", 20000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",1000);
        assertTrue(addedbalance1);
        assertTrue(addedbalance2);
        boolean gamecreated1 = this.createnewgame("achiadg", "achiadg-poker-game\n", GamePolicy.POTLIMIT , 10000, 10000, 100, 1, 9, true);
        boolean gamecreated2 = this.createnewgame("achiadg","a\rchiad-poker-game\r", GamePolicy.POTLIMIT , 300, 300, 2, 2, 12, false);
        boolean gamecreated3 = this.createnewgame("hodbub","new-p\\\noker-game\n", GamePolicy.POTLIMIT , 10000, 10000, 100, 1, 9, true);
        boolean gamecreated4 = this.createnewgame("hodbub","fun-poker-gam\\\re\\", GamePolicy.POTLIMIT , 300, 300, 2, 2, 12, false);
        assertFalse(gamecreated1);
        assertFalse(gamecreated2);
        assertFalse(gamecreated3);
        assertFalse(gamecreated4);
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testCreateNewGameThatIsEmpty()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        assertTrue(userloggedin1);
        assertTrue(userloggedin2);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg", 20000);
        boolean addedbalance2 = this.addbalancetouserwallet("hodbub",1000);
        assertTrue(addedbalance1);
        assertTrue(addedbalance2);
        boolean gamecreated1 = this.createnewgame("achiadg", "", GamePolicy.POTLIMIT , 10000, 10000, 100, 1, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","", GamePolicy.POTLIMIT , 10000, 10000, 100, 1, 9, true);
        assertFalse(gamecreated1);
        assertFalse(gamecreated2);
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testCreateNewGameWithSqlInjection()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        assertTrue(userloggedin1);
        boolean addedbalance1 = this.addbalancetouserwallet("achiadg",20000);
        assertTrue(addedbalance1);
        boolean gamecreated1 = this.createnewgame("achiadg","achiad-poker-game ; SELECT * FROM GAMES WHERE GAMENAME = achiad-poker-game", GamePolicy.POTLIMIT , 10000, 10000, 100, 1, 9, true);
        assertFalse(gamecreated1);
        boolean userloggedout1 = this.logout("achiadg");
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
    }
}
