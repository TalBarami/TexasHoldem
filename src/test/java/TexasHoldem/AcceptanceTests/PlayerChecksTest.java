package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.game.GameSettings;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class PlayerChecksTest extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testPlayerChecksValid() {
        registerUsers();
        loginUsers();
        addBalance();
        setUserLeague();
        createGames();
        usersJoinsGames();
        int potsize1 = this.getPotSize("achiadg-poker-game");
        int potsize2 = this.getPotSize("hodbub-poker-game");
        int playerbalance1 = this.getPlayerbalance("achiadg","achiadg-poker-game" );
        int playerbalance2 = this.getPlayerbalance("hodbub", "hodbub-poker-game");
        boolean playedturn1 = this.playturn("achiadg", "achiadg-poker-game", "CHECK");
        boolean playedturn2 = this.playturn("hodbub", "hodbub-poker-game", "CHECK");
        int potsize3 = this.getPotSize("achiadg-poker-game");
        int potsize4 = this.getPotSize("hodbub-poker-game");
        int playerbalance3 = this.getPlayerbalance("achiadg" ,"achiadg-poker-game");
        int playerbalance4 = this.getPlayerbalance("hodbub", "hodbub-poker-game");
        assertEquals(potsize1,potsize3);
        assertEquals(potsize2,potsize4);
        assertEquals(playerbalance1,playerbalance3);
        assertEquals(playerbalance2,playerbalance4);
        boolean closegame1 = this.closegame("achiadg-poker-game");
        boolean closegame2 = this.closegame("hodbub-poker-game");
        assertTrue(closegame1);
        assertTrue(closegame2);
        logoutUsers();
        deleteUsers();
    }


    private void createGames()
    {
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GameSettings.GamePolicy.NOLIMIT, 10000, 10000, 100, 2, 9, true);
        boolean gamecreated2 = this.createnewgame("hodbub","hodbub-poker-game",  GameSettings.GamePolicy.NOLIMIT , 300, 300, 2, 2, 9, true);
    }

    private void usersJoinsGames() {
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "hodbub-poker-game",false);
        boolean useraddedgame4 = this.joinexistinggame("achiadg" , "hodbub-poker-game",false);
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
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean useradded3 = this.registerUser("rotemw","rotemwald123","waldr@gmail.com",LocalDate.of(1991,11,26),null);
        boolean useradded4 = this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",LocalDate.of(1991,4,26),null);
    }
}
