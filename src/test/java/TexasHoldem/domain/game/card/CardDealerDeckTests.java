package TexasHoldem.domain.game.card;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.data.Hybernate.HibernateUtil;
import TexasHoldem.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.io.Serializable;

import static TexasHoldem.domain.game.card.Rank.NINE;
import static TexasHoldem.domain.game.card.Rank.TEN;
import static TexasHoldem.domain.game.card.Suit.HEART;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Created by hod on 30/05/2017.
 */
public class CardDealerDeckTests {

    @Test
    public void addCardTest(){
        Card card1 = TEN.of(HEART);
        Card card2 = new Card(TEN, HEART);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(card2);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }

        assertThat(card1,equalTo(card2));

        Session session1 = HibernateUtil.getInstance().getSessionFactory().openSession();
        Card card = null;
        try{
            session.beginTransaction();
            card = (Card) session.get(Card.class, (Serializable) card1);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        assertThat(card,equalTo(card2));
    }

    @Test
    public void cardsValuesTest(){
        Card thiscard = TEN.of(HEART);
        Card thatcard = new Card(TEN, HEART);
        Card thatcard1 = new Card(NINE, HEART);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(thatcard);
            session.save(thatcard1);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }

        assertThat(thiscard.compareTo(thatcard), is(0));
        assertThat(thiscard.compareTo(thatcard), is(greaterThan(0)));
    }





}
