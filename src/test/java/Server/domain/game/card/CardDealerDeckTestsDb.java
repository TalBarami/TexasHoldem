package Server.domain.game.card;
import Exceptions.InvalidArgumentException;
import Server.data.Hybernate.HibernateUtil;
import Server.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import static Server.domain.game.card.Rank.FIVE;
import static Server.domain.game.card.Rank.NINE;
import static Server.domain.game.card.Rank.TEN;
import static Server.domain.game.card.Suit.DIAMOND;
import static Server.domain.game.card.Suit.HEART;
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
        Deck deckFromDB = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(deck);
            deckFromDB = (Deck) session.get(Deck.class, deck.getId());
            session.delete(deckFromDB);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        Assert.assertEquals(deck.getId(), deckFromDB.getId());
    }

    @Test
    public void dealerTests(){
        Dealer dealer = new Dealer();
        Dealer dealerFromDB = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(dealer);
            dealerFromDB = (Dealer) session.get(Dealer.class, dealer.getId());
            session.delete(dealer);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        Assert.assertEquals(dealer.getId(), dealerFromDB.getId());
    }
}
