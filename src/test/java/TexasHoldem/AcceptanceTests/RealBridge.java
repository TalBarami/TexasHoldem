package TexasHoldem.AcceptanceTests;

import TexasHoldem.common.Exceptions.*;
import org.joda.time.DateTime;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

import TexasHoldem.service.TexasHoldemService;
import TexasHoldem.domain.game.GameSettings;
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
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (InvalidArgumentException e) {
            return false;
        }
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

    public boolean createnewgame(String username, String gamename, GameSettings.GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
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
        return true;
    }

    public boolean searchgamebytypepolicy(String type) {
        return true;
    }

    public boolean searchgamebybuyin(int buyin) {
        return true;
    }

    public boolean searchgamebychippolicy(int chippolicy) {
        return true;
    }

    public boolean searchgamebyminbet(int minbet) {
        return true;
    }

    public boolean searchgamebyminplayers(int numplayers) {
        return true;
    }

    public boolean searchgamebymaxplayers(int numplayers) {
        return true;
    }

    public boolean searchgamebyspectateisvalid(boolean spectatingavailable) {
        return true;
    }

    public boolean leavegame(String username, String choise, String gamename) {
        return true;
    }

    public boolean replaynonactivegame(String username, String gamename) {
        return true;
    }

    public boolean saveturns(String username, String gamename, List<String> turns) {
        return true;
    }

    public boolean searchavailablegamestojoin(String username) {
        return true;
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
