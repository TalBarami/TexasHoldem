package Server.domain.game.events;

import Server.data.Hybernate.HibernateUtil;
import Server.domain.events.SystemEvent;
import Server.domain.game.participants.Player;
import Server.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by אחיעד on 07/06/2017.
 */
public class SystemEventDbTests {

    @Test
    public void testNewSystemEventTest() throws Exception {
        User user = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991, 4, 20), null);
        SystemEvent systemEvent = new SystemEvent(user.getUsername(),"Achiad-poker-game");
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.save(systemEvent);
            systemEvent.setCreatorUserName(user.getUsername());
            SystemEvent systemEventFromDb = (SystemEvent) session.get(SystemEvent.class, systemEvent.getId());
            session.delete(systemEvent);
            session.delete(user);
            session.getTransaction().commit();
            assertEquals(systemEventFromDb.getCreatorUserName(),user.getUsername());
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
