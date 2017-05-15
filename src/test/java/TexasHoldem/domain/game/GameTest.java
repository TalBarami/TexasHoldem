package TexasHoldem.domain.game;


import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.game.participants.Spectator;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
import static TexasHoldem.domain.game.GamePolicy.*;

import org.junit.*;

import javax.jws.soap.SOAPBinding;
import java.lang.reflect.Method;
import java.time.LocalDate;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class GameTest {
    private GameSettings tournamentGameSettings;
    private GameSettings realMoneyGameSettings;
    //private User testUser1;
    //private User testUser2;
    //private User testUser3;
    //private User testUser4;

    @Before
    public void setUp() throws Exception {
        tournamentGameSettings =new GameSettings("tournamentTest",LIMIT,100,100,100,100,2,4,true);
        realMoneyGameSettings =new GameSettings("realMoneyTest",LIMIT,100,100,100,0,2,4,false);
        //testUser1=new User("testUser1","00000","test1@gmail.com", LocalDate.of(1111,10,10),null);
        //testUser2=new User("testUser2","11111","test2@gmail.com", LocalDate.of(2222,10,10),null);
        //testUser3=new User("testUser3","22222","test3@gmail.com", LocalDate.of(3333,10,10),null);
        //testUser4=new User("testUser4","33333","test4@gmail.com", LocalDate.of(4444,10,10),null);
        //testUser1.deposit(1500,true);
        //testUser2.deposit(2500,true);
    }

    @After
    public void tearDown() throws Exception {
        tournamentGameSettings =null;
        realMoneyGameSettings=null;
        //testUser1=null;
        //testUser2=null;
        //testUser3=null;
        //testUser4=null;
    }


    @Test
    public void joinGameAsPlayerTournamentTest() throws Exception {
        User user1= mock(User.class);
        User user2= mock(User.class);

        Game game=new Game(tournamentGameSettings,user1,null);
        assertThat(game.getPlayers().size(),is(1));//Creator automatically joins the game
        game.joinGameAsPlayer(user2);
        assertThat(game.getPlayers().size(),is(2));
        Assert.assertTrue(game.getSpectators().isEmpty());

        verify(user1,times(1)).withdraw(game.getBuyInPolicy(), false);
        verify(user2,times(1)).withdraw(game.getBuyInPolicy(), false);
    }

    @Test
    public void joinGameAsPlayerRealMoneyTest() throws Exception {
        User user1= mock(User.class);
        User user2= mock(User.class);

        Game game=new Game(realMoneyGameSettings,user1,null);
        assertThat(game.getPlayers().size(),is(1));//Creator automatically joins the game
        game.joinGameAsPlayer(user2);
        assertThat(game.getPlayers().size(),is(2));

        Assert.assertTrue(game.getSpectators().isEmpty());
        verify(user1,times(0)).withdraw(game.getBuyInPolicy(), false);
        verify(user2,times(0)).withdraw(game.getBuyInPolicy(), false);
    }


    @Test
    public void joinGameAsSpectatorInTournament() throws Exception {
        User user1= mock(User.class);
        User user2= mock(User.class);

        Game game=new Game(tournamentGameSettings,user1,null);
        assertThat(game.getPlayers().size(),is(1));//Creator automatically joins the game
        Assert.assertTrue(game.getSpectators().isEmpty());

        game.joinGameAsSpectator(user2);
        assertThat(game.getPlayers().size(),is(1));
        assertThat(game.getSpectators().size(),is(1));

        verify(user1,times(1)).withdraw(game.getBuyInPolicy(), false);
        verify(user2,times(0)).withdraw(game.getBuyInPolicy(), false);
    }

    @Test
    public void joinGameAsSpectatorInRealGame() throws Exception {
        User user1= mock(User.class);
        User user2= mock(User.class);

        Game game=new Game(realMoneyGameSettings,user1,null);
        assertThat(game.getPlayers().size(),is(1));//Creator automatically joins the game
        Assert.assertTrue(game.getSpectators().isEmpty());
        game.joinGameAsSpectator(user2);
        assertThat(game.getPlayers().size(),is(1));
        assertThat(game.getSpectators().size(),is(1));

        verify(user1,times(0)).withdraw(game.getBuyInPolicy(), false);
        verify(user2,times(0)).withdraw(game.getBuyInPolicy(), false);
    }

    @Test
    public void starGameEnoughPlayers() throws Exception {
        User user1= mock(User.class);
        User user2= mock(User.class);
        User user3= mock(User.class);

        Game game=new Game(tournamentGameSettings,user1,null);

        assertThat(game.getRounds().size(),is(0));
        game.joinGameAsPlayer(user2);
        game.joinGameAsPlayer(user3);
        assertThat(game.getDealerIndex(),is(0));
        game.startGame(null);
        assertThat(game.getDealerIndex(),is(1));
        assertThat(game.getRounds().size(),is(1));

        verify(user1,times(1)).withdraw(game.getBuyInPolicy(), false);
        verify(user2,times(1)).withdraw(game.getBuyInPolicy(), false);
        verify(user3,times(1)).withdraw(game.getBuyInPolicy(), false);
    }

    @Test
    public void starGameNotEnoughPlayers() throws Exception {
        User user1= mock(User.class);

        Game game=new Game(tournamentGameSettings,user1,null);
        assertThat(game.getRounds().size(),is(0));
        assertThat(game.getDealerIndex(),is(0));

        try{
            game.startGame(null);
            fail();
        }catch(Exception e){
            assertThat(game.getDealerIndex(),is(0));
            assertThat(game.getRounds().size(),is(0));
            verify(user1,times(1)).withdraw(game.getBuyInPolicy(), false);
        }
    }

    @Test
    public void removeParticipant() throws Exception {
        LeagueManager leagueManager= mock(LeagueManager.class);
        User user1= mock(User.class);
        User user2= mock(User.class);
        User user3= mock(User.class);

        Player player1=mock(Player.class);
        Spectator spec1=mock(Spectator.class);
        Spectator spec2=mock(Spectator.class);

        when(player1.getUser()).thenReturn(user1);
        when(spec1.getUser()).thenReturn(user2);
        when(spec2.getUser()).thenReturn(user3);

        Game game=new Game(tournamentGameSettings,user1,leagueManager);

        assertThat(game.getPlayers().size(),is(1));
        assertThat(game.getSpectators().size(),is(0));
        game.addSpectator(spec1);
        game.addSpectator(spec2);
        assertThat(game.getSpectators().size(),is(2));
        assertThat(game.getPlayers().size(),is(1));
        Player test1Player=game.getPlayers().get(0);
        game.removeParticipant(test1Player);
        assertThat(game.getPlayers().size(),is(0));
        assertThat(game.getSpectators().size(),is(2));
        Spectator test2Spectator=game.getSpectators().get(0);
        game.removeParticipant(test2Spectator);
        assertThat(game.getPlayers().size(),is(0));
        assertThat(game.getSpectators().size(),is(1));

        verify(leagueManager,times(1)).updateUserLeague(user1);
        verify(leagueManager,times(0)).updateUserLeague(user2);
        verify(user1,times(1)).withdraw(game.getBuyInPolicy(), false);
        verify(user2,times(0)).withdraw(game.getBuyInPolicy(), false);
        verify(user3,times(0)).withdraw(game.getBuyInPolicy(), false);
    }


    @Test
    public void isFullTestWithPlayers() throws Exception {
        User user1=mock(User.class);
        User user2=mock(User.class);
        User user3=mock(User.class);
        User user4=mock(User.class);

        Game game=new Game(tournamentGameSettings,user1,null);
        assertFalse(game.isFull());
        game.joinGameAsPlayer(user2);
        game.joinGameAsPlayer(user3);
        assertFalse(game.isFull());
        game.joinGameAsPlayer(user4);
        Assert.assertTrue(game.isFull());
    }

    @Test
    public void isFullTestWithSpectators() throws Exception {
        User user1=mock(User.class);
        User user2=mock(User.class);
        User user3=mock(User.class);
        User user4=mock(User.class);
        User user5=mock(User.class);

        Game game=new Game(tournamentGameSettings,user1,null);

        assertFalse(game.isFull());
        game.joinGameAsPlayer(user2);
        game.joinGameAsPlayer(user3);
        assertFalse(game.isFull());
        game.joinGameAsSpectator(user4);
        Assert.assertFalse(game.isFull());
        game.joinGameAsSpectator(user5);
        Assert.assertFalse(game.isFull());
    }

    @Test
    public void canBeSpectated() throws Exception {
        User user1=mock(User.class);

        Game game=new Game(tournamentGameSettings,user1,null);
        Assert.assertTrue(game.canBeSpectated());

        game=new Game(realMoneyGameSettings,user1,null);
        assertFalse(game.canBeSpectated());
    }

    @Test
    public void realMoneyGame() throws Exception {
        User user1=mock(User.class);
        Game game=new Game(tournamentGameSettings,user1,null);
        assertFalse(game.realMoneyGame());

        game=new Game(realMoneyGameSettings,user1,null);
        assertTrue(game.realMoneyGame());
    }

    @Test
    public void isActive() throws Exception {
        User user1=mock(User.class);
        Game game=new Game(realMoneyGameSettings,user1,null);
        assertTrue(game.isActive());

        game=new Game(tournamentGameSettings,user1,null);
        assertTrue(game.isActive());
    }
}