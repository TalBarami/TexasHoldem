package Server.IntegrationTests;

import Enumerations.GamePolicy;
import Server.domain.events.gameFlowEvents.MoveEvent;
import Server.domain.game.*;
import Server.domain.game.card.Card;
import Server.domain.game.card.Rank;
import Server.domain.game.card.Suit;
import Server.domain.game.participants.Player;
import Server.domain.user.LeagueManager;
import Server.domain.user.User;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by hod on 12/05/2017.
 */
public class RoundUserTest {

    @Test
    public void round1_testPaySmallAndBigBlind() throws Exception {
        int smallBlindAmount = 5;
        int bigBlindAmount = 10;

        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);

        user1.deposit(100, true);
        user2.deposit(100, true);
        user3.deposit(100, true);

        Player player1 = new Player(user1, 0, 0);
        Player player2 = new Player(user2, 0, 0);
        Player player3 = new Player(user3, 0, 0);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 100, 0, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Method method = Round.class.getDeclaredMethod("paySmallAndBigBlind", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertTrue(user2.getBalance() == 100 - smallBlindAmount);
        Assert.assertTrue(user3.getBalance() == 100 - bigBlindAmount);
    }

    @Test
    public void round1_testPreFlop_AllFold() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        user1.deposit(100, true);
        user2.deposit(100, true);
        user3.deposit(100, true);
        user4.deposit(100, true);

        Player player1 = new Player(user1, 0, 0);
        Player player2 = new Player(user2, 0, 0);
        Player player3 = new Player(user3, 0, 0);
        Player player4 = new Player(user3, 0, 0);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 100, 0, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        MoveEvent me1 = new MoveEvent(player1, GameActions.FOLD, 0, game1.getName());
        MoveEvent me2 = new MoveEvent(player2, GameActions.FOLD, 0, game1.getName());
        MoveEvent me4 = new MoveEvent(player4, GameActions.FOLD, 0, game1.getName());

        round1.setRoundActive(true);
        round1.setCurrentState(RoundState.PREFLOP);

        round1.playTurnOfPlayer(me4);
        round1.playTurnOfPlayer(me1);
        round1.playTurnOfPlayer(me2);
        // Suppose game has finished here because there were last only one player

        Assert.assertEquals(100, user4.getBalance());
        Assert.assertEquals(100, user1.getBalance());
        Assert.assertEquals(100, user2.getBalance());

        Assert.assertTrue(round1.getPotAmount() == 0);
        Assert.assertTrue(round1.getActivePlayers().size() == 1);
        Assert.assertTrue(round1.getActivePlayers().get(0) == player3);
        Assert.assertTrue(round1.isRoundActive() == false);
    }

    @Test
    public void round1_testPreFlop_AllCheck() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        user1.deposit(100, true);
        user2.deposit(100, true);
        user3.deposit(100, true);
        user4.deposit(100, true);

        Player player1 = new Player(user1, 0, 0);
        Player player2 = new Player(user2, 0, 0);
        Player player3 = new Player(user3, 0, 0);
        Player player4 = new Player(user3, 0, 0);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 100, 0, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        MoveEvent me1 = new MoveEvent(player1, GameActions.CHECK, 0, game1.getName());
        MoveEvent me2 = new MoveEvent(player2, GameActions.CHECK, 0, game1.getName());
        MoveEvent me3 = new MoveEvent(player3, GameActions.CHECK, 0, game1.getName());
        MoveEvent me4 = new MoveEvent(player4, GameActions.CHECK, 0, game1.getName());

        round1.setRoundActive(true);
        round1.setCurrentState(RoundState.PREFLOP);
        round1.setChipsToCall(0);

        round1.playTurnOfPlayer(me4);
        round1.playTurnOfPlayer(me1);
        round1.playTurnOfPlayer(me2);
        round1.playTurnOfPlayer(me3);

        Assert.assertEquals(100, user4.getBalance());
        Assert.assertEquals(100, user1.getBalance());
        Assert.assertEquals(100, user2.getBalance());
        Assert.assertEquals(100, user3.getBalance());

        Assert.assertTrue(round1.getPotAmount() == 0);
        Assert.assertTrue(round1.getActivePlayers().size() == 4);
        Assert.assertTrue(round1.isRoundActive() == true);
        Assert.assertTrue(round1.getCurrentState() == RoundState.FLOP);
    }

    @Test
    public void round1_testPreFlop_MixOfActions() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        user1.deposit(100, true);
        user2.deposit(100, true);
        user3.deposit(100, true);
        user4.deposit(100, true);

        Player player1 = new Player(user1, 0, 0);
        Player player2 = new Player(user2, 0, 0);
        Player player3 = new Player(user3, 0, 0);
        Player player4 = new Player(user4, 0, 0);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 100, 0, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        round1.setRoundActive(true);
        round1.setCurrentState(RoundState.TURN);

        // Player 4 is the first player
        MoveEvent me4 = new MoveEvent(player4, GameActions.CALL, 0, game1.getName());
        MoveEvent me1 = new MoveEvent(player1, GameActions.FOLD, 0, game1.getName());
        MoveEvent me2 = new MoveEvent(player2, GameActions.CALL, 0, game1.getName());
        MoveEvent me3 = new MoveEvent(player3, GameActions.RAISE, 20, game1.getName());
        MoveEvent me41 = new MoveEvent(player4, GameActions.CALL, 0, game1.getName());
        MoveEvent me21 = new MoveEvent(player2, GameActions.FOLD, 0, game1.getName());

        int oldChipsAmount4 = player4.getChipsAmount();
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();
        int oldChipsAmount3 = player3.getChipsAmount();

        round1.playTurnOfPlayer(me4);
        Assert.assertEquals(100 - 10, user4.getBalance());
        round1.playTurnOfPlayer(me1);
        Assert.assertEquals(100, user1.getBalance());
        round1.playTurnOfPlayer(me2);
        Assert.assertEquals(100 - 10, user2.getBalance());
        round1.playTurnOfPlayer(me3);
        Assert.assertEquals(100 - 20, user3.getBalance());
        round1.playTurnOfPlayer(me41);
        Assert.assertEquals(100 - 20, user4.getBalance());
        round1.playTurnOfPlayer(me21);
        Assert.assertEquals(100 - 10, user2.getBalance());

        Assert.assertTrue(round1.getPotAmount() == 50);
        Assert.assertTrue(round1.getActivePlayers().size() == 2);
        Assert.assertTrue(round1.isRoundActive() == true);
    }

    @Test
    public void round1_testCalculateWinner_oneWinner() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = new Player(user1, 0, 0);
        Player player2 = new Player(user2, 0, 0);

        user1.deposit(100, true);
        user2.deposit(100, true);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 100, 0, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Set<Card> player1CardSet = new HashSet<Card>();
        Set<Card> player2CardSet = new HashSet<Card>();

        player1CardSet.add(new Card(Rank.NINE, Suit.DIAMONDS));
        player1CardSet.add(new Card(Rank.TEN, Suit.DIAMONDS));
        player2CardSet.add(new Card(Rank.ACE, Suit.HEATS));
        player2CardSet.add(new Card(Rank.KING, Suit.HEATS));

        player1.addCards(player1CardSet);
        player2.addCards(player2CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADES));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADES));
        openedCards.add(new Card(Rank.KING, Suit.CLUBS));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADES));
        round1.setOpenedCards(openedCards);

        player1.setTotalAmountPayedInRound(50);
        player2.setTotalAmountPayedInRound(50);
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();

        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertEquals(oldChipsAmount1 + 100, user1.getBalance());
        Assert.assertEquals(oldChipsAmount2, user2.getBalance());
    }

    @Test
    public void round1_testCalculateWinner_twoWinnersSplitPot() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = new Player(user1, 0, 0);
        Player player2 = new Player(user2, 0, 0);

        user1.deposit(100, true);
        user2.deposit(100, true);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 100, 0, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Set<Card> player1CardSet = new HashSet<Card>();
        Set<Card> player2CardSet = new HashSet<Card>();

        player1CardSet.add(new Card(Rank.NINE, Suit.DIAMONDS));
        player1CardSet.add(new Card(Rank.TEN, Suit.DIAMONDS));
        player2CardSet.add(new Card(Rank.NINE, Suit.HEATS));
        player2CardSet.add(new Card(Rank.TEN, Suit.HEATS));

        player1.addCards(player1CardSet);
        player2.addCards(player2CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADES));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADES));
        openedCards.add(new Card(Rank.KING, Suit.CLUBS));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADES));
        round1.setOpenedCards(openedCards);

        player1.setTotalAmountPayedInRound(50);
        player2.setTotalAmountPayedInRound(50);
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();

        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertEquals(oldChipsAmount1 + 50, user1.getBalance());
        Assert.assertEquals(oldChipsAmount2 + 50, user2.getBalance());
    }

    @Test
    public void round1_testCalculateWinner_twoWinnersDifferentAmount() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);

        user1.deposit(100, true);
        user2.deposit(100, true);
        user3.deposit(100, true);

        Player player1 = new Player(user1, 0, 0);
        Player player2 = new Player(user2, 0, 0);
        Player player3 = new Player(user3, 0, 0);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 100, 0, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Set<Card> player1CardSet = new HashSet<Card>();
        Set<Card> player2CardSet = new HashSet<Card>();
        Set<Card> player3CardSet = new HashSet<Card>();

        player1CardSet.add(new Card(Rank.NINE, Suit.DIAMONDS));
        player1CardSet.add(new Card(Rank.TEN, Suit.DIAMONDS));
        player2CardSet.add(new Card(Rank.KING, Suit.HEATS));
        player2CardSet.add(new Card(Rank.SEVEN, Suit.HEATS));
        player3CardSet.add(new Card(Rank.ACE, Suit.CLUBS));
        player3CardSet.add(new Card(Rank.EIGHT, Suit.CLUBS));

        player1.addCards(player1CardSet);
        player2.addCards(player2CardSet);
        player3.addCards(player3CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADES));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADES));
        openedCards.add(new Card(Rank.KING, Suit.CLUBS));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADES));

        player1.setTotalAmountPayedInRound(20);
        player2.setTotalAmountPayedInRound(50);
        player3.setTotalAmountPayedInRound(50);
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();
        int oldChipsAmount3 = player3.getChipsAmount();

        round1.setOpenedCards(openedCards);
        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertEquals(oldChipsAmount1 + 60, user1.getBalance());
        Assert.assertEquals(oldChipsAmount2, user2.getBalance());
        Assert.assertEquals(oldChipsAmount3 + 60, user3.getBalance());
    }
}
