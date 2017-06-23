
package Server.AcceptanceTests;

import Enumerations.GamePolicy;
import Server.data.users.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


/**
 * Created by אחיעד on 08/04/2017.
 */

public class PlayTurnTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @After
    public void tearDown() {
        Users.getUsersInGame().clear();
        super.clearAllEventsFromDB();
        super.clearAllUsersFromDB();
    }

    @Test
    public void testWaitForNewTurnAfterWrongPlayAction(){
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcheck("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed4 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed5 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertFalse(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        assertTrue(playerplayed5);
        boolean playerplayed6 = this.playcheck("hodbub", "achiadg-poker-game");
        boolean playerplayed7 = this.playcheck("rotemw", "achiadg-poker-game");
        boolean playerplayed8 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed9 = this.playcheck("ronenbu", "achiadg-poker-game");
        assertTrue(playerplayed6);
        assertTrue(playerplayed7);
        assertFalse(playerplayed8);
        assertTrue(playerplayed9);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testNextPlayerCantPlayBeforePrevivousPlayedValidMove(){
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcheck("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed5 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed6 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertFalse(playerplayed2);
        assertFalse(playerplayed3);
        assertTrue(playerplayed4);
        assertTrue(playerplayed5);
        assertTrue(playerplayed6);
        boolean playerplayed7 = this.playcheck("hodbub", "achiadg-poker-game");
        boolean playerplayed8 = this.playcheck("rotemw", "achiadg-poker-game");
        boolean playerplayed9 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed10 = this.playcheck("achiadg", "achiadg-poker-game");
        boolean playerplayed11 = this.playcheck("ronenbu", "achiadg-poker-game");
        boolean playerplayed12 = this.playcheck("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed6);
        assertTrue(playerplayed7);
        assertTrue(playerplayed8);
        assertFalse(playerplayed9);
        assertFalse(playerplayed10);
        assertTrue(playerplayed11);
        assertTrue(playerplayed12);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testNotPlayersTurn(){
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("hodbub", "achiadg-poker-game");

        assertFalse(playerplayed1);

        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testCantCallAfterCheck(){
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        boolean playerplayed5 = this.playcheck("hodbub", "achiadg-poker-game");
        boolean playerplayed6 = this.playcheck("rotemw", "achiadg-poker-game");
        boolean playerplayed7 = this.playcall("ronenbu", "achiadg-poker-game");
        assertTrue(playerplayed5);
        assertTrue(playerplayed6);
        assertFalse(playerplayed7);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testCantCheckAfterRaise() {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playraise("ronenbu", "achiadg-poker-game",20);
        boolean playerplayed2 = this.playcheck("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertFalse(playerplayed2);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnValidMove()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnValidMoveAfterFirstCards()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        boolean playerplayed5 = this.playcheck("hodbub", "achiadg-poker-game");
        boolean playerplayed6 = this.playcheck("rotemw", "achiadg-poker-game");
        boolean playerplayed7 = this.playraise("ronenbu", "achiadg-poker-game", 250);
        boolean playerplayed8 = this.playfold("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed5);
        assertTrue(playerplayed6);
        assertTrue(playerplayed7);
        assertTrue(playerplayed7);
        assertTrue(playerplayed8);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnValidMoveAfterSecondCards()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        boolean playerplayed5 = this.playcheck("hodbub", "achiadg-poker-game");
        boolean playerplayed6 = this.playcheck("rotemw", "achiadg-poker-game");
        boolean playerplayed7 = this.playraise("ronenbu", "achiadg-poker-game", 250);
        boolean playerplayed8 = this.playfold("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed5);
        assertTrue(playerplayed6);
        assertTrue(playerplayed7);
        assertTrue(playerplayed8);
        boolean playerplayed9 = this.playfold("hodbub", "achiadg-poker-game");
        boolean playerplayed10 = this.playcall("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed9);
        assertTrue(playerplayed10);
        boolean playerplayed12 = this.playcheck("ronenbu", "achiadg-poker-game");
        boolean playerplayed11 = this.playcheck("rotemw", "achiadg-poker-game");
       assertTrue(playerplayed11);
        assertTrue(playerplayed12);
        leaveGames();
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
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        boolean playerplayed5 = this.playraise("hodbub", "achiadg-poker-game", 200);
        boolean playerplayed6 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed5);
        assertFalse(playerplayed6);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnInValidMoveSecondCards()
    {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        boolean playerplayed5 = this.playcheck("hodbub", "achiadg-poker-game");
        boolean playerplayed6 = this.playcheck("rotemw", "achiadg-poker-game");
        boolean playerplayed7 = this.playcheck("ronenbu", "achiadg-poker-game");
        boolean playerplayed8 = this.playcheck("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed5);
        assertTrue(playerplayed6);
        assertTrue(playerplayed7);
        assertTrue(playerplayed8);
        boolean playerplayed9 = this.playraise("hodbub", "achiadg-poker-game", 1000);
        boolean playerplayed10 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed9);
        assertFalse(playerplayed10);
        leaveGames();
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
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ro\tnenbu", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("achiadg", "ac\rhiadg-poker-game");
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-g\name");
        boolean playerplayed4 = this.playcheck("r\rotemw", "achiadg-poker-game");
        assertFalse(playerplayed1);
        assertFalse(playerplayed2);
        assertFalse(playerplayed3);
        assertFalse(playerplayed4);
        leaveGames();
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
        this.startgame("achiadg","achiadg-poker-game");
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game ; SELECT * FROM GAMES");
        assertFalse(playerplayed1);
        leaveGames();
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnUserLeagueUpdated()
    {
        registerUsers();
        loginUsers();
        addBalance();
        for(int i = 1; i <= 10; i++)
        {
            playGame(i);
        }

        int userleague1 = this.getuserleague("ronenbu");
        int userleague2 = this.getuserleague("rotemw");
        int userleague3 = this.getuserleague("hodbub");
        int userleague4 = this.getuserleague("achiadg");
        assertEquals(userleague1, 1);
        assertEquals(userleague2, 1);
        assertEquals(userleague3, 300);
        assertEquals(userleague4, 1);

        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnUserLeagueNoUpdated()
    {
        registerUsers();
        loginUsers();
        addBalance();
        for(int i = 1; i <= 9; i++)
        {
            playGame(i);
        }

        int userleague1 = this.getuserleague("achiadg");
        int userleague2 = this.getuserleague("hodbub");
        int userleague3 = this.getuserleague("ronenbu");
        int userleague4 = this.getuserleague("rotemw");
        assertEquals(userleague1, 0);
        assertEquals(userleague2, 0);
        assertEquals(userleague3, 0);
        assertEquals(userleague4, 0);

        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnUserLeagueUpdateTwoLeagues()
    {
        registerUsers();
        loginUsers();
        addBalance();
        for(int i = 1; i <= 10; i++)
        {
            playGame(i);
        }

        int userleague = this.getuserleague("hodbub");
        assertEquals(userleague, 300);

        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnUserLeagueNoUpdatedLessThan10Games()
    {
        registerUsers();
        loginUsers();
        addBalance();
        for(int i = 1; i <= 2; i++)
        {
            playGame(i);
        }

        int userleague1 = this.getuserleague("achiadg");
        int userleague2 = this.getuserleague("hodbub");
        int userleague3 = this.getuserleague("ronenbu");
        int userleague4 = this.getuserleague("rotemw");
        assertEquals(userleague1, 0);
        assertEquals(userleague2, 0);
        assertEquals(userleague3, 0);
        assertEquals(userleague4, 0);

        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnUserLeagueNoUpdatedInvalidCharecters()
    {
        registerUsers();
        loginUsers();
        addBalance();
        for(int i = 1; i <= 9; i++)
        {
            playGame(i);
        }

        int userleague = this.getuserleague("rotem\nw");
        assertEquals(userleague, -1);

        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testPlayTurnUserLeagueWithEmptyUser()
    {
        registerUsers();
        loginUsers();
        addBalance();
        for(int i = 1; i <= 9; i++)
        {
            playGame(i);
        }

        int userleague = this.getuserleague("");
        assertEquals(userleague, -1);

        logoutUsers();
        deleteUsers();
    }

    private void playGame(int i){
        createGames(String.valueOf(i));
        usersJoinsGames(String.valueOf(i));
        this.startgame("achiadg","achiadg-poker-game"+i);
        boolean playerplayed1 = this.playcall("ronenbu", "achiadg-poker-game"+i);
        boolean playerplayed2 = this.playcall("achiadg", "achiadg-poker-game"+i);
        boolean playerplayed3 = this.playcall("hodbub", "achiadg-poker-game"+i);
        boolean playerplayed4 = this.playcheck("rotemw", "achiadg-poker-game"+i);
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        assertTrue(playerplayed4);
        boolean playerplayed5 = this.playcheck("hodbub", "achiadg-poker-game"+i);
        boolean playerplayed6 = this.playcheck("rotemw", "achiadg-poker-game"+i);
        boolean playerplayed7 = this.playraise("ronenbu", "achiadg-poker-game"+i, 15);
        boolean playerplayed8 = this.playfold("achiadg", "achiadg-poker-game"+i);
        assertTrue(playerplayed5);
        assertTrue(playerplayed6);
        assertTrue(playerplayed7);
        assertTrue(playerplayed8);
        boolean playerplayed9 = this.playfold("hodbub", "achiadg-poker-game"+i);
        boolean playerplayed10 = this.playcall("rotemw", "achiadg-poker-game"+i);
        assertTrue(playerplayed9);
        assertTrue(playerplayed10);
        boolean playerplayed11 = this.playraise("ronenbu", "achiadg-poker-game"+i, 300);
        boolean playerplayed12 = this.playfold("rotemw", "achiadg-poker-game"+i);
        assertTrue(playerplayed11);
        assertTrue(playerplayed12);
        leaveGames(String.valueOf(i));
}

    private void createGames(String... gameNums) {
        String game="";
        for(String s : gameNums){game=s;}
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game"+game,  GamePolicy.NOLIMIT , 100, 100, 10, 2, 9, true);
    }

    private void usersJoinsGames(String... gameNums) {
        String game="";
        for(String s : gameNums){game=s;}
        boolean useraddedgame1 = this.joinexistinggame("hodbub" , "achiadg-poker-game"+game,false);
        boolean useraddedgame2 = this.joinexistinggame("rotemw" , "achiadg-poker-game"+game,false);
        boolean useraddedgame3 = this.joinexistinggame("ronenbu" , "achiadg-poker-game"+game,false);
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

    public void registerUsers() {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean useradded3 = this.registerUser("rotemw","rotemwald123","waldr@gmail.com",LocalDate.of(1991,11,26),null);
        boolean useradded4 = this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",LocalDate.of(1991,4,26),null);
    }

    public void leaveGames(String... gameNums) {
        String game="";
        for(String s : gameNums){game=s;}
        boolean closegame1 = this.leavegame("achiadg", "YES", "achiadg-poker-game"+game);
        boolean closegame2 = this.leavegame("rotemw" , "YES","achiadg-poker-game"+game);
        boolean closegame3 = this.leavegame("ronenbu" , "YES","achiadg-poker-game"+game);
        boolean closegame4 = this.leavegame("hodbub","YES","achiadg-poker-game"+game);
    }
}

