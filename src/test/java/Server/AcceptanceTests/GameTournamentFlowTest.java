package Server.AcceptanceTests;

import Enumerations.GamePolicy;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by RonenB on 5/13/2017.
 */
public class GameTournamentFlowTest extends ProjectTest {
    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testTournamentCorrectFlow() {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        /* FIRST TOURNAMENT ROUND */
        this.startgame("achiadg", "achiadg-poker-game");
        boolean playerplayed1 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed3 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        int potsize1 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize1, is(3 * 10));
        int playerbalance1 = this.getplayerbalance("rotemw", "achiadg-poker-game");
        assertThat(playerbalance1, is(90));

        /* FLOP */
        playerplayed1 = this.playcheck("hodbub", "achiadg-poker-game");
        playerplayed2 = this.playcheck("rotemw", "achiadg-poker-game");
        playerplayed3 = this.playcheck("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        int potsize2 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize2, is(3 * 10));
        int playerbalance2 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        assertThat(playerbalance2, is(90));

        /* RIVER */
        playerplayed1 = this.playcheck("hodbub", "achiadg-poker-game");
        playerplayed2 = this.playcheck("rotemw", "achiadg-poker-game");
        playerplayed3 = this.playcheck("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        int potsize3 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize3, is(3 * 10));
        int playerbalance3 = this.getplayerbalance("hodbub", "achiadg-poker-game");
        assertThat(playerbalance3, is(90));

        /* TURN */
        playerplayed1 = this.playraise("hodbub", "achiadg-poker-game", 90);
        playerplayed2 = this.playcall("rotemw", "achiadg-poker-game");
        playerplayed3 = this.playfold("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        int potsize4 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize4, is(10 * 3 + 90 * 2));

        String remainedPlayer;
        boolean left = false;
        if (this.getplayerbalance("hodbub", "achiadg-poker-game") == 0) {
            remainedPlayer = "rotemw";
            assertTrue(this.searchgamebyplayername("hodbub"));
            left = this.leavegame("hodbub", "YES", "achiadg-poker-game");
            assertFalse(this.searchgamebyplayername("hodbub"));
        } else if (this.getplayerbalance("rotemw", "achiadg-poker-game") == 0) {
            remainedPlayer = "hodbub";
            assertTrue(this.searchgamebyplayername("rotemw"));
            left = this.leavegame("rotemw", "YES", "achiadg-poker-game");
            assertFalse(this.searchgamebyplayername("rotemw"));
        } else
            return;
        assertTrue(left);

        int playerbalance4 = this.getplayerbalance(remainedPlayer, "achiadg-poker-game");
        assertThat(playerbalance4, is(210));
        int playerbalance5 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        assertThat(playerbalance5, is(90));


        /* SECOND TOURNAMENT ROUND */
        boolean started = this.startgame("achiadg", "achiadg-poker-game");
        assertTrue(started);
        playerplayed1 = this.playcall("achiadg", "achiadg-poker-game");
        playerplayed2 = this.playcheck(remainedPlayer, "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        potsize1 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize1, is(2 * 10));
        playerbalance1 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        assertThat(playerbalance1, is(80));
        playerbalance1 = this.getplayerbalance(remainedPlayer, "achiadg-poker-game");
        assertThat(playerbalance1, is(200));

        /* FLOP */
        playerplayed1 = this.playcheck("achiadg", "achiadg-poker-game");
        playerplayed2 = this.playcheck(remainedPlayer, "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        potsize2 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize2, is(2 * 10));
        playerbalance1 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        assertThat(playerbalance1, is(80));
        playerbalance1 = this.getplayerbalance(remainedPlayer, "achiadg-poker-game");
        assertThat(playerbalance1, is(200));

        /* RIVER */
        playerplayed1 = this.playcheck("achiadg", "achiadg-poker-game");
        playerplayed2 = this.playcheck(remainedPlayer, "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        potsize2 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize2, is(2 * 10));
        playerbalance1 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        assertThat(playerbalance1, is(80));
        playerbalance1 = this.getplayerbalance(remainedPlayer, "achiadg-poker-game");
        assertThat(playerbalance1, is(200));

        /* TURN */
        playerplayed1 = this.playraise("achiadg", "achiadg-poker-game", 80);
        playerplayed2 = this.playcall(remainedPlayer, "achiadg-poker-game");

        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        potsize4 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize4, is(10 * 2 + 80 * 2));

        String oldRemained = "";
        if (this.getplayerbalance("achiadg", "achiadg-poker-game") != 0) {
            oldRemained = remainedPlayer;
            remainedPlayer = "achiadg";
        }

        if (remainedPlayer.equals("achiadg")) {
            playerbalance1 = this.getplayerbalance("achiadg", "achiadg-poker-game");
            playerbalance2 = this.getplayerbalance(oldRemained, "achiadg-poker-game");
            assertThat(playerbalance1, is(180));
            assertThat(playerbalance2, is(120));
        } else {
            playerbalance1 = this.getplayerbalance("achiadg", "achiadg-poker-game");
            playerbalance2 = this.getplayerbalance(remainedPlayer, "achiadg-poker-game");
            assertThat(playerbalance1, is(0));
            assertThat(playerbalance2, is(300));
        }

        if (!remainedPlayer.equals("achiadg")) {
            this.leavegame("achiadg", "yes", "achiadg-poker-game");
            this.leavegame(remainedPlayer, "yes", "achiadg-poker-game");
        } else {
            this.leavegame(remainedPlayer, "yes", "achiadg-poker-game");
            this.leavegame("achiadg", "yes", "achiadg-poker-game");
        }

        logoutUsers();
        deleteUsers();
    }


    @Test
    public void testTournamentCorrectFlowAllIn() {
        registerUsers();
        loginUsers();
        addBalance();
        createGames();
        usersJoinsGames();
        /* FIRST TOURNAMENT ROUND */
        this.startgame("achiadg", "achiadg-poker-game");
        boolean playerplayed1 = this.playcall("achiadg", "achiadg-poker-game");
        boolean playerplayed2 = this.playcall("hodbub", "achiadg-poker-game");
        boolean playerplayed3 = this.playcheck("rotemw", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        int potsize1 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize1,is(3 * 10));
        int playerbalance1 = this.getplayerbalance("rotemw", "achiadg-poker-game");
        assertThat(playerbalance1,is(90));
        playerbalance1 = this.getplayerbalance("hodbub", "achiadg-poker-game");
        assertThat(playerbalance1,is(90));
        playerbalance1 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        assertThat(playerbalance1,is(90));

        /* FLOP */
        playerplayed1 = this.playraise("hodbub", "achiadg-poker-game",90);
        playerplayed2 = this.playcall("rotemw", "achiadg-poker-game");
        playerplayed3 = this.playcall("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        int potsize2 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize2,is(3 * 100));

        /* RIVER */
        playerplayed1 = this.playcheck("hodbub", "achiadg-poker-game");
        playerplayed2 = this.playcheck("rotemw", "achiadg-poker-game");
        playerplayed3 = this.playcheck("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);
        potsize2 = this.getpotsize("achiadg-poker-game");
        assertThat(potsize2,is(3 * 100));

        int playerbalance2 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        int playerbalance3 = this.getplayerbalance("rotemw", "achiadg-poker-game");
        int playerbalance4 = this.getplayerbalance("hodbub", "achiadg-poker-game");

        assertThat(playerbalance2,is(0));
        assertThat(playerbalance3,is(0));
        assertThat(playerbalance4,is(0));


        /* TURN */
        playerplayed1 = this.playcheck("hodbub", "achiadg-poker-game");
        playerplayed2 = this.playcheck("rotemw", "achiadg-poker-game");
        playerplayed3 = this.playcheck("achiadg", "achiadg-poker-game");
        assertTrue(playerplayed1);
        assertTrue(playerplayed2);
        assertTrue(playerplayed3);

        playerbalance2 = this.getplayerbalance("achiadg", "achiadg-poker-game");
        playerbalance3 = this.getplayerbalance("rotemw", "achiadg-poker-game");
        playerbalance4 = this.getplayerbalance("hodbub", "achiadg-poker-game");


        String playerToLeave1="";
        String playerToLeave2="";
        String playerToLeave3="";

        if(playerbalance2==150){
            if(playerbalance3==150)
                assertThat(playerbalance4,is(0));
            if(playerbalance4==150)
                assertThat(playerbalance3,is(0));
            playerToLeave1="hodbub";
            playerToLeave2="rotemw";
            playerToLeave3="achiadg";
        }
        else if(playerbalance3==150){
            if(playerbalance2==150)
                assertThat(playerbalance4,is(0));
            if(playerbalance4==150)
                assertThat(playerbalance2,is(0));
            playerToLeave1="hodbub";
            playerToLeave2="achiadg";
            playerToLeave3="rotemw";
        }
        else if(playerbalance4==150){
            if(playerbalance3==150)
                assertThat(playerbalance2,is(0));
            if(playerbalance2==150)
                assertThat(playerbalance3,is(0));
            playerToLeave1="rotemw";
            playerToLeave2="achiadg";
            playerToLeave3="hodbub";
        }
        else if(playerbalance2==300){
            assertThat(playerbalance3,is(0));
            assertThat(playerbalance4,is(0));

            playerToLeave1="rotemw";
            playerToLeave2="hodbub";
            playerToLeave3="achiadg";
        }
        else if(playerbalance3==300){
            assertThat(playerbalance2,is(0));
            assertThat(playerbalance4,is(0));
            playerToLeave1="achiadg";
            playerToLeave2="hodbub";
            playerToLeave3="rotemw";
        }
        else{
            assertThat(playerbalance2,is(0));
            assertThat(playerbalance3,is(0));
            assertThat(playerbalance4,is(300));
            playerToLeave1="rotemw";
            playerToLeave2="achiadg";
            playerToLeave3="hodbub";
        }

        this.leavegame(playerToLeave1,"yes","achiadg-poker-game");
        this.leavegame(playerToLeave2,"yes","achiadg-poker-game");
        this.leavegame(playerToLeave3,"yes","achiadg-poker-game");

        logoutUsers();
        deleteUsers();
    }

    private void createGames()
    {
        boolean gamecreated1 = this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 100, 100, 10, 2, 9, true);
    }

    public void leaveGames() {
        this.leavegame("achiadg", "YES", "achiadg-poker-game");
        this.leavegame("rotemw" , "YES","achiadg-poker-game");
        this.leavegame("hodbub","YES","achiadg-poker-game");
    }

    private void usersJoinsGames() {
        this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
    }

    public void deleteUsers() {
        this.deleteUser("achiadg");
        this.deleteUser("hodbub");
        this.deleteUser("rotemw");
    }

    public void logoutUsers() {
        this.logout("achiadg");
        this.logout("hodbub");
        this.logout("rotemw");
    }

    public void addBalance() {
        this.addbalancetouserwallet("achiadg",20000);
        this.addbalancetouserwallet("hodbub",40000);
        this.addbalancetouserwallet("rotemw",20000);
    }

    public void loginUsers() {
        this.login("achiadg","aChi12#*");
        this.login("hodbub","hBublil1308");
        this.login("rotemw","rotemwald123");
    }

    public void registerUsers()
    {
        this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        this.registerUser("rotemw","rotemwald123","waldr@gmail.com",LocalDate.of(1991,11,26),null);
    }

}


