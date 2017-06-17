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
        Hand actualHand = HandCalculator.getHand(TEN.of(HEATS), TEN.of(SPADES), TEN.of(DIAMONDS), TEN.of(CLUBS), TWO.of(DIAMONDS), FIVE.of(CLUBS), ACE.of(HEATS));
        Hand expectedHand = new Hand(TEN.of(HEATS), TEN.of(SPADES), TEN.of(DIAMONDS), TEN.of(CLUBS), ACE.of(HEATS));
        assertThat(actualHand, is(expectedHand));
    }
}