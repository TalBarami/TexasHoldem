package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.game.GamePolicy;

import java.time.LocalDate;

import java.awt.image.BufferedImage;
import java.util.List;

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

    public boolean createnewgame(String username, String gamename, GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        return real.createnewgame(username, gamename, policy, buyin,chippolicy, minimumbet,  minplayers, maxplayers, spectateisvalid);
    }

    public boolean closegame(String gamename) {
        return true;
    }

    public boolean joinexistinggame(String username, String gamename, boolean spec) {
       return real.joinexistinggame(username, gamename,spec);
    }

    public boolean searchgamebyplayername(String username) {
        return real.searchavailablegamestojoin(username);
    }

    public boolean searchgamebytypepolicy(GamePolicy policy) {
        return real.searchgamebytypepolicy(policy);
    }

    public boolean searchgamebybuyin(int buyin) {
        return real.searchgamebybuyin(buyin);
    }

    public boolean searchgamebychippolicy(int chippolicy) {
        return real.searchgamebychippolicy(chippolicy);
    }

    public boolean searchgamebyminbet(int minbet) {
        return real.searchgamebyminbet(minbet);
    }

    public boolean searchgamebyminplayers(int numplayers) {
        return real.searchgamebyminplayers(numplayers);
    }

    public boolean searchgamebymaxplayers(int numplayers) {
        return real.searchgamebymaxplayers(numplayers);
    }

    public boolean searchgamebyspectateisvalid(boolean spectatingavailable) {
        return real.searchgamebyspectateisvalid(spectatingavailable);
    }

    public boolean leavegame(String username, String choise, String gamename) {
        return  real.leavegame(username,choise, gamename);
    }

    public boolean replaynonactivegame(String username, String gamename) {
        return true;
    }

    public boolean saveturns(String username, String gamename, List<String> turns) {
        return true;
    }

    public boolean searchavailablegamestojoin(String username) {
       return real.searchavailablegamestojoin(username);
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
