package Server.domain.game.events;

import Server.data.Hybernate.HibernateUtil;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.game.GameActions;
import Server.domain.game.participants.Player;
import Server.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by hod on 07/06/2017.
 */
public class GameEventDbTests {

    @Test
    public void testNewGameEventTest() throws Exception {
        User user = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991, 4, 20), null);
        GameEvent gameevent = new GameEvent(user.getUsername(), GameActions.CHECK, "achiad-poker-game");
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(gameevent);
            gameevent.setEventAction(GameActions.CALL);
            GameEvent gameEventFromDb = (GameEvent) session.get(GameEvent.class, gameevent.getId());
            session.delete(gameEventFromDb);
            session.getTransaction().commit();
            assertEquals(gameevent.getEventAction(), gameEventFromDb.getEventAction());
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void testNewGameEvent2Test() throws Exception {
        User user = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991, 4, 20), null);
        GameEvent gameEvent = new GameEvent(user.getUsername(), GameActions.RAISE, "achiad-poker-game");
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.save(gameEvent);
            gameEvent.setEventAction(GameActions.CHECK);
            GameEvent gameEventFromDb = (GameEvent) session.get(GameEvent.class, gameEvent.getId());
            session.delete(gameEventFromDb);
            session.delete(user);
            session.getTransaction().commit();
            assertEquals(GameActions.CHECK, gameEventFromDb.getEventAction());
            assertEquals(gameEvent.getCreatorUserName(),user.getUsername());
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}


