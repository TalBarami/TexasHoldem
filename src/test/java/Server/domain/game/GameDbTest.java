package Server.domain.game;

import Enumerations.GamePolicy;
import Server.data.Hybernate.HibernateUtil;
import Server.domain.game.participants.Player;
import Server.domain.game.participants.Spectator;
import Server.domain.user.LeagueManager;
import Server.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by אחיעד on 07/06/2017.
 */
public class GameDbTest {

    @Test
    public void BasicGameTest() throws Exception {
        LeagueManager lm = new LeagueManager();
        User user = new User("hod", "hod", "hod@gmail.com", LocalDate.of(1991,4,20),null);
        GameSettings gamesettings = new GameSettings("hod-poker-game", GamePolicy.NOLIMIT,0,10,100,10000,2,9,true);
        GameSettings gameSettingsFromDb = null;

        Game gameFromDb = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.beginTransaction();
            Game game = new Game(gamesettings, user, lm);
            session.save(gamesettings);
            session.save(game);

            gameFromDb = (Game) session.get(Game.class, game.getId());

            gamesettings.setGameType(GamePolicy.POTLIMIT);
            gamesettings.setGameTypeLimit(10);
            gamesettings.setMinBet(20);

            gameSettingsFromDb = gameFromDb.getSettings();

            session.delete(gamesettings);
            session.delete(game);
            session.delete(user);
            session.getTransaction().commit();

        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            assertEquals(0,1);
        }finally {
            session.close();
        }

        assertEquals("hod-poker-game", gameSettingsFromDb.getName());
        assertEquals(GamePolicy.POTLIMIT, gameSettingsFromDb.getGameType());
        assertEquals(10, gameSettingsFromDb.getGameTypeLimit());
        assertEquals(20, gameSettingsFromDb.getMinBet());
        assertEquals(0, gameFromDb.getDealerIndex());
        assertEquals(gamesettings.getChipPolicy(), gameFromDb.getPlayers().get(0).getChipPolicy());
        assertEquals(GameActions.ENTER, gameFromDb.getGameEvents().get(0).getEventAction());
    }

    @Test
    public void advancedGameTestWithRound() throws Exception {
        LeagueManager lm = new LeagueManager();
        User user1 = new User("hod", "hod", "hod@gmail.com", LocalDate.of(1991,4,13),null);
        User user2 = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991,4,20),null);
        User user3 = new User("rotem", "rotem", "rotem@gmail.com", LocalDate.of(1991,4,20),null);
        GameSettings gamesettings = new GameSettings("hod-poker-game", GamePolicy.NOLIMIT,0,10,100,0,2,9,true);
        Game gameFromDb = null;
        Game game = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(user1);
            session.save(user2);
            session.save(user3);
            session.getTransaction().commit();
            session.beginTransaction();
            game = new Game(gamesettings, user1, lm);
            session.save(gamesettings);
            session.save(game);

            gameFromDb = (Game) session.get(Game.class, game.getId());
            gameFromDb.getPlayers().get(0).setChipPolicy(2000);
            game.joinGameAsPlayer(user2);
            game.joinGameAsSpectator(user3);

            session.getTransaction().commit();

        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            assertEquals(0,1);
        }finally {
            session.close();
        }

        Player player1 = gameFromDb.getPlayers().get(0);
        Player player2 = gameFromDb.getPlayers().get(1);
        Spectator spectator = gameFromDb.getSpectators().get(0);
        gameFromDb.startGame(player1);
        Round round = gameFromDb.getLastRound();

        assertEquals("hod@gmail.com" ,player1.getUser().getEmail());
        assertEquals(2000 ,player1.getChipPolicy());
        assertEquals("achiad@gmail.com", player2.getUser().getEmail());
        assertEquals("rotem@gmail.com", spectator.getUser().getEmail());
        assertEquals(player1.getId(), round.getActivePlayers().get(0).getId());

        session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.delete(gamesettings);
            session.delete(game);
            session.delete(user1);
            session.delete(user2);
            session.delete(user3);
            session.getTransaction().commit();

        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            assertEquals(0,1);
        }finally {
            session.close();
        }
    }
}
