package TexasHoldem.domain.game.hand;

import TexasHoldem.domain.game.Card;
import TexasHoldem.domain.game.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static TexasHoldem.domain.game.Rank.*;
import static TexasHoldem.domain.game.Suit.*;

/**
 * Created by Tal on 08/04/2017.
 */
public class HandTest {
    private List<Card> l1;
    private List<Card> l2;
    private List<Card> l3;

    @Before
    public void setUp(){
        l1 = Arrays.asList(new Card(FOUR, HEART), new Card(FIVE, HEART), new Card(SIX, HEART), new Card(SEVEN, HEART),
                new Card(EIGHT, HEART));

        l2 = Arrays.asList(new Card(ACE, HEART), new Card(ACE, CLUB), new Card(ACE, DIAMOND), new Card(ACE, SPADE),
                new Card(SIX, SPADE));

        l3 = Arrays.asList(new Card(ACE, HEART), new Card(KING, HEART), new Card(QUEEN, HEART), new Card(JACK, HEART),
                new Card(TEN, HEART));


/*
        l1 = Arrays.asList(new Card(TWO, HEART), new Card(THREE, HEART), new Card(FOUR, HEART), new Card(FIVE, HEART),
                new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART));

        l2 = Arrays.asList(new Card(ACE, HEART), new Card(ACE, CLUB), new Card(ACE, DIAMOND), new Card(ACE, SPADE),
                new Card(SIX, SPADE), new Card(SEVEN, SPADE), new Card(EIGHT, SPADE));
*/

    }

    @After
    public void tearDown(){

    }

    @Test
    public void testHand(){
        Hand h1 = new Hand(l1);
        Hand h2 = new Hand(l2);
        Hand h3 = new Hand(l3);

        System.out.println(h1);
        System.out.println(h2);
        System.out.println(h3);

        System.out.println(h1.compareTo(h3));
    }
}