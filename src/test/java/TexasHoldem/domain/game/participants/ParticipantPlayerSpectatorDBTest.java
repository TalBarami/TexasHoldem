package TexasHoldem.domain.game.participants;

import TexasHoldem.data.Hybernate.HibernateUtil;
import TexasHoldem.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by אחיעד on 30/05/2017.
 */
public class ParticipantPlayerSpectatorDBTest {

    @Test
    public void testNewParticipantAsPlayerTest() throws Exception {
        User user = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991,4,20),null);
        Player p = new Player(user, 1000, 0);

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.save(p);
            p.setChipPolicy(100);
            p.setChipsAmount(200);
            Player playerFromDb = (Player) session.get(Player.class, p.getId());

            assertEquals(p.getChipPolicy(), playerFromDb.getChipPolicy());
            assertEquals(p.getChipsAmount(), playerFromDb.getChipsAmount());
            assertEquals(p.getUser().getUsername(), playerFromDb.getUser().getUsername());

            session.delete(p);
            User userFromDb = (User) session.get(User.class, user.getUsername());
            assertEquals(userFromDb.getUsername(), user.getUsername());
            session.delete(user);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    @Test
    public void testNewParticipantAsSpecatatorTest() throws Exception {
        User user = new User("achiad", "1234", "achiad@gmail.com", LocalDate.of(1991,4,20),null);
        Spectator spec = new Spectator(user);
        Spectator specFromDb = null;

        Session session1 = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session1.beginTransaction();
            session1.save(user);
            session1.save(spec);

            specFromDb = (Spectator) session1.get(Spectator.class, spec.getId());

            session1.delete(user);
            session1.delete(spec);

            session1.getTransaction().commit();
        }catch (HibernateException e) {
            if (session1.getTransaction()!=null) {
                session1.getTransaction().rollback();
            }
        }finally {
            session1.close();
        }
        assertEquals(spec.getUser().getUsername(), specFromDb.getUser().getUsername());
        assertEquals(spec.getUser().getPassword(), specFromDb.getUser().getPassword());
        assertEquals(spec.getUser().getEmail(), specFromDb.getUser().getEmail());
    }

}