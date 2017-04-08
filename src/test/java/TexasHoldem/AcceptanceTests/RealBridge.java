package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;

import TexasHoldem.domain.users.User;

import java.awt.image.BufferedImage;

/**
 * Created by אחיעד on 05/04/2017.
 */
public class RealBridge implements Bridge {

    public RealBridge(){

    }


    public boolean registerUser(String username, String password, String email, DateTime dateTime)
    {
        return true;
    }

    public boolean searchUser(String username)
    {
        return true;
    }

    public  boolean deleteUser(String username)
    {
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

    public boolean addbalancetouserwallet(String username, int amounttoadd) {
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
}
