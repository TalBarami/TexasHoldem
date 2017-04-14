package TexasHoldem.domain.game;

import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.game.participants.Spectator;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
import static TexasHoldem.domain.game.GameSettings.GamePolicy.*;

import org.junit.*;

import java.time.LocalDate;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameTest {

    private LeagueManager leagueManager;
    private GameSettings tournamentGameSettings;
    private GameSettings realMoneyGameSettings;
    private User testUser1;
    private User testUser2;
    private User testUser3;
    private User testUser4;

    @Before
    public void setUp() throws Exception {
        tournamentGameSettings =new GameSettings("tournamentTest",LIMIT,100,100,100,100,2,4,true);
        realMoneyGameSettings =new GameSettings("realMoneyTest",LIMIT,100,100,100,0,2,4,false);
        testUser1=new User("testUser1","00000","test1@gmail.com", LocalDate.of(1111,10,10),null);
        testUser2=new User("testUser2","11111","test2@gmail.com", LocalDate.of(2222,10,10),null);
        testUser3=new User("testUser3","22222","test3@gmail.com", LocalDate.of(3333,10,10),null);
        testUser4=new User("testUser4","33333","test4@gmail.com", LocalDate.of(4444,10,10),null);
        testUser1.deposit(1500,true);
        testUser2.deposit(2500,true);
        leagueManager =new LeagueManager();
    }

    @After
    public void tearDown() throws Exception {
        tournamentGameSettings =null;
        realMoneyGameSettings=null;
        leagueManager =null;
        testUser1=null;
        testUser2=null;
        testUser3=null;
        testUser4=null;
    }


    @Test
    public void joinGameAsPlayerTournamentTest() throws Exception {
        Game game=new Game(tournamentGameSettings,testUser1,null);
        int previousUserBalance=testUser2.getBalance();
        assertThat(game.getPlayers().size(),is(1));//Creator automatically joins the game
        game.joinGameAsPlayer(testUser2);
        assertThat(game.getPlayers().size(),is(2));
        assertThat(testUser2.getBalance(),is(previousUserBalance- tournamentGameSettings.getBuyInPolicy()));
        Assert.assertTrue(testUser2.getGamePlayerMappings().containsKey(game));
        Assert.assertTrue(game.getSpectators().isEmpty());
    }

    @Test
    public void joinGameAsPlayerRealMoneyTest() throws Exception {
        Game game=new Game(realMoneyGameSettings,testUser1,null);
        int previousUserBalance=testUser2.getBalance();
        assertThat(game.getPlayers().size(),is(1));//Creator automatically joins the game
        Assert.assertFalse(testUser2.getGamePlayerMappings().containsKey(game));
        game.joinGameAsPlayer(testUser2);
        assertThat(game.getPlayers().size(),is(2));
        assertThat(testUser2.getBalance(),is(previousUserBalance));
        Assert.assertTrue(testUser2.getGamePlayerMappings().containsKey(game));
        Assert.assertTrue(game.getSpectators().isEmpty());
    }

    @Test
    public void joinGameAsSpectator() throws Exception {
        Game game=new Game(realMoneyGameSettings,testUser1,null);
        int previousUserBalance=testUser2.getBalance();
        assertThat(game.getPlayers().size(),is(1));//Creator automatically joins the game
        Assert.assertTrue(game.getSpectators().isEmpty());
        Assert.assertFalse(testUser2.getGamePlayerMappings().containsKey(game));
        game.joinGameAsSpectator(testUser2);
        assertThat(game.getPlayers().size(),is(1));
        assertThat(game.getSpectators().size(),is(1));
        Assert.assertTrue(testUser2.getGamePlayerMappings().containsKey(game));
        assertThat(testUser2.getBalance(),is(previousUserBalance));
        Assert.assertTrue(testUser2.getGamePlayerMappings().containsKey(game));

        Assert.assertFalse(game.isFull());
        game.joinGameAsSpectator(testUser3);
        game.joinGameAsSpectator(testUser4);
        Assert.assertFalse(game.isFull());
    }

    @Test
    public void startNewRound() throws Exception {
        Game game=new Game(tournamentGameSettings,testUser1,leagueManager);
        try {
            game.startNewRound();
            fail("expected exception was not thrown.");
        } catch(Exception e) {}

        assertThat(game.getRounds().size(),is(0));
        game.joinGameAsPlayer(testUser2);
        game.joinGameAsPlayer(testUser3);
        assertThat(game.getDealerIndex(),is(0));
        game.startNewRound(); //todo : delete later when start round will be working (else loop and waiting).
        assertThat(game.getDealerIndex(),is(1));
        assertThat(game.getRounds().size(),is(1));
    }

    @Test
    public void removeParticipant() throws Exception {
        Game game=new Game(tournamentGameSettings,testUser1,leagueManager);
        assertThat(game.getPlayers().size(),is(1));
        assertThat(game.getSpectators().size(),is(0));
        game.joinGameAsSpectator(testUser2);
        game.joinGameAsSpectator(testUser3);
        game.joinGameAsSpectator(testUser4);
        assertThat(game.getSpectators().size(),is(3));
        assertThat(game.getPlayers().size(),is(1));
        Player test1Player=game.getPlayers().stream().filter(player -> player.getUser().getEmail().equals(testUser1.getEmail())).findFirst().get();
        game.removeParticipant(test1Player);
        assertThat(game.getPlayers().size(),is(0));
        assertThat(game.getSpectators().size(),is(3));
        Spectator test2Spectator=game.getSpectators().stream().filter(sepc -> sepc.getUser().getEmail().equals(testUser2.getEmail())).findFirst().get();
        game.removeParticipant(test2Spectator);
        assertThat(game.getPlayers().size(),is(0));
        assertThat(game.getSpectators().size(),is(2));
    }


    @Test
    public void isFullTest() throws Exception {
        Game game=new Game(tournamentGameSettings,testUser1,null);
        Assert.assertFalse(game.isFull());
        game.joinGameAsPlayer(testUser2);
        game.joinGameAsPlayer(testUser3);
        Assert.assertFalse(game.isFull());
        game.joinGameAsPlayer(testUser4);
        Assert.assertTrue(game.isFull());
    }

    @Test
    public void canBeSpectated() throws Exception {
        Game game=new Game(tournamentGameSettings,testUser1,null);
        Assert.assertTrue(game.canBeSpectated());

        game=new Game(realMoneyGameSettings,testUser1,null);
        Assert.assertFalse(game.canBeSpectated());
    }

    @Test
    public void realMoneyGame() throws Exception {
        Game game=new Game(tournamentGameSettings,testUser1,null);
        Assert.assertFalse(game.realMoneyGame());

        game=new Game(realMoneyGameSettings,testUser1,null);
        Assert.assertTrue(game.realMoneyGame());
    }

    @Test
    public void isActive() throws Exception {
    }
}