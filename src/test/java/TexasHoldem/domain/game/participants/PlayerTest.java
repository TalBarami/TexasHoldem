package TexasHoldem.domain.game.participants;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.domain.user.User;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by user on 15/04/2017.
 */
public class PlayerTest {
    @Test
    public void testAddChips1() {
        User user1 = mock(User.class);
        Player player1 = new Player(user1, 100, 100);

        player1.addChips(10);
        Assert.assertTrue(player1.getChipsAmount() == 110);
    }

    @Test
    public void testAddChips2() throws ArgumentNotInBoundsException {
        User user1 = mock(User.class);
        Player player1 = new Player(user1, 0, 0);

        player1.addChips(10);
        Assert.assertTrue(player1.getChipsAmount() == 100);
        verify(user1).deposit(10, false);
    }

    @Test
    public void testPayChips1() {
        User user1 = mock(User.class);
        Player player1 = new Player(user1, 100, 100);

        int ans = player1.payChips(10);
        Assert.assertTrue(ans == 10);
        Assert.assertTrue(player1.getChipsAmount() == 90);
    }

    @Test
    public void testPayChips2() {
        User user1 = mock(User.class);
        Player player1 = new Player(user1, 100, 100);

        int ans = player1.payChips(101);
        Assert.assertTrue(ans == 100);
        Assert.assertTrue(player1.getChipsAmount() == 0);
    }

    @Test
    public void testPayChips3() throws ArgumentNotInBoundsException {
        User user1 = mock(User.class);
        Player player1 = new Player(user1, 0, 0);
        int oldLastBetSinceCardOpen = player1.getLastBetSinceCardOpen();
        int oldTotalAmountPayedInRound = player1.getTotalAmountPayedInRound();

        when(user1.withdraw(10, false)).thenReturn(10);
        int ans = player1.payChips(10);
        Assert.assertTrue(player1.getLastBetSinceCardOpen() == oldLastBetSinceCardOpen + 10);
        Assert.assertTrue(player1.getTotalAmountPayedInRound() == oldTotalAmountPayedInRound + 10);
    }
}