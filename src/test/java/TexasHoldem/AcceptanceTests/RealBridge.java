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
            service.register(username, password, email, dateTime, img);
        }catch (InvalidArgumentException e){
            return false;
        }
        return true;
    }

    public boolean searchUser(String username)
    {
        try {
            if(service.getUser(username) != null)
              return true;
            else
              return false;
        }catch (InvalidArgumentException e){
            return false;}
    }

    public  boolean deleteUser(String username)
    {
        try {
            service.deleteUser(username);
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
            service.login(username,password);
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
            service.logout(username);
        } catch (InvalidArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editUserName(String oldusername, String newusername, String password, String email, LocalDate date) {
        try {
            service.editProfile(oldusername,newusername,  password, email, date);
        }catch (InvalidArgumentException e){
            return false;
        } catch (EntityDoesNotExistsException e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean addbalancetouserwallet(String username, int amounttoadd) {
        try {
            service.deposit(username,amounttoadd);
        }catch (ArgumentNotInBoundsException e){
            return false;
        } catch (InvalidArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean createnewgame(String username, String gamename, GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        try {
            service.createGame(username,gamename,policy,0,minimumbet,buyin,chippolicy,minplayers,maxplayers,spectateisvalid);
        }catch (NoBalanceForBuyInException e){
            return false;
        }catch (  InvalidArgumentException e){
            return false;
        }catch (  ArgumentNotInBoundsException e){
            return false;
        }
        return true;
    }

    public boolean closegame(String gamename) {
        return true;
    }

    public boolean joinexistinggame(String username, String gamename, boolean spec) {
        try {
            service.joinGame(username, gamename,spec);
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
        if(service.findGamesByUsername(username).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebytypepolicy(GamePolicy policy) {
        if(service.findGamesByGamePolicy(policy).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebybuyin(int buyin) {
        List<Game> games =service.findGamesByMaximumBuyIn(buyin);
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
        if(service.findGamesByChipPolicy(chippolicy).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebyminbet(int minbet) {
        if(service.findGamesByMinimumBet(minbet).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebyminplayers(int numplayers) {
        if(service.findGamesByMinimumPlayers(numplayers).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebymaxplayers(int numplayers) {
        if(service.findGamesByMaximumPlayers(numplayers).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean searchgamebyspectateisvalid(boolean spectatingavailable) {
        if(service.findSpectatableGames().size() != 0)
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
            service.leaveGame(username, gamename);
        }catch (GameException e){
            return false;
        }
        return true;
    }

    public boolean replaynonactivegame(String username, String gamename) {
        return true;
    }

    public boolean saveturns(String username, String gamename, List<String> turns) {
        return true;
    }

    public boolean searchavailablegamestojoin(String username) {
        if(service.findAvailableGames(username).size() != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean playturn(String username,String gamename , String action) {
        return true;
    }

    public int getPotSize(String gamename) {
        return -1;
    }

    public int getPlayerbalance(String username, String gamename) {
        return -1;
    }

    public boolean playturnraise(String username, String gamename, String action, int amounttoraise) {
        return true;
    }

    public int getnumofround(String gamename) {
        return -1;
    }

    public int getnumofplayersinround(String gamename, int numofround) {
        return -1;
    }

    public boolean setuserleague(String adminname, String username, int league) {
        return true;
    }

    public boolean searchgamebypotsize(int pot) {
        return true;
    }

    public boolean setcriteriatomoveleague(String adminname, int criteria) {
        return true;
    }

    @Override
    public boolean spectateactivegame(String username, String gamename, boolean spec) {
        try {
            service.spectateGame(username, gamename,spec);
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
}
