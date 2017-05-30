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
        Card card1 = TEN.of(HEART);
        Card card2 = new Card(TEN, HEART);
        Card card = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(card2);
            card = (Card) session.get(Card.class, (Serializable) card1);
            session.delete(card2);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        assertThat(card,equalTo(card2));
        assertThat(card1,equalTo(card2));
    }

    @Test
    public void cardsValuesTest(){
        Card card = null;
        Card card1 = null;
        Card thiscard = TEN.of(HEART);
        Card thatcard = new Card(TEN, HEART);
        Card thiscard1 = NINE.of(HEART);
        Card thatcard1 = new Card(NINE, HEART);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(thatcard);
            session.save(thatcard1);
            card = (Card) session.get(Card.class, (Serializable) thiscard);
            card1 = (Card) session.get(Card.class, (Serializable) thiscard);
            session.delete(thatcard);
            session.delete(thatcard1);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        assertThat(thiscard.compareTo(card), is(0));
        assertThat(thiscard1.compareTo(card1), is(greaterThan(0)));
    }

    @Test
    public void cardsFromStringTests(){
        Card card1 = null;
        Card thiscard = FIVE.of(DIAMOND);
        Card card = Card.fromString("five of diamond");
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(card);
            card1 = (Card) session.get(Card.class, (Serializable) thiscard);
            session.delete(card);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        assertThat(card1, is(FIVE.of(DIAMOND)));
    }

    @Test
    public void deckTests(){
        List<Card> cards= new ArrayList<Card>();
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            for(Rank rank : Rank.values()){
                for(Suit suit : Suit.values()){
                    Card card = new Card(rank, suit);
                    session.save(card);
                }
            }
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }

        Session session1 = HibernateUtil.getInstance().getSessionFactory().openSession();
        Card card1 = null;
        try{
            session1.beginTransaction();
            for(Rank rank : Rank.values()){
                for(Suit suit : Suit.values()){
                    Card card = new Card(rank, suit);
                    card1 = (Card) session.get(Card.class, (Serializable) card);
                    cards.add(card1);
                }
            }
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }

        int i = 0;
        for(Rank rank : Rank.values()){
            for(Suit suit : Suit.values()){
                Card card = new Card(rank, suit);
                assertThat(card,equalTo(cards.indexOf(i)));
            }
        }
    }
}
