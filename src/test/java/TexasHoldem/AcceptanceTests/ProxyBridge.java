package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;

import TexasHoldem.domain.users.User;

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


    public boolean registerUser(String username, String password, String email, DateTime dateTime) {
        return true;
    }

    public boolean searchUser(String username) {
        return true;
    }

    public  boolean deleteUser(String username){
        return true;
    }

    public boolean login(String username, String password)
    {
        return true;
    }
    public boolean logout(String username)
    {
        return true;
    }

    public boolean editUserName(String oldusername, String newusername) {
        return true;
    }

    public boolean editPassword(String oldusername, String password) {
        return true;
    }

    public boolean editEmail(String oldusername, String email) {
        return true;
    }

    public boolean editDateOfBirth(String oldusername, DateTime dateofbirth) {
        return true;
    }

    public boolean editImage(String oldusername, BufferedImage newimage) {
        return true;
    }

    public boolean addbalancetouserwallet(String username,int amounttoadd) {
        return true;
    }

    public boolean createnewgame(String username, String gamename, String policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        return true;
    }

    public boolean closegame(String gamename) {
        return true;
    }

    public boolean joinexistinggame(String username, String gamename) {
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

    public boolean spectateactivegame(String username, String gamename) {
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

}
