package TexasHoldem.domain.game.hand;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static TexasHoldem.domain.game.card.Rank.*;
import static TexasHoldem.domain.game.card.Suit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Tal on 11/04/2017.
 */
public class HandCalculatorTest {
    private static Logger logger = LoggerFactory.getLogger(HandCalculatorTest.class);

    @Test
    public void getHand(){
        Hand actualHand = HandCalculator.getHand(TEN.of(HEART), TEN.of(SPADE), TEN.of(DIAMOND), TEN.of(CLUB), TWO.of(DIAMOND), FIVE.of(CLUB), ACE.of(HEART));
        Hand expectedHand = new Hand(TEN.of(HEART), TEN.of(SPADE), TEN.of(DIAMOND), TEN.of(CLUB), ACE.of(HEART));
        assertThat(actualHand, is(expectedHand));
    }
}