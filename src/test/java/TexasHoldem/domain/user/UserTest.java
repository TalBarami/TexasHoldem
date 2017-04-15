package TexasHoldem.domain.user;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by user on 15/04/2017.
 */
public class UserTest {
    @Test
    public void testDeposit1() throws ArgumentNotInBoundsException {
        User user1 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        user1.deposit(10, true);
        Assert.assertTrue(user1.getBalance() == 10);
    }

    @Test
    public void testDeposit2() throws ArgumentNotInBoundsException {
        User user1 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        user1.deposit(10, false);
        Assert.assertTrue(user1.getBalance() == 10);
        Assert.assertTrue(user1.getAmountEarnedInLeague() == 10);
    }

    @Test
    public void testWithdraw1() throws ArgumentNotInBoundsException {
        User user1 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        user1.withdraw(10, true);
        Assert.assertTrue(user1.getBalance() == 0);
    }

    @Test
    public void testWithdraw2() throws ArgumentNotInBoundsException {
        User user1 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        user1.deposit(100, false);
        user1.withdraw(10, false);
        Assert.assertTrue(user1.getBalance() == 90);
        Assert.assertTrue(user1.getAmountEarnedInLeague() == 90);
    }
}