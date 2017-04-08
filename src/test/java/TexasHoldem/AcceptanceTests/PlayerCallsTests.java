package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class PlayerCallsTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testPlayerCallsValid() {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        int potsize1 = this.getPotSize("achiadg-poker-game");
        int potsize2 = this.getPotSize("hodbub-poker-game");
        int playerbalance1 = this.getPlayerbalance("hodbub" , "achiadg-poker-game");
        int playerbalance2 = this.getPlayerbalance("ronenbu" , "hodbub-poker-game");
        boolean playedturn1 = this.playturnraise("achiadg" , "achiadg-poker-game","RAISE",400);
        boolean playedturn2 = this.playturnraise("hodbub" , "hodbub-poker-game","RAISE",6);
        boolean playedturn3 = this.playturn("hodbub" , "achiadg-poker-game","CALL");
        boolean playedturn4 = this.playturn("ronenbu" , "hodbub-poker-game","CALL");
        int potsize3 = this.getPotSize("achiadg-poker-game");
        int potsize4 = this.getPotSize("hodbub-poker-game");
        int playerbalance3 = this.getPlayerbalance("hodbub" , "achiadg-poker-game");
        int playerbalance4 = this.getPlayerbalance("ronenbu" , "hodbub-poker-game");
        assertEquals(potsize1 + 800,potsize3 );
        assertEquals(potsize2 + 12,potsize4 );
        assertEquals(playerbalance1,playerbalance3 - 400);
        assertEquals(playerbalance2,playerbalance4 - 6);
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
