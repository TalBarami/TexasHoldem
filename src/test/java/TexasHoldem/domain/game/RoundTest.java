package TexasHoldem.domain.game;

import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void round1_testRunTurnRound() {
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

        round1.playFlopOrTurnRound();

        verify(player1).chooseAction(any());
        verify(player2).chooseAction(any());
        verify(player3).chooseAction(any());
        verify(player4).chooseAction(any());

        Assert.assertTrue(round1.getPotAmount() == 0);
        Assert.assertTrue(round1.getActivePlayers().size() == 4);
    }

    /*
    @Test
    public void round1_testDealCards() throws Exception {
        round1.runDealCards();

        for (Player p : round1.getActivePlayers()) {
            Assert.assertTrue(p.getCards().size() == 2);
        }
    }

    @Test
    public void round1_testPaySmallAndBigBlind() throws Exception {
        int smallBlindAmount = 5;
        int bigBlindAmount = 10;

        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();
        int smallPlayerOldChipAmount = smallPlayer.getChipsAmount();
        int bigPlayerOldChipAmount = bigPlayer.getChipsAmount();

        round1.runDealCards();
        round1.runPaySmallAndBigBlind();

        Assert.assertTrue(smallPlayer.getChipsAmount() == smallPlayerOldChipAmount - smallBlindAmount);
        Assert.assertTrue(bigPlayer.getChipsAmount() == bigPlayerOldChipAmount - bigBlindAmount);
    }

    @Test
    public void round1_testPlayPreFlopRound() throws Exception {
        Map<Player, List<Pair<GameActions, Integer>>> playerDecisions = new HashMap<Player, List<Pair<GameActions, Integer>>>();

        Player dealer = round1.getCurrentDealerPlayer();
        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();
        Player lastPlayer = round1.getActivePlayers().stream().filter(p -> p != dealer && p != smallPlayer && p != bigPlayer).findFirst().get();

        double dealerOldRealCurrency = dealer.getUser().getBalance();
        double smallPlayerOldRealCurrency = smallPlayer.getUser().getBalance();
        double bigPlayerOldRealCurrency = bigPlayer.getUser().getBalance();
        double lastPlayerOldRealCurrency = lastPlayer.getUser().getBalance();

        List<Pair<GameActions, Integer>> dealerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        List<Pair<GameActions, Integer>> smallPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        List<Pair<GameActions, Integer>> bigPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        List<Pair<GameActions, Integer>> lastPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();

        dealerDecisionList.add(Pair.of(GameActions.CALL, null));
        smallPlayerDecisionList.add(Pair.of(GameActions.CALL, null));
        bigPlayerDecisionList.add(Pair.of(GameActions.CHECK, null));
        lastPlayerDecisionList.add(Pair.of(GameActions.CALL, null));

        playerDecisions.put(dealer, dealerDecisionList);
        playerDecisions.put(smallPlayer, smallPlayerDecisionList);
        playerDecisions.put(bigPlayer, bigPlayerDecisionList);
        playerDecisions.put(lastPlayer, lastPlayerDecisionList);

        round1.runDealCards();
        round1.runPaySmallAndBigBlind();
        round1.runPlayPreFlopRound(playerDecisions);

        Assert.assertTrue(round1.getPotAmount() == 40);
        Assert.assertTrue(round1.getActivePlayers().size() == 4);
        Assert.assertTrue(round1.getActivePlayers().stream().allMatch(p -> p.getChipsAmount() == 90));

        Assert.assertTrue(dealer.getUser().getBalance() == dealerOldRealCurrency);
        Assert.assertTrue(smallPlayer.getUser().getBalance() == smallPlayerOldRealCurrency);
        Assert.assertTrue(bigPlayer.getUser().getBalance() == bigPlayerOldRealCurrency);
        Assert.assertTrue(lastPlayer.getUser().getBalance() == lastPlayerOldRealCurrency);
    }

    @Test
    public void round1_testPlayTurnOrFlopRound() {
        Map<Player, List<Pair<GameActions, Integer>>> playerDecisions = new HashMap<Player, List<Pair<GameActions, Integer>>>();

        Player dealer = round1.getCurrentDealerPlayer();
        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();
        Player lastPlayer = round1.getActivePlayers().stream().filter(p -> p != dealer && p != smallPlayer && p != bigPlayer).findFirst().get();

        double dealerOldRealCurrency = dealer.getUser().getBalance();
        double smallPlayerOldRealCurrency = smallPlayer.getUser().getBalance();
        double bigPlayerOldRealCurrency = bigPlayer.getUser().getBalance();
        double lastPlayerOldRealCurrency = lastPlayer.getUser().getBalance();

        List<Pair<GameActions, Integer>> dealerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        List<Pair<GameActions, Integer>> smallPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        List<Pair<GameActions, Integer>> bigPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        List<Pair<GameActions, Integer>> lastPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();

        dealerDecisionList.add(Pair.of(GameActions.CALL, null));
        smallPlayerDecisionList.add(Pair.of(GameActions.CALL, null));
        bigPlayerDecisionList.add(Pair.of(GameActions.CHECK, null));
        lastPlayerDecisionList.add(Pair.of(GameActions.CALL, null));

        playerDecisions.put(dealer, dealerDecisionList);
        playerDecisions.put(smallPlayer, smallPlayerDecisionList);
        playerDecisions.put(bigPlayer, bigPlayerDecisionList);
        playerDecisions.put(lastPlayer, lastPlayerDecisionList);

        round1.runDealCards();
        round1.runPaySmallAndBigBlind();
        round1.runPlayPreFlopRound(playerDecisions);

        dealerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        smallPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        bigPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();
        lastPlayerDecisionList = new LinkedList<Pair<GameActions, Integer>>();

        dealerDecisionList.add(Pair.of(GameActions.CALL, null));
        smallPlayerDecisionList.add(Pair.of(GameActions.RAISE, 10));
        bigPlayerDecisionList.add(Pair.of(GameActions.CALL, null));
        lastPlayerDecisionList.add(Pair.of(GameActions.FOLD, null));

        playerDecisions.put(dealer, dealerDecisionList);
        playerDecisions.put(smallPlayer, smallPlayerDecisionList);
        playerDecisions.put(bigPlayer, bigPlayerDecisionList);
        playerDecisions.put(lastPlayer, lastPlayerDecisionList);

        round1.runPreStartOfNewTurn();
        round1.runPlayFlopRound(playerDecisions);

        Assert.assertTrue(round1.getPotAmount() == 70);
        Assert.assertTrue(round1.getActivePlayers().size() == 3);
        Assert.assertTrue(dealer.getChipsAmount() == 80);
        Assert.assertTrue(smallPlayer.getChipsAmount() == 80);
        Assert.assertTrue(bigPlayer.getChipsAmount() == 80);
        Assert.assertTrue(lastPlayer.getChipsAmount() == 90);

        Assert.assertTrue(dealer.getUser().getBalance() == dealerOldRealCurrency);
        Assert.assertTrue(smallPlayer.getUser().getBalance() == smallPlayerOldRealCurrency);
        Assert.assertTrue(bigPlayer.getUser().getBalance() == bigPlayerOldRealCurrency);
        Assert.assertTrue(lastPlayer.getUser().getBalance() == lastPlayerOldRealCurrency);
    }
    */
}