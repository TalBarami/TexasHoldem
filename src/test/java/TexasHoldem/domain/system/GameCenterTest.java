package TexasHoldem.domain.system;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameSettings;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;

import static TexasHoldem.domain.game.GameSettings.GamePolicy.LIMIT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by RonenB on 4/14/2017.
 */
public class GameCenterTest {
    private GameCenter gc;
    private LocalDate now;
    private String testUser1,testUser1Pass,testUser1Email;
    private String testUser2,testUser2Pass,testUser2Email;

    private GameSettings tournamentGameSettings;
    private GameSettings realMoneyGameSettings;

    /*private User testUser1;
    private User testUser2;
    private User testUser3;
    private User testUser4;*/

    @Before
    public void setUp() throws Exception {
        gc=new GameCenter();
        now=LocalDate.now();
        testUser1="testUser1";
        testUser1Pass="1111111";
        testUser1Email="test1@gmail.com";
        testUser2="testUser2";
        testUser2Pass="2222222";
        testUser1Email="test2@gmail.com";


        tournamentGameSettings =new GameSettings("tournamentTest",LIMIT,100,100,100,100,2,4,true);
        realMoneyGameSettings =new GameSettings("realMoneyTest",LIMIT,100,100,100,0,2,4,false);
        /*testUser1=new User("testUser1","00000","test1@gmail.com", LocalDate.of(1111,10,10),null);
        testUser2=new User("testUser2","11111","test2@gmail.com", LocalDate.of(2222,10,10),null);
        testUser3=new User("testUser3","22222","test3@gmail.com", LocalDate.of(3333,10,10),null);
        testUser4=new User("testUser4","33333","test4@gmail.com", LocalDate.of(4444,10,10),null);
        testUser1.deposit(1500,true);
        testUser2.deposit(2500,true);*/

    }

    @After
    public void tearDown() throws Exception {
        gc=null;
        now=null;
        testUser1=null;
        testUser1Pass=null;
        testUser1Email=null;
        testUser2=null;
        testUser2Pass=null;
        testUser2Email=null;

        tournamentGameSettings =null;
        realMoneyGameSettings=null;
        /*testUser1=null;
        testUser2=null;
        testUser3=null;
        testUser4=null;*/
    }

    @Test
    public void registerUserTest() throws Exception {
        Assert.assertNull(gc.getUser(testUser1));
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        assertThat(gc.getUser(testUser1).getPassword(),is(testUser1Pass));
        assertThat(gc.getUser(testUser1).getCurrLeague(),is(gc.getLeagueManager().getDefaultLeagueForNewUsers()));
    }

    @Test
    public void deleteUserFailTest() throws Exception {
        try{
            gc.deleteUser(testUser1);
            fail("Exception didn't thrown, there is no registered user with selected name.");
        }catch(EntityDoesNotExistsException e){}
    }

    @Test
    public void deleteUserSuccessTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        assertNotNull(gc.getUser(testUser1));
        gc.deleteUser(testUser1);
        assertNull(gc.getUser(testUser1));
    }

    @Test
    public void loginFailTest() throws Exception {
        try{
            gc.login("failFail","1234");
        }catch(EntityDoesNotExistsException e){}

        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        assertTrue(gc.getLoggedInUsers().isEmpty());

        try{
            gc.login(testUser1,"0000000");
            fail();
        }catch(LoginException e){}
    }

    @Test
    public void loginSuccessTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        assertTrue(gc.getLoggedInUsers().isEmpty());
        gc.login(testUser1,testUser1Pass);
        assertThat(gc.getLoggedInUsers().size(),is(1));
        assertThat(gc.getLoggedInUsers().get(0).getUsername(),is(testUser1));
    }

    @Test
    public void logoutFailTest() throws Exception {
        try{
            gc.logout("fail");
            fail();
        }catch(InvalidArgumentException e){}
    }

    @Test
    public void logoutSuccesslTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.login(testUser1,testUser1Pass);
        assertThat(gc.getLoggedInUsers().size(),is(1));
        gc.logout(testUser1);
        assertTrue(gc.getLoggedInUsers().isEmpty());

        gc.registerUser(testUser2,testUser2Pass,testUser2Email,now,null);
        gc.login(testUser1,testUser1Pass);
        gc.login(testUser2,testUser2Pass);
        assertTrue(gc.getUser(testUser1).getGamePlayerMappings().isEmpty());
        assertTrue(gc.getUser(testUser2).getGamePlayerMappings().isEmpty());

        gc.getUser(testUser1).deposit(10000000,true);
        gc.createGame(testUser1,tournamentGameSettings);
        gc.joinGame(testUser2,tournamentGameSettings.getName(),true);
        assertFalse(gc.getUser(testUser1).getGamePlayerMappings().isEmpty());
        assertFalse(gc.getUser(testUser2).getGamePlayerMappings().isEmpty());
        Game game=gc.getGameByName(tournamentGameSettings.getName());
        assertTrue(game.getSpectators().get(0).getUser().getUsername().equals(testUser2));
        assertTrue(game.getPlayers().get(0).getUser().getUsername().equals(testUser1));
        gc.logout(testUser1);
        gc.logout(testUser2);
        assertTrue(gc.getUser(testUser1).getGamePlayerMappings().isEmpty());
        assertTrue(gc.getUser(testUser2).getGamePlayerMappings().isEmpty());
        assertTrue(game.getSpectators().isEmpty());
        assertTrue(game.getPlayers().isEmpty());
    }

    @Test
    public void editProfileFailTest() throws Exception {
        try{
            gc.editProfile("fail",testUser1,testUser1Pass,testUser1Email,now);
            fail();
        }catch(EntityDoesNotExistsException e){}

        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.registerUser(testUser2,testUser2Pass,testUser2Email,now,null);
        try{
            gc.editProfile(testUser1,testUser2,testUser1Pass,testUser1Email,now);
            fail();
        }catch(InvalidArgumentException e){
            if(!e.getMessage().equals("Selected user name already exist."))
                fail();
        }

        try{
            gc.editProfile(testUser1,testUser1,testUser1Pass,testUser2Email,now);
            fail();
        }catch(InvalidArgumentException e){
            if(!e.getMessage().equals("Selected e-mail already exist."))
                fail();
        }

        try{
            gc.editProfile(testUser1,testUser2,testUser1Pass,testUser2Email,now);
            fail();
        }catch(InvalidArgumentException e){
            if(!e.getMessage().equals("Selected user name and e-mail already exist."))
                fail();
        }
    }

    @Test
    public void editProfileSuccessTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        assertNull(gc.getUser("newName"));
        gc.editProfile(testUser1,"newName","newPass","new@gmail.com",now.minusYears(2));
        assertNull(gc.getUser(testUser1));
        User u=gc.getUser("newName");
        assertThat(u.getPassword(),is("newPass"));
        assertThat(u.getEmail(),is("new@gmail.com"));
        assertThat(u.getDateOfBirth(),is(now.minusYears(2)));
    }

    @Test
    public void depositMoney() throws Exception {
    }

    @Test
    public void createGame() throws Exception {
    }

    @Test
    public void joinGame() throws Exception {
    }

    @Test
    public void leaveGame() throws Exception {
    }

    @Test
    public void findAvailableGames() throws Exception {
    }

    @Test
    public void findSpectateableGames() throws Exception {
    }

}