package Server.domain.game.hand;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static Server.domain.game.card.Rank.*;
import static Server.domain.game.card.Suit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Tal on 11/04/2017.
 */
public class HandCalculatorTest {
    private static Logger logger = LoggerFactory.getLogger(HandCalculatorTest.class);

    @Test
    public void getHand(){
        Hand actualHand = HandCalculator.getHand(TEN.of(HEARTS), TEN.of(SPADES), TEN.of(DIAMONDS), TEN.of(CLUBS), TWO.of(DIAMONDS), FIVE.of(CLUBS), ACE.of(HEARTS));
        Hand expectedHand = new Hand(TEN.of(HEARTS), TEN.of(SPADES), TEN.of(DIAMONDS), TEN.of(CLUBS), ACE.of(HEARTS));
        assertThat(actualHand, is(expectedHand));
    }

    @Test
    public void fourOfAKind_vs_twoPair(){
        Hand A = HandCalculator.getHand(KING.of(DIAMONDS), THREE.of(SPADES), THREE.of(CLUBS), NINE.of(HEARTS), NINE.of(CLUBS), NINE.of(SPADES), NINE.of(DIAMONDS));
        Hand B = HandCalculator.getHand(KING.of(DIAMONDS), THREE.of(SPADES), THREE.of(CLUBS), NINE.of(HEARTS), NINE.of(CLUBS), TEN.of(CLUBS), SIX.of(CLUBS));

        Hand expectedA = new Hand(NINE.of(HEARTS), NINE.of(CLUBS), NINE.of(SPADES), NINE.of(DIAMONDS), KING.of(DIAMONDS));
        Hand expectedB = new Hand(KING.of(DIAMONDS), THREE.of(SPADES), THREE.of(CLUBS), NINE.of(HEARTS), NINE.of(CLUBS));

        assertThat(A, is(expectedA));
        assertThat(B, is(expectedB));

        assertThat(A.compareTo(B), is(greaterThan(0)));
    }
}