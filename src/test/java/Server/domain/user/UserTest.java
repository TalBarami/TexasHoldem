package Server.domain.user;

import Exceptions.ArgumentNotInBoundsException;
import Server.data.users.Users;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by user on 15/04/2017.
 */
public class UserTest {
    Users usersDb;
    User user1;

    @Before
    public void setUp() throws Exception {
        user1 = new User("hodbub", "1234", "hobdud@post.bgu.ac.il", LocalDate.now(), null);
        usersDb = new Users();
        usersDb.addUser(user1);
    }

    @After
    public void tearDown() throws Exception {
        usersDb.deleteUser(user1);
    }

    @Test
    public void testDeposit1() throws ArgumentNotInBoundsException {
        user1.deposit(10, true);
        Assert.assertTrue(user1.getBalance() == 10);
    }

    @Test
    public void testDeposit2() throws ArgumentNotInBoundsException {
        user1.deposit(10, false);
        Assert.assertTrue(user1.getBalance() == 10);
        Assert.assertTrue(user1.getAmountEarnedInLeague() == 10);
    }

    @Test
    public void testWithdraw1() throws ArgumentNotInBoundsException {
        user1.withdraw(10, true);
        Assert.assertTrue(user1.getBalance() == 0);
    }

    @Test
    public void testWithdraw2() throws ArgumentNotInBoundsException {
        user1.deposit(100, false);
        user1.withdraw(10, false);
        Assert.assertTrue(user1.getBalance() == 90);
        Assert.assertTrue(user1.getAmountEarnedInLeague() == 90);
    }
}