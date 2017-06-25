package Server.AcceptanceTests;

import Server.domain.events.gameFlowEvents.GameEvent;
import Enumerations.GamePolicy;

import java.time.LocalDate;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by אחיעד on 05/04/2017.
 */
public interface Bridge {


    boolean registerUser(String username, String password, String email, LocalDate dateTime, String img);

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

    boolean searchgamebyspectateisvalid();
    
    boolean leavegame(String username, String choise, String gamename);

    List<GameEvent> replaynonactivegame(String gamename);

    boolean searchavailablegamestojoin(String username);

    int getPotSize(String gamename);

    int getPlayerbalance(String username, String gamename);

  /*  boolean setuserleague(String adminname, String username, int league); */

    boolean searchgamebypotsize(int pot);

  /*  boolean setcriteriatomoveleague(String adminname, int criteria); */

    boolean spectateactivegame(String username, String gamename, boolean spec);

   /* boolean moveuserleague(String admin, String username, int league); */

    boolean startgame(String userName,String gameName);

    boolean playcall(String username, String gamename);

    boolean playcheck(String username, String gamename);

    boolean playraise(String username, String gamename, int amount);

    boolean playfold(String username, String gamename);

    int getuserleague(String username);
}
