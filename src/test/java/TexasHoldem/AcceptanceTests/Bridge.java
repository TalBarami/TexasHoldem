package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;
import java.time.LocalDate;

import java.awt.image.BufferedImage;
import java.util.List;
import TexasHoldem.domain.game.GameSettings;
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

    boolean createnewgame(String username,String gamename, GameSettings.GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid);

    boolean closegame(String gamename);

    boolean joinexistinggame(String username, String gamename, boolean spec);

    boolean searchgamebyplayername(String username);

    boolean searchgamebytypepolicy(String type);

    boolean searchgamebybuyin(int buyin);

    boolean searchgamebychippolicy(int chippolicy);

    boolean searchgamebyminbet(int minbet);

    boolean searchgamebyminplayers(int numplayers);

    boolean searchgamebymaxplayers(int numplayers);

    boolean searchgamebyspectateisvalid(boolean spectatingavailable);
    
    boolean leavegame(String username, String choise, String gamename);

    boolean replaynonactivegame(String username, String gamename);

    boolean saveturns(String username, String gamename, List<String> turns);

    boolean searchavailablegamestojoin(String username);

    boolean playturn(String username, String gamename ,String action);

    int getPotSize(String gamename);

    int getPlayerbalance(String username, String gamename);

    boolean playturnraise(String username, String gamename, String action, int amounttoraise);

    int getnumofround(String gamename);

    int getnumofplayersinround(String gamename, int numofround);

    boolean setuserleague(String adminname, String username, int league);

    boolean searchgamebypotsize(int pot);

    boolean setcriteriatomoveleague(String adminname, int criteria);

    boolean spectateactivegame(String username, String gamename, boolean spec);
}
