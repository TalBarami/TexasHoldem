package TexasHoldem.domain.game;

import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by RotemWald on 11/04/2017.
 */
public class RoundTest {
    Round round1;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void setRound1() {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);
        Player player3 = new Player(user3, 100, 100);
        Player player4 = new Player(user4, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GameSettings.GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        this.round1 = round1;
    }

    @Test
    public void round1_testPaySmallAndBigBlind() throws Exception {
        int smallBlindAmount = 5;
        int bigBlindAmount = 10;

        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);
        Player player3 = new Player(user3, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);

        GameSettings settings1 = new GameSettings("Game1", GameSettings.GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();
        int smallPlayerOldChipAmount = smallPlayer.getChipsAmount();
        int bigPlayerOldChipAmount = bigPlayer.getChipsAmount();

        Method method = Round.class.getDeclaredMethod("paySmallAndBigBlind", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertTrue(smallPlayer.getChipsAmount() == smallPlayerOldChipAmount - smallBlindAmount);
        Assert.assertTrue(bigPlayer.getChipsAmount() == bigPlayerOldChipAmount - bigBlindAmount);
    }

    @Test
    public void round1_testPlayPreFlopRound() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GameSettings.GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        when(player1.chooseAction(any())).thenReturn(GameActions.FOLD);
        when(player2.chooseAction(any())).thenReturn(GameActions.FOLD);
        when(player3.chooseAction(any())).thenReturn(GameActions.FOLD);
        when(player4.chooseAction(any())).thenReturn(GameActions.FOLD);

        // when(player2.chooseAmountToRaise(any())).thenReturn(10);

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);
        when(player3.getUser()).thenReturn(user3);
        when(player4.getUser()).thenReturn(user4);

        double dealerOldRealCurrency = player1.getUser().getBalance();
        double smallPlayerOldRealCurrency = player2.getUser().getBalance();
        double bigPlayerOldRealCurrency = player3.getUser().getBalance();
        double lastPlayerOldRealCurrency = player4.getUser().getBalance();

        round1.setRoundActive(true);
        Method method = Round.class.getDeclaredMethod("playPreFlopRound", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertTrue(round1.getPotAmount() == 0);
        Assert.assertTrue(round1.getActivePlayers().size() == 1);

        Assert.assertTrue(player1.getUser().getBalance() == dealerOldRealCurrency);
        Assert.assertTrue(player2.getUser().getBalance() == smallPlayerOldRealCurrency);
        Assert.assertTrue(player3.getUser().getBalance() == bigPlayerOldRealCurrency);
        Assert.assertTrue(player4.getUser().getBalance() == lastPlayerOldRealCurrency);
    }

    @Test
    public void round1_testPlayFlopOrTurnRound() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GameSettings.GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        round1.initLastPlayer();

        when(player1.chooseAction(any())).thenReturn(GameActions.CHECK);
        when(player2.chooseAction(any())).thenReturn(GameActions.CHECK);
        when(player3.chooseAction(any())).thenReturn(GameActions.CHECK);
        when(player4.chooseAction(any())).thenReturn(GameActions.CHECK);

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);
        when(player3.getUser()).thenReturn(user3);
        when(player4.getUser()).thenReturn(user4);

        round1.setRoundActive(true);
        Method method = Round.class.getDeclaredMethod("playFlopOrTurnRound", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        verify(player1).chooseAction(any());
        verify(player2).chooseAction(any());
        verify(player3).chooseAction(any());
        verify(player4).chooseAction(any());

        Assert.assertTrue(round1.getPotAmount() == 0);
        Assert.assertTrue(round1.getActivePlayers().size() == 4);
    }

    @Test
    public void round1_testPlayRiverRound() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GameSettings.GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        when(player1.chooseAction(any())).thenReturn(GameActions.RAISE);
        when(player2.chooseAction(any())).thenReturn(GameActions.RAISE).thenReturn(GameActions.CALL);
        when(player3.chooseAction(any())).thenReturn(GameActions.FOLD);
        when(player4.chooseAction(any())).thenReturn(GameActions.CALL).thenReturn(GameActions.FOLD);

        when(player2.getLastBetSinceCardOpen()).thenReturn(0).thenReturn(0).thenReturn(10);

        when(player2.chooseAmountToRaise(any(int.class))).thenReturn(10);
        when(player2.payChips(10)).thenReturn(10).thenReturn(10);
        when(player4.payChips(10)).thenReturn(10);
        when(player1.chooseAmountToRaise(any(int.class))).thenReturn(20);
        when(player1.payChips(20)).thenReturn(20);

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);
        when(player3.getUser()).thenReturn(user3);
        when(player4.getUser()).thenReturn(user4);

        double dealerOldRealCurrency = player1.getUser().getBalance();
        double smallPlayerOldRealCurrency = player2.getUser().getBalance();
        double bigPlayerOldRealCurrency = player3.getUser().getBalance();
        double lastPlayerOldRealCurrency = player4.getUser().getBalance();

        round1.setRoundActive(true);
        round1.initLastPlayer();
        Method method = Round.class.getDeclaredMethod("playRiverRound", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertTrue(round1.getPotAmount() == 50);
        Assert.assertTrue(round1.getActivePlayers().size() == 2);

        verify(player1).payChips(20);
        verify(player2, times(2)).payChips(10);
        verify(player4).payChips(10);

        Assert.assertTrue(player1.getUser().getBalance() == dealerOldRealCurrency);
        Assert.assertTrue(player2.getUser().getBalance() == smallPlayerOldRealCurrency);
        Assert.assertTrue(player3.getUser().getBalance() == bigPlayerOldRealCurrency);
        Assert.assertTrue(player4.getUser().getBalance() == lastPlayerOldRealCurrency);
    }
}