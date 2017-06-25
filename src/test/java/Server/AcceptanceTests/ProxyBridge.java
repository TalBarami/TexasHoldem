package Server.AcceptanceTests;

import Server.domain.events.gameFlowEvents.GameEvent;
import Enumerations.GamePolicy;

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


    public boolean registerUser(String username, String password, String email, LocalDate dateTime, String img) {
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
    public boolean editUserName(String oldusername, String newusername, String password, String email, LocalDate date,String image) {
        return real.editUserName(oldusername,newusername, password, email, date, image);
    }


    public boolean addbalancetouserwallet(String username,int amounttoadd) {
        return real.addbalancetouserwallet(username,amounttoadd);
    }

    public boolean createnewgame(String username, String gamename, GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        return real.createnewgame(username, gamename, policy, buyin,chippolicy, minimumbet,  minplayers, maxplayers, spectateisvalid);
    }


    public boolean joinexistinggame(String username, String gamename, boolean spec) {
       return real.joinexistinggame(username, gamename,spec);
    }

    public boolean searchgamebyplayername(String username) {
        return real.searchgamebyplayername(username);
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

    public boolean searchgamebyspectateisvalid() {
        return real.searchgamebyspectateisvalid();
    }

    public boolean leavegame(String username, String choise, String gamename) {
        return  real.leavegame(username,choise, gamename);
    }

   public List<GameEvent> replaynonactivegame(String gamename) {
        return real.replaynonactivegame(gamename);
    }

    public boolean searchavailablegamestojoin(String username) {
       return real.searchavailablegamestojoin(username);
    }

    public int getPotSize(String gamename) {
        return real.getPotSize(gamename);
    }

    public int getPlayerbalance(String username, String gamename) {
        return real.getPlayerbalance(username, gamename);
    }


   /* public boolean setuserleague(String adminname, String username, int league) {
        return real.setuserleague(adminname, username, league);
    }*/

    public boolean searchgamebypotsize(int pot) {
        return real.searchgamebypotsize(pot);
    }

   /* public boolean setcriteriatomoveleague(String adminname, int criteria) {
        return real.setcriteriatomoveleague(adminname, criteria);
    } */

    @Override
    public boolean spectateactivegame(String username, String gamename, boolean spec) {
        return real.spectateactivegame(username,gamename, spec);
    }

  /*  @Override
    public boolean moveuserleague(String admin, String username, int league) {
        return real.moveuserleague(admin, username, league);
     } */

    @Override
    public boolean startgame(String userName,String gameName) {
       return real.startgame(userName,gameName);
    }

    @Override
    public boolean playcall(String username, String gamename) {
        return real.playcall(username, gamename);
    }

    @Override
    public boolean playcheck(String username, String gamename) {
        return real.playcheck(username, gamename);
    }

    @Override
    public boolean playraise(String username, String gamename, int amount) {
        return real.playraise(username, gamename, amount);
    }

    @Override
    public boolean playfold(String username, String gamename) {
        return real.playfold(username, gamename);
    }

    @Override
    public int getuserleague(String username) {
        return real.getuserleague(username);
    }

}
