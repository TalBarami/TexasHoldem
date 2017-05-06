package TexasHoldem.AcceptanceTests;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GamePolicy;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

import TexasHoldem.service.TexasHoldemService;

import javax.security.auth.login.LoginException;

/**
 * Created by אחיעד on 05/04/2017.
 */
public class RealBridge implements Bridge {

    TexasHoldemService service;

    public RealBridge(){
        service = new TexasHoldemService();
    }


    public boolean registerUser(String username, String password, String email, LocalDate dateTime, BufferedImage img)
    {
        try {
            service.userService().register(username, password, email, dateTime, img);
        }catch (InvalidArgumentException e){
            return false;
        }
        return true;
    }

    public boolean searchUser(String username)
    {
        try {
            if(service.userService().getUser(username) != null)
              return true;
            else
              return false;
        }catch (InvalidArgumentException e){
            return false;}
    }

    public  boolean deleteUser(String username)
    {
        try {
            service.userService().deleteUser(username);
        }catch (EntityDoesNotExistsException e){
            return false;
        } catch (InvalidArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean login(String username, String password)
    {
        try {
            service.userService().login(username,password);
        }catch ( LoginException e){
            return false;
        } catch (InvalidArgumentException e) {
            return false;
        } catch (EntityDoesNotExistsException e) {
            return false;
        }
        return true;
    }

    public boolean logout(String username)
    {
        try {
            service.userService().logout(username);
        } catch (InvalidArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editUserName(String oldusername, String newusername, String password, String email, LocalDate date) {
        try {
            service.userService().editProfile(oldusername,newusername,  password, email, date);
        }catch (InvalidArgumentException e){
            return false;
        } catch (EntityDoesNotExistsException e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean addbalancetouserwallet(String username, int amounttoadd) {
        try {
            service.userService().deposit(username,amounttoadd);
        }catch (ArgumentNotInBoundsException e){
            return false;
        } catch (InvalidArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean createnewgame(String username, String gamename, GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        try {
            service.gameService().createGame(username,gamename,policy,0,minimumbet,buyin,chippolicy,minplayers,maxplayers,spectateisvalid);
        }catch (NoBalanceForBuyInException e){
            return false;
        }catch (  InvalidArgumentException e){
            return false;
        }catch (  ArgumentNotInBoundsException e){
            return false;
        }
        return true;
    }


    public boolean joinexistinggame(String username, String gamename, boolean spec) {
        try {
            service.gameService().joinGame(username, gamename,spec);
        }catch (GameIsFullException e){
            return false;
        }catch (  InvalidArgumentException e){
            return false;
        }catch (  LeaguesDontMatchException e){
            return false;
        }catch (  CantSpeactateThisRoomException e){
            return false;
        }catch (   NoBalanceForBuyInException e){
            return false;
        }
        return true;
    }

    public boolean searchgamebyplayername(String username) {
        List<Game> games = null;
        try {
            games = service.searchService().findGamesByUsername(username);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        if(games.size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebytypepolicy(GamePolicy policy) {
        List<Game> games = null;
        try {
            games = service.searchService().findGamesByGamePolicy(policy);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        if(games.size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebybuyin(int buyin) {
        List<Game> games =service.searchService().findGamesByMaximumBuyIn(buyin);
        if(games.size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebychippolicy(int chippolicy) {
        if(service.searchService().findGamesByChipPolicy(chippolicy).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebyminbet(int minbet) {
        if(service.searchService().findGamesByMinimumBet(minbet).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebyminplayers(int numplayers) {
        if(service.searchService().findGamesByMinimumPlayers(numplayers).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebymaxplayers(int numplayers) {
        if(service.searchService().findGamesByMaximumPlayers(numplayers).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebyspectateisvalid(boolean spectatingavailable) {
        if(service.searchService().findSpectatableGames().size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean leavegame(String username, String choise, String gamename) {
        try {
            service.gameService().leaveGame(username, gamename);
        }catch (GameException e){
            return false;
        }
        return true;
    }

 /*   public boolean replaynonactivegame(String username, String gamename) {
        return true;
    } */

    public boolean searchavailablegamestojoin(String username) {
        List<Game> games = null;
        try {
            games = service.searchService().findAvailableGames(username);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        if(games.size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getPotSize(String gamename) {
        return -1;
    }

    public int getPlayerbalance(String username, String gamename) {
        return -1;
    }


 /*   public boolean setuserleague(String adminname, String username, int league) {
//            try {
//                service.leagueService().setDefaultLeague(adminname, league);
//            } catch (NoPermissionException e) {
//                e.printStackTrace();
//                return false;
//            }
        return true;
    } */

    public boolean searchgamebypotsize(int pot) {
        if(service.searchService().findGamesByPotSize(pot).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

  /*  public boolean setcriteriatomoveleague(String adminname, int criteria) {
//        try {
//            service.leagueService().setLeagueCriteria(adminname, criteria);
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    } */


   public boolean spectateactivegame(String username, String gamename, boolean spec) {
        try {
            service.gameService().spectateGame(username, gamename,spec);
        }catch (GameIsFullException | NoBalanceForBuyInException | LeaguesDontMatchException | InvalidArgumentException | CantSpeactateThisRoomException e){
            return false;
        }
        return true;
    }


  /*  public boolean moveuserleague(String admin, String username, int league) {
//        try {
//            service.leagueService().setUserLeague(admin, username, league );
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    } */


    public void startgame(String gamename) {

    }

    // FIXME: Add try / catch if necessary & remove redundant argument "amount".
    public boolean playcall(String username, String gamename, int amount) {
        service.gameService().playCall(username, gamename);
        return true;
    }

    // FIXME: Add try / catch if necessary & remove redundant argument "amount".
    public boolean playcheck(String username, String gamename, int amount) {
        service.gameService().playCheck(username, gamename);
        return true;
    }

    // FIXME: Add try / catch if necessary.
    public boolean playraise(String username, String gamename, int amount) {
        service.gameService().playRaise(username, gamename, amount);
        return true;
    }

    // FIXME: Add try / catch if necessary & remove redundant argument "amount".
    public boolean playfold(String username, String gamename, int amount) {
        service.gameService().playFold(username, gamename);
        return true;
    }

    @Override
    public int getuserleague(String username) {
        try {
            return service.userService().getUserLeague(username);
        } catch (InvalidArgumentException e) {
           return -1;
        }
    }
}
