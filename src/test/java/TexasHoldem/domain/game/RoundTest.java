package TexasHoldem.domain.game;

import TexasHoldem.domain.game.leagues.LeagueManager;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.users.User;
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
        setAndGetRound1();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void setAndGetRound1() {
        User user1 = new User("waldr", "1234", "waldr@post.bgu.ac.il", LocalDate.now());
        User user2 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now());
        User user3 = new User("achiadg", "1234", "achiadg@post.bgu.ac.il", LocalDate.now());
        User user4 = new User("ronenb", "1234", "ronenb@post.bgu.ac.il", LocalDate.now());

        Player player1 = new Player(user1, 100, 100);
        Player player2 = new Player(user2, 100, 100);
        Player player3 = new Player(user3, 100, 100);
        Player player4 = new Player(user4, 100, 100);

        List<Player> playerList1 = new LinkedList<Player>();
        playerList1.add(player1);
        playerList1.add(player2);
        playerList1.add(player3);
        playerList1.add(player4);

        GameSettings settings1 = new GameSettings(GameSettings.GamePolicy.NOLIMIT, 10, 5, 100, 2, 9, false);
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
        Map<Player, Game.GameActions> playerDecisions = new HashMap<Player, Game.GameActions>();

        Player dealer = round1.getCurrentDealerPlayer();
        Player smallPlayer = round1.getSmallPlayer();
        Player bigPlayer = round1.getBigPlayer();
        Player lastPlayer = round1.getActivePlayers().stream().filter(p -> p != dealer && p != smallPlayer && p != bigPlayer).findFirst().get();

        int dealerOldRealCurrency = dealer.getUser().getWallet().getBalance();
        int smallPlayerOldRealCurrency = smallPlayer.getUser().getWallet().getBalance();
        int bigPlayerOldRealCurrency = bigPlayer.getUser().getWallet().getBalance();
        int lastPlayerOldRealCurrency = lastPlayer.getUser().getWallet().getBalance();

        playerDecisions.put(dealer, Game.GameActions.CALL);
        playerDecisions.put(smallPlayer, Game.GameActions.CALL);
        playerDecisions.put(bigPlayer, Game.GameActions.CHECK);
        playerDecisions.put(lastPlayer, Game.GameActions.CALL);

        round1.runPlayPreFlopRound(playerDecisions);

        Assert.assertTrue(round1.getPotAmount() == 40);
        Assert.assertTrue(round1.getActivePlayers().size() == 4);
        Assert.assertTrue(round1.getActivePlayers().stream().allMatch(p -> p.getChipsAmount() == 90));

        Assert.assertTrue(dealer.getUser().getWallet().getBalance() == dealerOldRealCurrency);
        Assert.assertTrue(smallPlayer.getUser().getWallet().getBalance() == smallPlayerOldRealCurrency);
        Assert.assertTrue(bigPlayer.getUser().getWallet().getBalance() == bigPlayerOldRealCurrency);
        Assert.assertTrue(lastPlayer.getUser().getWallet().getBalance() == lastPlayerOldRealCurrency);
    }
}