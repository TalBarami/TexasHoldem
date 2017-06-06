package TexasHoldem.domain.game.card;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.data.Hybernate.HibernateUtil;
import TexasHoldem.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static TexasHoldem.domain.game.card.Rank.FIVE;
import static TexasHoldem.domain.game.card.Rank.NINE;
import static TexasHoldem.domain.game.card.Rank.TEN;
import static TexasHoldem.domain.game.card.Suit.DIAMOND;
import static TexasHoldem.domain.game.card.Suit.HEART;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by hod on 30/05/2017.
 */
public class CardDealerDeckTestsDb {

    @Test
    public void addCardTest(){
        Card card1 = new Card(TEN, HEART);
        Card card = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(card1);
            card = (Card) session.get(Card.class, card1.getId());
            session.delete(card1);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        assertThat(card,equalTo(card1));
    }

    @Test
    public void cardsValuesTest(){
        Card card1FromDB = null;
        Card card2FromDB = null;
        Card card1 = new Card(TEN, HEART);
        Card card2 = new Card(NINE, HEART);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(card1);
            session.save(card2);
            card1FromDB = (Card) session.get(Card.class, card1.getId());
            card2FromDB = (Card) session.get(Card.class, card2.getId());
            session.delete(card1);
            session.delete(card2);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        assertThat(card1.compareTo(card1FromDB), is(0));
        assertThat(card2.compareTo(card2FromDB), is(0));
    }

    @Test
    public void deckTests(){
        Deck deck = new Deck();
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(deck);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
}
