package TexasHoldem.domain.game;

import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.hand.Hand;
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

/**
 * Created by RotemWald on 11/04/2017.
 */
public class RoundTest {
    Round round1;

    public RoundTest() {
        setRound1();
    }

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

        round1.runPaySmallAndBigBlind();

        Assert.assertTrue(smallPlayer.getChipsAmount() == smallPlayerOldChipAmount - smallBlindAmount);
        Assert.assertTrue(bigPlayer.getChipsAmount() == bigPlayerOldChipAmount - bigBlindAmount);
    }

    @Test
    public void round1_testPlayPreFlopRound() throws Exception {
        Map<Player, List<Pair<Game.GameActions, Integer>>> playerDecisions = new HashMap<Player, List<Pair<Game.GameActions, Integer>>>();

        Player dealer = round1.getCurrentDealerPlayer();
        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();
        Player lastPlayer = round1.getActivePlayers().stream().filter(p -> p != dealer && p != smallPlayer && p != bigPlayer).findFirst().get();

        double dealerOldRealCurrency = dealer.getUser().getBalance();
        double smallPlayerOldRealCurrency = smallPlayer.getUser().getBalance();
        double bigPlayerOldRealCurrency = bigPlayer.getUser().getBalance();
        double lastPlayerOldRealCurrency = lastPlayer.getUser().getBalance();

        List<Pair<Game.GameActions, Integer>> dealerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> smallPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> bigPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> lastPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();

        dealerDecisionList.add(Pair.of(Game.GameActions.CALL, null));
        smallPlayerDecisionList.add(Pair.of(Game.GameActions.CALL, null));
        bigPlayerDecisionList.add(Pair.of(Game.GameActions.CHECK, null));
        lastPlayerDecisionList.add(Pair.of(Game.GameActions.CALL, null));

        playerDecisions.put(dealer, dealerDecisionList);
        playerDecisions.put(smallPlayer, smallPlayerDecisionList);
        playerDecisions.put(bigPlayer, bigPlayerDecisionList);
        playerDecisions.put(lastPlayer, lastPlayerDecisionList);

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
    public void round1_testPlayFlopRound() {
        Map<Player, List<Pair<Game.GameActions, Integer>>> playerDecisions = new HashMap<Player, List<Pair<Game.GameActions, Integer>>>();

        Player dealer = round1.getCurrentDealerPlayer();
        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();
        Player lastPlayer = round1.getActivePlayers().stream().filter(p -> p != dealer && p != smallPlayer && p != bigPlayer).findFirst().get();

        double dealerOldRealCurrency = dealer.getUser().getBalance();
        double smallPlayerOldRealCurrency = smallPlayer.getUser().getBalance();
        double bigPlayerOldRealCurrency = bigPlayer.getUser().getBalance();
        double lastPlayerOldRealCurrency = lastPlayer.getUser().getBalance();

        List<Pair<Game.GameActions, Integer>> dealerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> smallPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> bigPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> lastPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();

        dealerDecisionList.add(Pair.of(Game.GameActions.CALL, null));
        smallPlayerDecisionList.add(Pair.of(Game.GameActions.RAISE, 10));
        bigPlayerDecisionList.add(Pair.of(Game.GameActions.CALL, null));
        lastPlayerDecisionList.add(Pair.of(Game.GameActions.FOLD, null));

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

    @Test
    public void round1_runPlayRiverRound() {
        Map<Player, List<Pair<Game.GameActions, Integer>>> playerDecisions = new HashMap<Player, List<Pair<Game.GameActions, Integer>>>();

        Player dealer = round1.getCurrentDealerPlayer();
        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();

        double dealerOldRealCurrency = dealer.getUser().getBalance();
        double smallPlayerOldRealCurrency = smallPlayer.getUser().getBalance();
        double bigPlayerOldRealCurrency = bigPlayer.getUser().getBalance();

        List<Pair<Game.GameActions, Integer>> dealerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> smallPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();
        List<Pair<Game.GameActions, Integer>> bigPlayerDecisionList = new LinkedList<Pair<Game.GameActions, Integer>>();

        smallPlayerDecisionList.add(Pair.of(Game.GameActions.CHECK, null));
        bigPlayerDecisionList.add(Pair.of(Game.GameActions.RAISE, 10));
        dealerDecisionList.add(Pair.of(Game.GameActions.RAISE, 20));
        smallPlayerDecisionList.add(Pair.of(Game.GameActions.FOLD, null));
        bigPlayerDecisionList.add(Pair.of(Game.GameActions.RAISE, 30));
        dealerDecisionList.add(Pair.of(Game.GameActions.CALL, null));

        playerDecisions.put(dealer, dealerDecisionList);
        playerDecisions.put(smallPlayer, smallPlayerDecisionList);
        playerDecisions.put(bigPlayer, bigPlayerDecisionList);

        round1.runPreStartOfNewTurn();
        round1.runPlayRiverRound(playerDecisions);

        Assert.assertTrue(round1.getPotAmount() == 220);
        Assert.assertTrue(round1.getActivePlayers().size() == 2);
        Assert.assertTrue(dealer.getChipsAmount() == 20);
        Assert.assertTrue(smallPlayer.getChipsAmount() == 50);
        Assert.assertTrue(bigPlayer.getChipsAmount() == 20);

        Assert.assertTrue(dealer.getUser().getBalance() == dealerOldRealCurrency);
        Assert.assertTrue(smallPlayer.getUser().getBalance() == smallPlayerOldRealCurrency);
        Assert.assertTrue(bigPlayer.getUser().getBalance() == bigPlayerOldRealCurrency);
    }
}