package TexasHoldem.IntegrationTests;

import TexasHoldem.domain.events.gameFlowEvents.MoveEvent;
import TexasHoldem.domain.game.*;
import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.card.Rank;
import TexasHoldem.domain.game.card.Suit;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
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
public class RoundPlayerTest {

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

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
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
    public void round1_testPreFlop_AllFold() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);
        Player player3 = new Player(user3, 100, 100);
        Player player4 = new Player(user3, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        MoveEvent me1 = new MoveEvent(player1, GameActions.FOLD, 0);
        MoveEvent me2 = new MoveEvent(player2, GameActions.FOLD, 0);
        MoveEvent me4 = new MoveEvent(player4, GameActions.FOLD, 0);

        round1.setRoundActive(true);
        round1.setCurrentState(RoundState.PREFLOP);
        int oldChipsAmount4 = player4.getChipsAmount();
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();

        round1.playTurnOfPlayer(me4);
        round1.playTurnOfPlayer(me1);
        round1.playTurnOfPlayer(me2);
        // Suppose game has finished here because there were last only one player

        Assert.assertEquals(oldChipsAmount4, player4.getChipsAmount());
        Assert.assertEquals(oldChipsAmount1, player1.getChipsAmount());
        Assert.assertEquals(oldChipsAmount2, player2.getChipsAmount());

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

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);
        Player player3 = new Player(user3, 100, 100);
        Player player4 = new Player(user3, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        MoveEvent me1 = new MoveEvent(player1, GameActions.CHECK, 0);
        MoveEvent me2 = new MoveEvent(player2, GameActions.CHECK, 0);
        MoveEvent me3 = new MoveEvent(player3, GameActions.CHECK, 0);
        MoveEvent me4 = new MoveEvent(player4, GameActions.CHECK, 0);

        round1.setRoundActive(true);
        round1.setCurrentState(RoundState.PREFLOP);
        round1.setChipsToCall(0);

        int oldChipsAmount4 = player4.getChipsAmount();
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();
        int oldChipsAmount3 = player3.getChipsAmount();

        round1.playTurnOfPlayer(me4);
        round1.playTurnOfPlayer(me1);
        round1.playTurnOfPlayer(me2);
        round1.playTurnOfPlayer(me3);

        Assert.assertEquals(oldChipsAmount4, player4.getChipsAmount());
        Assert.assertEquals(oldChipsAmount1, player1.getChipsAmount());
        Assert.assertEquals(oldChipsAmount2, player2.getChipsAmount());
        Assert.assertEquals(oldChipsAmount3, player3.getChipsAmount());

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

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);
        Player player3 = new Player(user3, 100, 100);
        Player player4 = new Player(user4, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        round1.setRoundActive(true);
        round1.setCurrentState(RoundState.TURN);

        // Player 4 is the first player
        MoveEvent me4 = new MoveEvent(player4, GameActions.CALL, 0);
        MoveEvent me1 = new MoveEvent(player1, GameActions.FOLD, 0);
        MoveEvent me2 = new MoveEvent(player2, GameActions.CALL, 0);
        MoveEvent me3 = new MoveEvent(player3, GameActions.RAISE, 20);
        MoveEvent me41 = new MoveEvent(player4, GameActions.CALL, 0);
        MoveEvent me21 = new MoveEvent(player2, GameActions.FOLD, 0);

        int oldChipsAmount4 = player4.getChipsAmount();
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();
        int oldChipsAmount3 = player3.getChipsAmount();

        round1.playTurnOfPlayer(me4);
        Assert.assertEquals(oldChipsAmount4 - 10, player4.getChipsAmount());
        round1.playTurnOfPlayer(me1);
        Assert.assertEquals(oldChipsAmount1, player1.getChipsAmount());
        round1.playTurnOfPlayer(me2);
        Assert.assertEquals(oldChipsAmount2 - 10, player2.getChipsAmount());
        round1.playTurnOfPlayer(me3);
        Assert.assertEquals(oldChipsAmount3 - 20, player3.getChipsAmount());
        round1.playTurnOfPlayer(me41);
        Assert.assertEquals(oldChipsAmount4 - 20, player4.getChipsAmount());
        round1.playTurnOfPlayer(me21);
        Assert.assertEquals(oldChipsAmount2 - 10, player2.getChipsAmount());

        Assert.assertTrue(round1.getPotAmount() == 50);
        Assert.assertTrue(round1.getActivePlayers().size() == 2);
        Assert.assertTrue(round1.isRoundActive() == true);
    }

    @Test
    public void round1_testCalculateWinner_oneWinner() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Set<Card> player1CardSet = new HashSet<Card>();
        Set<Card> player2CardSet = new HashSet<Card>();

        player1CardSet.add(new Card(Rank.NINE, Suit.DIAMOND));
        player1CardSet.add(new Card(Rank.TEN, Suit.DIAMOND));
        player2CardSet.add(new Card(Rank.ACE, Suit.HEART));
        player2CardSet.add(new Card(Rank.KING, Suit.HEART));

        player1.addCards(player1CardSet);
        player2.addCards(player2CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADE));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADE));
        openedCards.add(new Card(Rank.KING, Suit.CLUB));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMOND));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADE));
        round1.setOpenedCards(openedCards);

        player1.setTotalAmountPayedInRound(50);
        player2.setTotalAmountPayedInRound(50);
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();

        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertEquals(oldChipsAmount1 + 100, player1.getChipsAmount());
        Assert.assertEquals(oldChipsAmount2, player2.getChipsAmount());
    }

    @Test
    public void round1_testCalculateWinner_twoWinnersSplitPot() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Set<Card> player1CardSet = new HashSet<Card>();
        Set<Card> player2CardSet = new HashSet<Card>();

        player1CardSet.add(new Card(Rank.NINE, Suit.DIAMOND));
        player1CardSet.add(new Card(Rank.TEN, Suit.DIAMOND));
        player2CardSet.add(new Card(Rank.NINE, Suit.HEART));
        player2CardSet.add(new Card(Rank.TEN, Suit.HEART));

        player1.addCards(player1CardSet);
        player2.addCards(player2CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADE));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADE));
        openedCards.add(new Card(Rank.KING, Suit.CLUB));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMOND));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADE));
        round1.setOpenedCards(openedCards);

        player1.setTotalAmountPayedInRound(50);
        player2.setTotalAmountPayedInRound(50);
        int oldChipsAmount1 = player1.getChipsAmount();
        int oldChipsAmount2 = player2.getChipsAmount();

        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        Assert.assertEquals(oldChipsAmount1 + 50, player1.getChipsAmount());
        Assert.assertEquals(oldChipsAmount2 + 50, player2.getChipsAmount());
    }

    @Test
    public void round1_testCalculateWinner_twoWinnersDifferentAmount() throws Exception {
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

        GameSettings settings1 = new GameSettings("Game1", GamePolicy.NOLIMIT, 100, 10, 5, 100, 2, 9, false);
        Game game1 = new Game(settings1, user1, new LeagueManager());
        Round round1 = new Round(playerList1, settings1, 0);

        Set<Card> player1CardSet = new HashSet<Card>();
        Set<Card> player2CardSet = new HashSet<Card>();
        Set<Card> player3CardSet = new HashSet<Card>();

        player1CardSet.add(new Card(Rank.NINE, Suit.DIAMOND));
        player1CardSet.add(new Card(Rank.TEN, Suit.DIAMOND));
        player2CardSet.add(new Card(Rank.KING, Suit.HEART));
        player2CardSet.add(new Card(Rank.SEVEN, Suit.HEART));
        player3CardSet.add(new Card(Rank.ACE, Suit.CLUB));
        player3CardSet.add(new Card(Rank.EIGHT, Suit.CLUB));

        player1.addCards(player1CardSet);
        player2.addCards(player2CardSet);
        player3.addCards(player3CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADE));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADE));
        openedCards.add(new Card(Rank.KING, Suit.CLUB));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMOND));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADE));

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

        Assert.assertEquals(oldChipsAmount1 + 60, player1.getChipsAmount());
        Assert.assertEquals(oldChipsAmount2, player2.getChipsAmount());
        Assert.assertEquals(oldChipsAmount3 + 60, player3.getChipsAmount());
    }
}
