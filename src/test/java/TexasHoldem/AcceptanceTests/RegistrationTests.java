package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by אחיעד on 05/04/2017.
 */
public class RegistrationTests extends ProjectTest {

    @Before
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testRegisterValidUser()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
        assertTrue(useradded);
        boolean userfound = this.searchUser("achiadg");
        assertTrue(userfound);
        boolean deleteUser = this.deleteUser("achiadg");
        assertTrue(deleteUser);
        userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testRegisterInValidUserName()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
        assertTrue(useradded);
        useradded = this.registerUser("achiadg","aCh*","achiadg@gmail.post.bgu.ac.il",new DateTime(1995,3,28,14,18));
        assertFalse(useradded);
        boolean deleteUser = this.deleteUser("achiadg");
        assertTrue(deleteUser);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testRegisterInValidEmail()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
        assertTrue(useradded);
        useradded = this.registerUser("achiadgelerenter","aChi*","achiadg@gmail.com",new DateTime(1991,4,20,14,18));
        assertFalse(useradded);
        boolean deleteUser = this.deleteUser("achiadg");
        assertTrue(deleteUser);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testRegisterInValidCharecters()
    {
        boolean useradded = this.registerUser("achi\radg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
        assertFalse(useradded);
        useradded = this.registerUser("achiadgelerenter","aC\nhi*","achiadg@gmail.com",new DateTime(1991,4,20,14,18));
        assertFalse(useradded);
        useradded = this.registerUser("achiadgelerenter","aChi*","achiadg@gm\tail.com",new DateTime(1991,4,20,14,18));
        assertFalse(useradded);
        useradded = this.registerUser("achiadgelerenter","aChi*","achiadg@gmail.com",new DateTime(1991,4,38,14,18));
        assertFalse(useradded);
    }

}
