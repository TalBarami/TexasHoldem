package TexasHoldem.AcceptanceTests;

/**
 * Created by אחיעד on 05/04/2017.
 */

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class LogOutUserTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
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

}
