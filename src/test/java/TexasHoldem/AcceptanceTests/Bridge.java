package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;

import TexasHoldem.domain.users.User;

import java.awt.image.BufferedImage;

/**
 * Created by אחיעד on 05/04/2017.
 */
public interface Bridge {


    boolean registerUser(String username, String password, String email, DateTime dateTime);

    boolean searchUser(String username);

    boolean deleteUser(String username);

    boolean login(String username, String password);

    boolean logout(String username);

    boolean editUserName(String oldusername, String newusername);

    boolean editPassword(String oldusername, String password);

    boolean editEmail(String oldusername, String email);

    boolean editDateOfBirth(String oldusername, DateTime dateofbirth);

    boolean editImage(String oldusername, BufferedImage newimage);

    boolean addbalancetouserwallet(String username,int amounttoadd);

    boolean createnewgame(String username,String gamename, String policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid);

    boolean closegame(String gamename);

    boolean joinexistinggame(String username, String gamename);

    boolean searchgamebyplayername(String username);

    boolean searchgamebytypepolicy(String type);

    boolean searchgamebybuyin(int buyin);
}
