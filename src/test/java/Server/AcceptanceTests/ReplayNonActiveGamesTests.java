
package Server.AcceptanceTests;

import Server.data.Hybernate.HibernateUtil;
import Server.data.users.Users;
import Server.domain.events.SystemEvent;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.events.gameFlowEvents.MoveEvent;
import Server.domain.game.GameActions;
import Enumerations.GamePolicy;
import Server.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;


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
    public void tearDown() {
        logoutUsers();
        deleteUsers();
        Users.getUsersInGame().clear();
        super.clearAllEventsFromDB();
        super.clearAllUsersFromDB();
    }
    
    @Test
    public void testNoReplayForActiveGames()
    {
        createGames();
        usersJoinsGames();
        List<GameEvent> ans=this.replaynonactivegame("achiadg-poker-game");
        assertNull(ans);
        leaveGames();
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
        assertTrue(ans.get(0).getCreatorUserName().equals("achiadg"));
        assertTrue(ans.get(1).getCreatorUserName().equals("hodbub"));
        assertTrue(ans.get(2).getCreatorUserName().equals("rotemw"));
        assertTrue(ans.get(3).getCreatorUserName().equals("achiadg"));
        assertTrue(ans.get(4).getCreatorUserName().equals("rotemw"));
        assertTrue(ans.get(5).getCreatorUserName().equals("hodbub"));
        assertTrue(ans.get(6).getCreatorUserName().equals("hodbub"));
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
        assertThat(res.size(),is(14));
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
        assertTrue(ans.get(0).getCreatorUserName().equals("achiadg"));
        assertTrue(ans.get(1).getCreatorUserName().equals("hodbub"));
        assertTrue(ans.get(2).getCreatorUserName().equals("rotemw"));
        assertTrue(ans.get(3).getCreatorUserName().equals("achiadg"));
        assertTrue(ans.get(4).getCreatorUserName().equals("achiadg"));
        assertTrue(ans.get(5).getCreatorUserName().equals("hodbub"));
        assertTrue(ans.get(6).getCreatorUserName().equals("rotemw"));
        assertTrue(ans.get(7).getCreatorUserName().equals("hodbub"));
        assertTrue(ans.get(8).getCreatorUserName().equals("rotemw"));
        assertTrue(ans.get(9).getCreatorUserName().equals("achiadg"));
        assertTrue(ans.get(10).getCreatorUserName().equals("achiadg"));
        assertTrue(ans.get(11).getCreatorUserName().equals("rotemw"));
        assertTrue(ans.get(12).getCreatorUserName().equals("hodbub"));
        assertTrue(ans.get(13).getCreatorUserName().equals("hodbub"));
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
        assertThat(ans.get(10).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(11).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(12).getEventAction(),is(GameActions.EXIT));
        assertThat(ans.get(13).getEventAction(),is(GameActions.CLOSED));
    }

    @Test
    public void testReplayAfterGameEnds()
    {

    }

    private String playTournamentAllIn(){
        /* FIRST TOURNAMENT ROUND */
        this.startgame("achiadg", "achiadg-poker-game");
        realSleep(1000);

        this.playcall("achiadg", "achiadg-poker-game");
        realSleep(1000);
        this.playcall("hodbub", "achiadg-poker-game");
        realSleep(1000);
        this.playcheck("rotemw", "achiadg-poker-game");
        realSleep(1000);


        /* FLOP */
        this.playraise("hodbub", "achiadg-poker-game",90);
        realSleep(1000);
        this.playcall("rotemw", "achiadg-poker-game");
        realSleep(1000);
        this.playcall("achiadg", "achiadg-poker-game");
        realSleep(1000);

        /* RIVER */
        this.playcheck("hodbub", "achiadg-poker-game");
        realSleep(1000);
        this.playcheck("rotemw", "achiadg-poker-game");
        realSleep(1000);
        this.playcheck("achiadg", "achiadg-poker-game");
        realSleep(1000);


        /* TURN */
        this.playcheck("hodbub", "achiadg-poker-game");
        realSleep(1000);
        this.playcheck("rotemw", "achiadg-poker-game");
        realSleep(1000);
        this.playcheck("achiadg", "achiadg-poker-game");
        realSleep(1000);

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
        realSleep(1000);
    }

    public void leaveGames() {
        this.leavegame("achiadg", "YES", "achiadg-poker-game");
        realSleep(1000);
        this.leavegame("rotemw" , "YES","achiadg-poker-game");
        realSleep(1000);
        this.leavegame("hodbub","YES","achiadg-poker-game");
        realSleep(1000);
    }

    private void usersJoinsGames() {
        this.joinexistinggame("hodbub" , "achiadg-poker-game",false);
        realSleep(1000);
        this.joinexistinggame("rotemw" , "achiadg-poker-game",false);
        realSleep(1000);
    }

    private void realSleep(int mili){
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
