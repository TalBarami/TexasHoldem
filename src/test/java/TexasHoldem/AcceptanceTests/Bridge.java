package TexasHoldem.AcceptanceTests;

import TexasHoldem.domain.game.GamePolicy;

import java.time.LocalDate;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by אחיעד on 05/04/2017.
 */
public interface Bridge {


    boolean registerUser(String username, String password, String email, LocalDate dateTime, BufferedImage img);

    boolean searchUser(String username);

    boolean deleteUser(String username);

    boolean login(String username, String password);

    boolean logout(String username);

    boolean editUserName(String oldusername, String newusername, String password, String email, LocalDate date) ;

    boolean addbalancetouserwallet(String username,int amounttoadd);

    boolean createnewgame(String username, String gamename, GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid);


    boolean joinexistinggame(String username, String gamename, boolean spec);

    boolean searchgamebyplayername(String username);

    boolean searchgamebytypepolicy(GamePolicy policy);

    boolean searchgamebybuyin(int buyin);

    boolean searchgamebychippolicy(int chippolicy);

    boolean searchgamebyminbet(int minbet);

    boolean searchgamebyminplayers(int numplayers);

    boolean searchgamebymaxplayers(int numplayers);

    boolean searchgamebyspectateisvalid(boolean spectatingavailable);
    
    boolean leavegame(String username, String choise, String gamename);

  /*  boolean replaynonactivegame(String username, String gamename); */

    boolean searchavailablegamestojoin(String username);

    int getPotSize(String gamename);

    int getPlayerbalance(String username, String gamename);

  /*  boolean setuserleague(String adminname, String username, int league); */

    boolean searchgamebypotsize(int pot);

  /*  boolean setcriteriatomoveleague(String adminname, int criteria); */

    boolean spectateactivegame(String username, String gamename, boolean spec);

   /* boolean moveuserleague(String admin, String username, int league); */

    void startgame(String gamename);

    boolean playcall(String username, String gamename, int amount);

    boolean playcheck(String username, String gamename, int amount);

    boolean playraise(String username, String gamename, int amount);

    boolean playfold(String username, String gamename, int amount);

    int getuserleague(String username);
}
