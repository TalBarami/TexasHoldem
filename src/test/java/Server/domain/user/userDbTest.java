package Server.domain.user;

import Enumerations.GamePolicy;
import Server.data.Hybernate.HibernateUtil;
import Server.domain.game.Game;
import Server.domain.game.GameSettings;
import Server.domain.game.participants.Participant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by hod on 07/06/2017.
 */
public class userDbTest {

    @Test
    public void userGameMappingTest() throws Exception {
        LeagueManager lm = new LeagueManager();
        User user = new User("hod", "hod", "hod@gmail.com", LocalDate.of(1991,4,20),null);
        GameSettings gamesettings = new GameSettings("hod-poker-game", GamePolicy.NOLIMIT,0,10,100,10000,2,9,true);
        Game game = new Game(gamesettings, user, lm);
        Game gameFromDb = null;
        User userFromDb = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.save(gamesettings);
            session.save(game);

            gameFromDb = (Game) session.get(Game.class, game.getId());
            user.addGameParticipant(gameFromDb, gameFromDb.getPlayers().get(0));
            userFromDb = (User) session.get(User.class, user.getUsername());
            user.setEmail("bub@gmail.com");

            session.delete(game);
            session.delete(user);
            session.getTransaction().commit();

        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            assertEquals(0,1);
        }finally {
            session.close();
        }

        Participant part = userFromDb.getGameMapping().get(gameFromDb);
        assertEquals("hod", part.getUser().getUsername());
        assertEquals("bub@gmail.com", part.getUser().getEmail());
    }
}
