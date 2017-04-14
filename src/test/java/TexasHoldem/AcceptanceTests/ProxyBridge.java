package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import java.time.LocalDate;

import java.awt.image.BufferedImage;
import java.util.List;
import TexasHoldem.domain.game.GameSettings;
/**
 * Created by אחיעד on 05/04/2017.
 */
public class ProxyBridge implements Bridge {

    private Bridge real;

    public ProxyBridge(){
        real = null;
    }

    public void setRealBridge(Bridge impl)
    {
        if(real == null)
        {
            real = impl;
        }
    }


    public boolean registerUser(String username, String password, String email, LocalDate dateTime, BufferedImage img) {
       return real.registerUser(username, password, email, dateTime, img);
    }

    public boolean searchUser(String username) {
        return real.searchUser(username);
    }

    public  boolean deleteUser(String username){
        return real.deleteUser(username);
    }

    public boolean login(String username, String password)
    {
        return real.login(username, password);
    }
    public boolean logout(String username)
    {
        return real.logout(username);
    }

    @Override
    public boolean editUserName(String oldusername, String newusername, String password, String email, LocalDate date) {
        return real.editUserName(oldusername,newusername, password, email, date);
    }


    public boolean addbalancetouserwallet(String username,int amounttoadd) {
        return real.addbalancetouserwallet(username,amounttoadd);
    }

    public boolean createnewgame(String username, String gamename, GameSettings.GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        return real.createnewgame(username, gamename, policy, buyin,chippolicy, minimumbet,  minplayers, maxplayers, spectateisvalid);
    }

    public boolean closegame(String gamename) {
        return true;
    }

    public boolean joinexistinggame(String username, String gamename, boolean spec) {
       return real.joinexistinggame(username, gamename,spec);
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
        return real.spectateactivegame(username,gamename, spec);
    }

}
