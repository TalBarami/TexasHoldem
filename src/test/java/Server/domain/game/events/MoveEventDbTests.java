package Server.domain.game.events;

import Server.data.Hybernate.HibernateUtil;
import Server.domain.events.gameFlowEvents.MoveEvent;
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
public class MoveEventDbTests {

    @Test
    public void testNewMoveEventTest() throws Exception {
        User user = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991, 4, 20), null);
        MoveEvent moveEvent = new MoveEvent(user.getUsername(), GameActions.RAISE,30,"achiad-poker-game");
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.save(moveEvent);
            moveEvent.setAmountToRaise(60);
            MoveEvent moveEventFromDb = (MoveEvent) session.get(MoveEvent.class, moveEvent.getId());
            session.delete(moveEvent);
            session.delete(user);
            session.getTransaction().commit();
            assertEquals(moveEventFromDb.getCreatorUserName(),user.getUsername());
            assertEquals(moveEventFromDb.getAmountToRaise(),moveEvent.getAmountToRaise());
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
