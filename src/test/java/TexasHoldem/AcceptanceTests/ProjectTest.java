package TexasHoldem.AcceptanceTests;

import junit.framework.TestCase;
import org.joda.time.DateTime;

import java.awt.image.BufferedImage;

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

    public boolean createnewgame(String username,String gamename, String policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        return bridge.createnewgame(username,gamename, policy, buyin, chippolicy, minimumbet, minplayers, maxplayers, spectateisvalid);
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
}
