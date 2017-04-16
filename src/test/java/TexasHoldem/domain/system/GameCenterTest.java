package TexasHoldem.domain.system;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameSettings;
import TexasHoldem.domain.user.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

import static TexasHoldem.domain.game.GameSettings.GamePolicy.LIMIT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameCenterTest {
    private GameCenter gc;
    private LocalDate now;
    private String testUser1,testUser1Pass,testUser1Email;
    private String testUser2,testUser2Pass,testUser2Email;
    private String testUser3,testUser3Pass,testUser3Email;

    private GameSettings tournamentGameSettings;
    private GameSettings tournamentGameSettings2;
    private GameSettings realMoneyGameSettings;
    private GameSettings realMoneyGameSettings2;

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
        testUser3="testUser3";
        testUser3Pass="3333333";
        testUser3Email="test3@gmail.com";

        tournamentGameSettings =new GameSettings("tournamentTest",LIMIT,100,100,100,100,2,4,true);
        tournamentGameSettings2 =new GameSettings("tournamentTest2",LIMIT,100,100,2000,100,2,4,false);
        realMoneyGameSettings =new GameSettings("realMoneyTest",LIMIT,100,100,100,0,2,4,false);
        realMoneyGameSettings2 =new GameSettings("realMoneyTest2",LIMIT,100,100,100,0,2,2,true);
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
        testUser3=null;
        testUser3Pass=null;
        testUser3Email=null;

        tournamentGameSettings =null;
        tournamentGameSettings2 =null;
        realMoneyGameSettings=null;
        realMoneyGameSettings2=null;
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
    public void logoutSuccessTest() throws Exception {
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
    public void depositMoneyFailTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        try{
            gc.depositMoney(testUser1,-100);
        }catch(ArgumentNotInBoundsException e){}
    }

    @Test
    public void depositMoneySuccessTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        assertThat(gc.getUser(testUser1).getAmountEarnedInLeague(),is(0));
        assertThat(gc.getUser(testUser1).getBalance(),is(0));
        gc.depositMoney(testUser1,1000);
        assertThat(gc.getUser(testUser1).getAmountEarnedInLeague(),is(0));
        assertThat(gc.getUser(testUser1).getBalance(),is(1000));
    }

    @Test
    public void createGameFailTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        tournamentGameSettings.setMaximalPlayers(0);
        try{
            gc.createGame(testUser1,tournamentGameSettings);
        }catch(InvalidArgumentException e){
            if(!e.getMessage().equals("Maximal amount of players is greater than minimal."))
                fail();
        }

        tournamentGameSettings.setMaximalPlayers(10);
        try{
            gc.createGame(testUser1,tournamentGameSettings);
        }catch(ArgumentNotInBoundsException e){
            if(!e.getMessage().contains("Players amount should be between 2 and 9"))
                fail();
        }

        tournamentGameSettings.setMaximalPlayers(6);
        try{
            gc.createGame(testUser1,tournamentGameSettings);
        }catch(NoBalanceForBuyInException e){
            if(!e.getMessage().contains("User's balance below the selected game buy in."))
                fail();
        }
    }

    @Test
    public void createGameSuccessTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.depositMoney(testUser1,tournamentGameSettings.getBuyInPolicy()+100);
        assertNull(gc.getGameByName(tournamentGameSettings.getName()));

        gc.createGame(testUser1,tournamentGameSettings);
        User user=gc.getUser(testUser1);
        Game game=gc.getGameByName(tournamentGameSettings.getName());
        assertTrue(user.getGamePlayerMappings().containsKey(game));
        assertThat(user.getBalance(),is(100));
        assertThat(game.getLeague(),is(user.getCurrLeague()));
        assertThat(game.getPlayers().size(),is(1));
        assertTrue(game.getSpectators().isEmpty());
    }

    @Test
    public void joinGameFailTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.registerUser(testUser2,testUser2Pass,testUser2Email,now,null);
        gc.registerUser(testUser3,testUser3Pass,testUser3Email,now,null);

        try{
            gc.joinGame(testUser1,"fail",false);
            fail();
        }catch(InvalidArgumentException e){
            if(!e.getMessage().equals("There is no game in the system with selected name."))
                fail();
        }

        gc.createGame(testUser1,realMoneyGameSettings);
        try{
            gc.joinGame(testUser2,realMoneyGameSettings.getName(),true);
            fail();
        }catch(CantSpeactateThisRoomException e){
            if(!e.getMessage().equals("Selected game can't be spectated due to it's settings."))
                fail();
        }

        gc.getUser(testUser3).setCurrLeague(gc.getGameByName(realMoneyGameSettings.getName()).getLeague()+1);
        try{
            gc.joinGame(testUser3,realMoneyGameSettings.getName(),false);
            fail();
        }catch(LeaguesDontMatchException e){
            if(!e.getMessage().contains("Can't join game, user's league is"))
                fail();
        }

        gc.getGameByName(realMoneyGameSettings.getName()).getSettings().setMaximalPlayers(2);
        gc.joinGame(testUser2,realMoneyGameSettings.getName(),false);
        gc.getUser(testUser3).setCurrLeague(gc.getGameByName(realMoneyGameSettings.getName()).getLeague());
        try{
            gc.joinGame(testUser3,realMoneyGameSettings.getName(),false);
            fail();
        }catch(GameIsFullException e){
            if(!e.getMessage().contains("Can't join game as player because it's full."))
                fail();
        }

        gc.depositMoney(testUser1,tournamentGameSettings.getBuyInPolicy()+100);
        gc.createGame(testUser1,tournamentGameSettings);
        try{
            gc.joinGame(testUser2,tournamentGameSettings.getName(),false);
            fail();
        }catch(NoBalanceForBuyInException e){
            if(!e.getMessage().contains("Buy in is"))
                fail();
        }
    }

    @Test
    public void joinGameSuccessTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.registerUser(testUser2,testUser2Pass,testUser2Email,now,null);

        gc.depositMoney(testUser1,tournamentGameSettings.getBuyInPolicy()+100);
        gc.depositMoney(testUser2,tournamentGameSettings.getBuyInPolicy()+100);

        gc.createGame(testUser1,tournamentGameSettings);
        gc.joinGame(testUser2,tournamentGameSettings.getName(),false);

        Game g=gc.getGameByName(tournamentGameSettings.getName());
        User u=gc.getUser(testUser2);
        assertTrue(u.getGamePlayerMappings().containsKey(g));
        assertThat(u.getBalance(),is(100));
        assertThat(g.getPlayers().size(),is(2));
        assertThat(g.getSpectators().size(),is(0));
    }

    @Test
    public void leaveGameFailTest() throws Exception {
        try{
            gc.leaveGame(testUser1,tournamentGameSettings.getName());
            fail();
        }catch(InvalidArgumentException e){
            if(!e.getMessage().contains(String.format("User '%s' doesn't exist in the system.",testUser1)))
                fail();
        }

        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        try{
            gc.leaveGame(testUser1,tournamentGameSettings.getName());
            fail();
        }catch(InvalidArgumentException e){
            if(!e.getMessage().contains(String.format("Game '%s' doesn't exist in the system.",tournamentGameSettings.getName())))
                fail();
        }

        gc.createGame(testUser1,realMoneyGameSettings);
        gc.registerUser(testUser2,testUser2Pass,testUser2Email,now,null);
        try{
            gc.leaveGame(testUser2,realMoneyGameSettings.getName());
            fail();
        }catch(GameException e){
            if(!e.getMessage().contains(String.format("User '%s' can't leave game '%s', since he is not playing inside.",testUser2,realMoneyGameSettings.getName())))
                fail();
        }
    }

    @Test
    public void leaveGameSuccessTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.registerUser(testUser2,testUser2Pass,testUser2Email,now,null);
        gc.createGame(testUser1,realMoneyGameSettings);
        gc.joinGame(testUser2,realMoneyGameSettings.getName(),false);

        gc.leaveGame(testUser2,realMoneyGameSettings.getName());

        Game g=gc.getGameByName(realMoneyGameSettings.getName());
        assertFalse(gc.isArchived(g));
        assertThat(g.getPlayers().size(),is(1));
        assertThat(g.getPlayers().get(0).getUser().getUsername(),is(testUser1));
        User u1=gc.getUser(testUser1);
        User u2=gc.getUser(testUser2);
        assertTrue(u1.getGamePlayerMappings().containsKey(g));
        assertTrue(!u2.getGamePlayerMappings().containsKey(g));
    }

    @Test
    public void leaveGameSuccessWithArchivingTest() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.createGame(testUser1,realMoneyGameSettings);
        Game g=gc.getGameByName(realMoneyGameSettings.getName());

        gc.leaveGame(testUser1,realMoneyGameSettings.getName());
        assertTrue(gc.isArchived(g));
    }


    @Test
    public void findAvailableGames() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.registerUser(testUser2,testUser2Pass,testUser2Email,now,null);
        gc.registerUser(testUser3,testUser3Pass,testUser3Email,now,null);

        gc.depositMoney(testUser1,100000000);

        gc.createGame(testUser1,tournamentGameSettings);
        gc.createGame(testUser1,tournamentGameSettings2);
        gc.createGame(testUser1,realMoneyGameSettings);
        gc.createGame(testUser1,realMoneyGameSettings2);

        Game g1=gc.getGameByName(tournamentGameSettings.getName());
        Game g2=gc.getGameByName(tournamentGameSettings2.getName());
        Game g3=gc.getGameByName(realMoneyGameSettings.getName());
        Game g4=gc.getGameByName(realMoneyGameSettings2.getName());

        List<Game> games=gc.findAvailableGames(testUser2);
        assertThat(games.size(),is(2));
        assertTrue(games.contains(g3));
        assertTrue(games.contains(g4));

        gc.depositMoney(testUser2,1000);
        games=gc.findAvailableGames(testUser2);
        assertThat(games.size(),is(3));
        assertTrue(games.contains(g1));

        gc.depositMoney(testUser2,1000000);
        games=gc.findAvailableGames(testUser2);
        assertThat(games.size(),is(4));
        assertTrue(games.contains(g2));

        games=gc.findAvailableGames(testUser3);
        assertThat(games.size(),is(2));
        assertTrue(games.contains(g3));
        assertTrue(games.contains(g4));

        gc.joinGame(testUser2,realMoneyGameSettings2.getName(),false);
        games=gc.findAvailableGames(testUser3);
        assertThat(games.size(),is(1));
        assertTrue(games.contains(g3));
    }

    @Test
    public void findSpectateableGames() throws Exception {
        gc.registerUser(testUser1,testUser1Pass,testUser1Email,now,null);
        gc.depositMoney(testUser1,100000000);

        List<Game> games=gc.findSpectateableGames();
        assertThat(games.size(),is(0));


        gc.createGame(testUser1,tournamentGameSettings);
        Game g1=gc.getGameByName(tournamentGameSettings.getName());

        games=gc.findSpectateableGames();
        assertThat(games.size(),is(1));
        assertTrue(games.contains(g1));

        gc.createGame(testUser1,tournamentGameSettings2);
        Game g2=gc.getGameByName(tournamentGameSettings2.getName());

        games=gc.findSpectateableGames();
        assertThat(games.size(),is(1));
        assertTrue(games.contains(g1));

        gc.createGame(testUser1,realMoneyGameSettings);
        Game g3=gc.getGameByName(realMoneyGameSettings.getName());

        games=gc.findSpectateableGames();
        assertThat(games.size(),is(1));
        assertTrue(games.contains(g1));

        gc.createGame(testUser1,realMoneyGameSettings2);
        Game g4=gc.getGameByName(realMoneyGameSettings2.getName());

        games=gc.findSpectateableGames();
        assertThat(games.size(),is(2));
        assertTrue(games.contains(g1));
        assertTrue(games.contains(g4));
    }
}