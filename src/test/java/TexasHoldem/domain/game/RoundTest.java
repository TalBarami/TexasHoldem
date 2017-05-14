package TexasHoldem.domain.game;

import TexasHoldem.domain.events.gameFlowEvents.MoveEvent;
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

import static org.mockito.Mockito.*;

/**
 * Created by RotemWald on 11/04/2017.
 */
public class RoundTest {
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

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);

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

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);
        when(player3.getUser()).thenReturn(user3);
        when(player4.getUser()).thenReturn(user4);

        round1.playTurnOfPlayer(me4);
        round1.playTurnOfPlayer(me1);
        round1.playTurnOfPlayer(me2);
        // Suppose game has finished here because there were last only one player

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

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);

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

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);
        when(player3.getUser()).thenReturn(user3);
        when(player4.getUser()).thenReturn(user4);

        round1.playTurnOfPlayer(me4);
        round1.playTurnOfPlayer(me1);
        round1.playTurnOfPlayer(me2);
        round1.playTurnOfPlayer(me3);

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

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Player player4 = mock(Player.class);

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

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);
        when(player3.getUser()).thenReturn(user3);
        when(player3.getChipsAmount()).thenReturn(90);
        when(player4.getUser()).thenReturn(user4);
        when(player4.payChips(10)).thenReturn(10).thenReturn(10);
        when(player4.getLastBetSinceCardOpen()).thenReturn(0).thenReturn(0).thenReturn(10).thenReturn(10);
        when(player2.payChips(10)).thenReturn(10);
        when(player3.payChips(20)).thenReturn(20);

        round1.playTurnOfPlayer(me4);
        round1.playTurnOfPlayer(me1);
        round1.playTurnOfPlayer(me2);
        round1.playTurnOfPlayer(me3);
        round1.playTurnOfPlayer(me41);
        round1.playTurnOfPlayer(me21);

        Assert.assertTrue(round1.getPotAmount() == 50);
        Assert.assertTrue(round1.getActivePlayers().size() == 2);
        Assert.assertTrue(round1.isRoundActive() == true);

        verify(player4, times(2)).payChips(10);
        verify(player3).payChips(20);
        verify(player2).payChips(10);
    }

    @Test
    public void round1_testCalculateWinner_oneWinner() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

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

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);

        when(player1.getCards()).thenReturn(player1CardSet);
        when(player2.getCards()).thenReturn(player2CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADE));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADE));
        openedCards.add(new Card(Rank.KING, Suit.CLUB));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMOND));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADE));

        when(player1.getTotalAmountPayedInRound()).thenReturn(50, 50, 50, 50, 50, 0);
        when(player2.getTotalAmountPayedInRound()).thenReturn(50, 50, 50, 0);

        round1.setOpenedCards(openedCards);
        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        verify(player1).addChips(100);
    }

    @Test
    public void round1_testCalculateWinner_twoWinnersSplitPot() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

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

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);

        when(player1.getCards()).thenReturn(player1CardSet);
        when(player2.getCards()).thenReturn(player2CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADE));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADE));
        openedCards.add(new Card(Rank.KING, Suit.CLUB));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMOND));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADE));

        when(player1.getTotalAmountPayedInRound()).thenReturn(50, 50, 50, 50, 50, 0);
        when(player2.getTotalAmountPayedInRound()).thenReturn(50, 50, 50, 0);

        round1.setOpenedCards(openedCards);
        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        verify(player1).addChips(50);
        verify(player1).addChips(50);
    }

    @Test
    public void round1_testCalculateWinner_twoWinnersDifferentAmount() throws Exception {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now(), null);
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now(), null);

        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);

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

        when(player1.getUser()).thenReturn(user1);
        when(player2.getUser()).thenReturn(user2);
        when(player3.getUser()).thenReturn(user3);

        when(player1.getCards()).thenReturn(player1CardSet);
        when(player2.getCards()).thenReturn(player2CardSet).thenReturn(player2CardSet);
        when(player3.getCards()).thenReturn(player3CardSet).thenReturn(player3CardSet);

        List<Card> openedCards = new LinkedList<Card>();
        openedCards.add(new Card(Rank.JACK, Suit.SPADE));
        openedCards.add(new Card(Rank.QUEEN, Suit.SPADE));
        openedCards.add(new Card(Rank.KING, Suit.CLUB));
        openedCards.add(new Card(Rank.ACE, Suit.DIAMOND));
        openedCards.add(new Card(Rank.FOUR, Suit.SPADE));

        when(player1.getTotalAmountPayedInRound()).thenReturn(20, 20, 20, 20, 20, 0);
        when(player2.getTotalAmountPayedInRound()).thenReturn(50, 50, 50, 30, 30, 0);
        when(player3.getTotalAmountPayedInRound()).thenReturn(50, 50, 50, 30, 30, 30, 30, 30, 0);

        round1.setOpenedCards(openedCards);
        Method method = Round.class.getDeclaredMethod("calculateWinner", null);
        method.setAccessible(true);
        method.invoke(round1, null);

        verify(player1).addChips(60);
        verify(player3).addChips(60);
    }
}