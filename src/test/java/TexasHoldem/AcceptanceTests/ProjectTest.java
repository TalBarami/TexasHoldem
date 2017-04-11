package TexasHoldem.AcceptanceTests;

import junit.framework.TestCase;
import org.joda.time.DateTime;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by אחיעד on 05/04/2017.
 */

public abstract class ProjectTest extends TestCase{
    private Bridge bridge;

    public void setUp()
    {
        this.bridge = Driver.getBridge();
    }


    public boolean searchUser(String username) {
        return bridge.searchUser(username);
    }

    public boolean deleteUser(String username) {
        return bridge.deleteUser(username);
    }

    public boolean registerUser(String username, String password, String email, DateTime dateofbirth) {
        return bridge.registerUser(username, password, email, dateofbirth);
    }

    public boolean login(String username, String password) {
        return bridge.login(username,password);
    }

    public boolean logout(String username) {
        return bridge.logout(username);
    }

    public boolean editUserName(String oldusername, String newusername) {
        return bridge.editUserName(oldusername, newusername);
    }

    public boolean editPassword(String oldusername, String password) {
        return bridge.editPassword(oldusername, password);
    }

    public boolean editEmail(String oldusername, String email)
    {
       return bridge.editEmail(oldusername, email);
    }

    public boolean editDateOfBirth(String oldusername, DateTime dateofbirth) {
        return bridge.editDateOfBirth(oldusername,dateofbirth);
    }

    public boolean editImage(String oldusername, BufferedImage newimage) {
        return bridge.editImage(oldusername, newimage);
    }

    public boolean addbalancetouserwallet(String username, int amounttoadd) {
        return bridge.addbalancetouserwallet(username, amounttoadd);
    }

    public boolean createnewgame(String username,String gamename, String policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid, int league) {
        return bridge.createnewgame(username,gamename, policy, buyin, chippolicy, minimumbet, minplayers, maxplayers, spectateisvalid, league);
    }

    public boolean closegame(String gamename) {
        return bridge.closegame(gamename);
    }

    public boolean joinexistinggame(String username, String gamename) {
        return bridge.joinexistinggame(username, gamename);
    }

    public boolean searchgamebyplayername(String username) {
        return bridge.searchgamebyplayername(username);
    }

    public boolean searchgamebytypepolicy(String type) {
        return bridge.searchgamebytypepolicy(type);
    }

    public boolean searchgamebybuyin(int buyin) {
        return bridge.searchgamebybuyin(buyin);
    }

    public boolean searchgamebychippolicy(int chippolicy) {
        return bridge.searchgamebychippolicy(chippolicy);
    }

    public boolean searchgamebyminbet(int minbet) {
        return bridge.searchgamebyminbet(minbet);
    }

    public boolean searchgamebyminplayers(int numplayers) {
        return bridge.searchgamebyminplayers(numplayers);
    }

    public boolean searchgamebymaxplayers(int numplayers) {
        return bridge.searchgamebymaxplayers(numplayers);
    }

    public boolean searchgamebyspectateisvalid(boolean spectatingavailable) {
        return bridge.searchgamebyspectateisvalid(spectatingavailable);
    }

    public boolean spectateactivegame(String username, String gamename) {
        return bridge.spectateactivegame(username,gamename);
    }

    public boolean leavegame(String username, String choise, String gamename) {
        return bridge.leavegame(username, choise, gamename);
    }

    public boolean replaynonactivegame(String username, String gamename) {
        return bridge.replaynonactivegame(username,gamename);
    }

    public boolean saveturns(String username, String gamename, List<String> turns) {
        return bridge.saveturns(username,gamename,turns);
    }

    public boolean searchavailablegamestojoin(String username) {
        return bridge.searchavailablegamestojoin(username);
    }

    public boolean playturn(String username, String gamename, String action) {
        return bridge.playturn(username,gamename, action);
    }

    public int getPotSize(String gamename) {
        return bridge.getPotSize(gamename);
    }

    public int getPlayerbalance(String username, String gamename) {
        return  bridge.getPlayerbalance(username, gamename);
    }

    public boolean playturnraise(String username, String gamename, String action, int amounttoraise) {
        return bridge.playturnraise(username,gamename, action,amounttoraise);
    }

    public int getnumofround(String gamename) {
        return bridge.getnumofround(gamename);
    }

    public int getnumofplayersinround(String gamename, int numofround) {
        return  bridge.getnumofplayersinround(gamename,  numofround);
    }

    public boolean searchgamebypotsize(int pot) {
        return bridge.searchgamebypotsize( pot);
    }

    public boolean setuserleague(String adminname, String username, int newleague) {
        return bridge.setuserleague(adminname, username,newleague);
    }

    public boolean setcriteriatomoveleague(String adminname, int criteria) {
        return bridge.setcriteriatomoveleague(adminname,criteria);
    }
}
