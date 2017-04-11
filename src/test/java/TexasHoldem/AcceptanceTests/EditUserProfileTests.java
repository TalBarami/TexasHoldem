package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * Created by אחיעד on 08/04/2017.
 */
public class EditUserProfileTests extends ProjectTest {

        @Before
        public void setUp()
        {
            super.setUp();
        }


        @Test
        public void testEditPassword()
        {
            boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
            assertTrue(useradded);
            boolean useredited1 = this.editPassword("achiadg", "achiad12#*");
            assertTrue(useredited1);
            boolean useredited2 = this.editPassword("achiadg", "aCh112#*");
            assertTrue(useredited2);
            boolean deleteUser1 = this.deleteUser("achiadg");
            assertTrue(deleteUser1);
            boolean userfound = this.searchUser("achiadg");
            assertFalse(userfound);
        }

        @Test
        public void testEditEmail()
        {
            boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
            assertTrue(useradded);
            boolean useredited1 = this.editEmail("achiadg", "achiadg@post.bgu.ac.il");
            assertTrue(useredited1);
            boolean useredited2 = this.editEmail("achiadg", "achiadg@gmail.com");
            assertTrue(useredited2);
            boolean deleteUser1 = this.deleteUser("achiadg");
            assertTrue(deleteUser1);
            boolean userfound = this.searchUser("achiadg");
            assertFalse(userfound);
        }


        @Test
        public void testEditPasswordNoChange()
        {
            boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
            assertTrue(useradded);
            boolean useredited = this.editPassword("achiadg", "aChi12#*");
            assertFalse(useredited);
            boolean deleteUser1 = this.deleteUser("achiadg");
            assertTrue(deleteUser1);
            boolean userfound = this.searchUser("achiadg");
            assertFalse(userfound);
        }

        @Test
        public void testEditEmailNoChange()
        {
            boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
            assertTrue(useradded);
            boolean useredited = this.editEmail("achiadg", "achiadg@gmail.com");
            assertFalse(useredited);
            boolean deleteUser1 = this.deleteUser("achiadg");
            assertTrue(deleteUser1);
            boolean userfound = this.searchUser("achiadg");
            assertFalse(userfound);
        }


        @Test
        public void testEditPasswordNoValid()
        {
            boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
            assertTrue(useradded);
            boolean useredited = this.editPassword("achiadg", "aChi1\n2#*");
            assertFalse(useredited);
            boolean deleteUser1 = this.deleteUser("achiadg");
            assertTrue(deleteUser1);
            boolean userfound = this.searchUser("achiadg");
            assertFalse(userfound);
        }

        @Test
        public void testEditEmailNoValid()
        {
            boolean useradded = this.registerUser("achiadg","aChi12#*","achiadg@gmail.com",new DateTime(1991,4,20,22,13));
            assertTrue(useradded);
            boolean useredited = this.editEmail("achiadg", "achi\tadg@g\nmail.com");
            assertFalse(useredited);
            boolean deleteUser1 = this.deleteUser("achiadg");
            assertTrue(deleteUser1);
            boolean userfound = this.searchUser("achiadg");
            assertFalse(userfound);
        }


}
