package TexasHoldem.AcceptanceTests;

/**
 * Created by אחיעד on 05/04/2017.
 */

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class LogInUserTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testLogInValidUser()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1308");
        assertTrue(userloggedin1);
        assertTrue(userloggedin2);
        boolean userloggedout1 = this.logout("achiadg");
        boolean userloggedout2 = this.logout("hodbub");
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testLogInInvalidUser()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("ronenbut","aChi12#*");
        boolean userloggedin2 = this.login("hodbub43","hBublil1308");
        assertFalse(userloggedin1);
        assertFalse(userloggedin2);
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testLogInInvalidUserEmpty()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("","aChi12#*");
        boolean userloggedin2 = this.login("","hBublil1308");
        assertFalse(userloggedin1);
        assertFalse(userloggedin2);
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testLogInInvalidPassword()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","ronen1");
        boolean userloggedin2 = this.login("hodbub","hBublil13085678");
        assertFalse(userloggedin1);
        assertFalse(userloggedin2);
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testLogInInvalidPasswordEmpty()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achiadg","");
        boolean userloggedin2 = this.login("hodbub","");
        assertFalse(userloggedin1);
        assertFalse(userloggedin2);
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }

    @Test
    public void testLogInInvalidCharecters()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean useradded2 = this.registerUser("hodbub","hBublil1308","hod.bub@gmail.com",LocalDate.of(1991,8,28),null);
        boolean userloggedin1 = this.login("achi\nadg","aChi12#*");
        boolean userloggedin2 = this.login("hodbub","hBublil1\t308");
        assertFalse(userloggedin1);
        assertFalse(userloggedin2);
        boolean deleteUser1 = this.deleteUser("achiadg");
        boolean deleteUser2 = this.deleteUser("hodbub");
        assertTrue(deleteUser1);
        assertTrue(deleteUser2);
    }



    @Test
    public void testLogInPasswordSqlInjection()
    {
        boolean useradded1 = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        boolean userloggedin1 = this.login("achiadg","aChi12#* ; SELECT * FROM Users");
        assertFalse(userloggedin1);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
    }

}
