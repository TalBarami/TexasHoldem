package Server.AcceptanceTests;

import Server.data.Hybernate.HibernateUtil;
import Server.domain.events.SystemEvent;
import Server.domain.events.gameFlowEvents.GameEvent;
import Enumerations.GamePolicy;
import Server.domain.events.gameFlowEvents.MoveEvent;
import Server.domain.user.User;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
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

    public boolean registerUser(String username, String password, String email, LocalDate dateofbirth, String img) {
        return bridge.registerUser(username, password, email, dateofbirth, img);
    }

    public boolean login(String username, String password) {
        return bridge.login(username,password);
    }

    public boolean logout(String username) {
        return bridge.logout(username);
    }

    public boolean editusername(String oldusername, String newusername, String password, String email, LocalDate date, String image) {
        return bridge.editUserName(oldusername, newusername,password,email,date,image);
    }

    public boolean addbalancetouserwallet(String username, int amounttoadd) {
        return bridge.addbalancetouserwallet(username, amounttoadd);
    }

    public List<GameEvent> replaynonactivegame(String gamename) {
        return bridge.replaynonactivegame(gamename);
    }

    public boolean createnewgame(String username, String gamename, GamePolicy policy, int buyin, int chippolicy, int minimumbet, int minplayers, int maxplayers, boolean spectateisvalid) {
        return bridge.createnewgame(username,gamename, policy, buyin, chippolicy, minimumbet, minplayers, maxplayers, spectateisvalid);
    }

    public boolean joinexistinggame(String username, String gamename, boolean spec) {
        return bridge.joinexistinggame(username, gamename, spec);
    }

    public boolean searchgamebyplayername(String username) {
        return bridge.searchgamebyplayername(username);
    }

    public boolean searchgamebytypepolicy(GamePolicy policy) {
        return bridge.searchgamebytypepolicy(policy);
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

    public boolean searchgamebyspectateisvalid() {
        return bridge.searchgamebyspectateisvalid();
    }

    public boolean leavegame(String username, String choise, String gamename) {
        return bridge.leavegame(username, choise, gamename);
    }

    public boolean searchavailablegamestojoin(String username) {
        return bridge.searchavailablegamestojoin(username);
    }

    public int getpotsize(String gamename) {
        return bridge.getPotSize(gamename);
    }

    public int getplayerbalance(String username, String gamename) {
        return  bridge.getPlayerbalance(username, gamename);
    }

    public boolean spectateactivegame(String username, String gamename, boolean spec) {
        return bridge.spectateactivegame(username, gamename, spec);
    }

    public boolean startgame(String userName,String gameName) {
        return bridge.startgame(userName,gameName);
    }

    public boolean playcall(String username, String gamename) {
        return bridge.playcall(username, gamename);
    }

    public boolean playcheck(String username, String gamename) {
        return bridge.playcheck( username, gamename);
    }

    public boolean playraise(String username, String gamename, int amount) {
        return bridge.playraise(username, gamename, amount);
    }

    public boolean playfold(String username, String gamename) {
        return bridge.playfold(username, gamename);
    }

    public int getuserleague(String username) {
        return bridge.getuserleague(username);
    }

    public void clearAllEventsFromDB(){
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<?> instances = session.createCriteria(MoveEvent.class).list();
        for (Object obj : instances) {
            session.delete(obj);
        }
        instances = session.createCriteria(GameEvent.class).list();
        for (Object obj : instances) {
            session.delete(obj);
        }
        instances = session.createCriteria(SystemEvent.class).list();
        for (Object obj : instances) {
            session.delete(obj);
        }

        session.getTransaction().commit();
    }

    public void clearAllUsersFromDB(){
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<?> instances = session.createCriteria(User.class).list();
        for (Object obj : instances) {
            session.delete(obj);
        }

        session.getTransaction().commit();
    }

    /* DELETED FROM PREVIOUS ASSIGNMENTS
     public boolean setuserleague(String adminname, String username, int newleague) {
        return bridge.setuserleague(adminname, username,newleague);
    }

    public boolean setcriteriatomoveleague(String adminname, int criteria) {
        return bridge.setcriteriatomoveleague(adminname,criteria);
    }

    public boolean moveuserleague(String admin, String username, int league) {
        return bridge.moveuserleague(admin, username,league);
    }
     */
}
