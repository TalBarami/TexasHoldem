
package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.events.gameFlowEvents.GameEvent;
import TexasHoldem.domain.game.GameActions;
import TexasHoldem.domain.game.GamePolicy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class ReplayNonActiveGamesTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
        registerUsers();
        loginUsers();
        addBalance();
    }

    @After
    public void teardown(){
        logoutUsers();
        deleteUsers();
    }

    @Test
    public void testNoReplayForActiveGames()
    {
        createGames();
        usersJoinsGames();
        List<GameEvent> ans=this.replaynonactivegame("achiadg-poker-game");
        assertNull(ans);
    }

    @Test
    public void testReplayForGameJustCreatedEnteredAndExitedAmount()
    {
        createGames();
        usersJoinsGames();
        leaveGames();
        List<GameEvent> ans=this.replaynonactivegame("achiadg-poker-game");
        assertThat(ans.size(), is(7));
    }

    @Test
    public void testReplayForGameJustCreatedEnteredAndExitedEventTime() {
        createGames();
        usersJoinsGames();
        leaveGames();
        List<GameEvent> ans = this.replaynonactivegame("achiadg-poker-game");
        assertTrue(ans.get(0).getEventTime().compareTo(ans.get(1).getEventTime())<=0);
        assertTrue(ans.get(1).getEventTime().compareTo(ans.get(2).getEventTime())<=0);
        assertTrue(ans.get(2).getEventTime().compareTo(ans.get(3).getEventTime())<=0);
        assertTrue(ans.get(3).getEventTime().compareTo(ans.get(4).getEventTime())<=0);
        assertTrue(ans.get(4).getEventTime().compareTo(ans.get(5).getEventTime())<=0);
        assertTrue(ans.get(5).getEventTime().compareTo(ans.get(6).getEventTime())<=0);
    }

    @Test
    public void testReplayForGameJustCreatedEnteredAndExitedEventInitiators()
    {
        createGames();
        usersJoinsGames();
        leaveGames();
        List<GameEvent> ans=this.replaynonactivegame("achiadg-poker-game");
        assertTrue(ans.get(0).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(1).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(2).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(3).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(4).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(5).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(6).getEventInitiator().getUser().getUsername().equals("hodbub"));
    }

    @Test
    public void testReplayForGameJustCreatedEnteredAndExitedEventAction()
    {
        createGames();
        usersJoinsGames();
        leaveGames();
        List<GameEvent> ans=this.replaynonactivegame("achiadg-poker-game");
        assertThat(ans.get(0).getEventAction(),is(GameActions.ENTER));
        assertThat(ans.get(1).getEventAction(),is(GameActions.ENTER));
        assertThat(ans.get(2).getEventAction(),is(GameActions.ENTER));
        assertThat(ans.get(3).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(4).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(5).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(6).getEventAction(),is(GameActions.CLOSED));
    }

    @Test
    public void testReplayForTournamentAllInAmountEvents() {
        createGames();
        usersJoinsGames();

        String gameName = playTournamentAllIn();
        List<GameEvent> res=this.replaynonactivegame(gameName);
        assertThat(res.size(),is(20));
    }

    @Test
    public void testReplayForTournamentAllInEventTime() {
        createGames();
        usersJoinsGames();

        String gameName = playTournamentAllIn();
        List<GameEvent> ans=this.replaynonactivegame(gameName);
        assertTrue(ans.get(0).getEventTime().compareTo(ans.get(1).getEventTime())<=0);
        assertTrue(ans.get(1).getEventTime().compareTo(ans.get(2).getEventTime())<=0);
        assertTrue(ans.get(2).getEventTime().compareTo(ans.get(3).getEventTime())<=0);
        assertTrue(ans.get(3).getEventTime().compareTo(ans.get(4).getEventTime())<=0);
        assertTrue(ans.get(4).getEventTime().compareTo(ans.get(5).getEventTime())<=0);
        assertTrue(ans.get(5).getEventTime().compareTo(ans.get(6).getEventTime())<=0);
    }

    @Test
    public void testReplayForTournamentAllInEventInitiators()
    {
        createGames();
        usersJoinsGames();

        String gameName = playTournamentAllIn();
        List<GameEvent> ans=this.replaynonactivegame(gameName);
        int x=5;
        assertTrue(ans.get(0).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(1).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(2).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(3).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(4).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(5).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(6).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(7).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(8).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(9).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(10).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(11).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(12).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(13).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(14).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(15).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(16).getEventInitiator().getUser().getUsername().equals("achiadg"));
        assertTrue(ans.get(17).getEventInitiator().getUser().getUsername().equals("rotemw"));
        assertTrue(ans.get(18).getEventInitiator().getUser().getUsername().equals("hodbub"));
        assertTrue(ans.get(19).getEventInitiator().getUser().getUsername().equals("hodbub"));
    }

    @Test
    public void testReplayForTournamentAllInEventAction()
    {
        createGames();
        usersJoinsGames();

        String gameName = playTournamentAllIn();
        List<GameEvent> ans=this.replaynonactivegame(gameName);

        assertThat(ans.get(0).getEventAction(),is(GameActions.ENTER));
        assertThat(ans.get(1).getEventAction(),is(GameActions.ENTER));
        assertThat(ans.get(2).getEventAction(),is(GameActions.ENTER));
        assertThat(ans.get(3).getEventAction(),is(GameActions.NEWROUND));
        assertThat(ans.get(4).getEventAction(),is(GameActions.CALL));
        assertThat(ans.get(5).getEventAction(),is(GameActions.CALL));
        assertThat(ans.get(6).getEventAction(),is(GameActions.CHECK));
        assertThat(ans.get(7).getEventAction(),is(GameActions.RAISE));
        assertThat(ans.get(8).getEventAction(),is(GameActions.CALL));
        assertThat(ans.get(9).getEventAction(),is(GameActions.CALL));
        assertThat(ans.get(10).getEventAction(),is(GameActions.CHECK));
        assertThat(ans.get(11).getEventAction(),is(GameActions.CHECK));
        assertThat(ans.get(12).getEventAction(),is(GameActions.CHECK));
        assertThat(ans.get(13).getEventAction(),is(GameActions.CHECK));
        assertThat(ans.get(14).getEventAction(),is(GameActions.CHECK));
        assertThat(ans.get(15).getEventAction(),is(GameActions.CHECK));
        assertThat(ans.get(16).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(17).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(18).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(19).getEventAction(),is(GameActions.CLOSED));
    }



    private String playTournamentAllIn(){
        /* FIRST TOURNAMENT ROUND */
        this.startgame("achiadg", "achiadg-poker-game");

        this.playcall("achiadg", "achiadg-poker-game");
        this.playcall("hodbub", "achiadg-poker-game");
        this.playcheck("rotemw", "achiadg-poker-game");


        /* FLOP */
        this.playraise("hodbub", "achiadg-poker-game",90);
        this.playcall("rotemw", "achiadg-poker-game");
        this.playcall("achiadg", "achiadg-poker-game");

        /* RIVER */
        this.playcheck("hodbub", "achiadg-poker-game");
        this.playcheck("rotemw", "achiadg-poker-game");
        this.playcheck("achiadg", "achiadg-poker-game");


        /* TURN */
        this.playcheck("hodbub", "achiadg-poker-game");
        this.playcheck("rotemw", "achiadg-poker-game");
        this.playcheck("achiadg", "achiadg-poker-game");

        leaveGames();

        return "achiadg-poker-game";
    }

    public void deleteUsers() {
        this.deleteUser("achiadg");
        deleteUser("hodbub");
        deleteUser("rotemw");
        deleteUser("ronenbu");
    }

    public void logoutUsers() {
        this.logout("achiadg");
        this.logout("hodbub");
        this.logout("rotemw");
        this.logout("ronenbu");
    }

    public void addBalance() {
        this.addbalancetouserwallet("achiadg",20000);
        this.addbalancetouserwallet("hodbub",40000);
        this.addbalancetouserwallet("rotemw",20000);
        this.addbalancetouserwallet("ronenbu",30000);
    }

    public void loginUsers() {
        this.login("achiadg","aChi12#*");
        this.login("hodbub","hBublil1308");
        this.login("rotemw","rotemwald123");
        this.login("ronenbu","ronenbu123");
    }

    public void registerUsers()
    {
        this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        this.registerUser("rotemw","rotemwald123","waldr@gmail.com",LocalDate.of(1991,11,26),null);
        this.registerUser("ronenbu","ronenbu123","butirevr@gmail.com",LocalDate.of(1991,4,26),null);
    }

    private void createGames()
    {
        this.createnewgame("achiadg","achiadg-poker-game",  GamePolicy.NOLIMIT , 100, 100, 10, 2, 9, true);
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
}
