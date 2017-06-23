package Server.AcceptanceTests;

import Server.data.users.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class EditUserProfileTests extends ProjectTest {

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
    public void testEditPassword()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com", LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited1 = this.editusername("achiadg", "achiadg", "aChi1234#*","achiadg@gmail.com", LocalDate.of(1991,4,20));
        assertTrue(useredited1);
        boolean useredited2 = this.editusername("achiadg", "achiadg", "achiadg12", "achiadg@gmail.com", LocalDate.of(1991, 4, 20));
        assertTrue(useredited2);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testEditEmail()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited1 = this.editusername("achiadg", "achiadg", "aChi12#*","achiadg@post.bgu.ac.il",LocalDate.of(1991,4,20));
        assertTrue(useredited1);
        boolean useredited2 = this.editusername("achiadg","achiadg", "aChi12#*", "achiadg@gmail.com",LocalDate.of(1991,4,20));
        assertTrue(useredited2);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testEditPasswordNoChange()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited = this.editusername("achiadg","achiadg", "aChi12#*", "achiadg@gmail.com",LocalDate.of(1991,4,20));
        assertTrue(useredited);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testEditEmailNoChange()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited = this.editusername("achiadg","achiadg", "aChi12#*", "achiadg@gmail.com",LocalDate.of(1991,4,20));
        assertTrue(useredited);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testEditPasswordNoValid()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited = this.editusername("achiadg", "achiadg","aChi1\n2#*", "achiadg@gmail.com",LocalDate.of(1991,4,20));
        assertFalse(useredited);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testEditPasswordEmpty()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited = this.editusername("achiadg", "achiadg","", "achiadg@gmail.com",LocalDate.of(1991,4,20));
        assertFalse(useredited);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testEditEmailNoValid()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited = this.editusername("achiadg", "achiadg","aChi12#*","achi\tadg@g\nmail.com",LocalDate.of(1991,4,20));
        assertFalse(useredited);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }

    @Test
    public void testEditEmailEmpty()
    {
        boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",LocalDate.of(1991,4,20),null);
        assertTrue(useradded);
        boolean useredited = this.editusername("achiadg", "achiadg","aChi12#*","",LocalDate.of(1991,4,20));
        assertFalse(useredited);
        boolean deleteUser1 = this.deleteUser("achiadg");
        assertTrue(deleteUser1);
        boolean userfound = this.searchUser("achiadg");
        assertFalse(userfound);
    }
}
