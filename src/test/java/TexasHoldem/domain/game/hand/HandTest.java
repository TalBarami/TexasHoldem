package TexasHoldem.domain.game.hand;

import TexasHoldem.domain.game.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static TexasHoldem.domain.game.Rank.*;
import static TexasHoldem.domain.game.Suit.*;

/**
 * Created by Tal on 08/04/2017.
 */
public class HandTest {
    private Hand hand;

    @Before
    public void setUp(){
        List<Card> l1 = Arrays.asList(new Card(TWO, HEART), new Card(THREE, HEART), new Card(FOUR, HEART), new Card(FIVE, HEART),
                new Card(SIX, HEART), new Card(SEVEN, HEART), new Card(EIGHT, HEART));
        Hand hand = new Hand(l1);
    }

    @After
    public void tearDown(){

    }

    @Test
    public void testHand(){
        System.out.println(hand);
    }
}