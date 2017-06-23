package Server.AcceptanceTests;

/**
 * Created by אחיעד on 05/04/2017.
 */

import Server.data.users.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class LogOutUserTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @After
    public void tearDown() {
        Users.getUsersInGame().clear();
        super.clearAllUsersFromDB();
    }

    @Test
    public void testLogOutUser()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean userloggedin = this.login("achiadg","aChi12#*");
        boolean userloggedout1 = this.logout("achiadg");
        assertTrue(userloggedout1);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
    }

    @Test
    public void testLogOutUserInValid()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean userloggedin = this.login("achiadg","aChi12#*");
        boolean userloggedout1 = this.logout("achiadgele");
        assertFalse(userloggedout1);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
    }

    @Test
    public void testLogOutUserInValidEmptyUser()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean userloggedin = this.login("achiadg","aChi12#*");
        boolean userloggedout1 = this.logout("");
        assertFalse(userloggedout1);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
    }

}
