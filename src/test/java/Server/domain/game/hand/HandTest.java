package Server.domain.game.hand;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static Server.domain.game.card.Rank.*;
import static Server.domain.game.card.Suit.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HandTest {
    private static Logger logger = LoggerFactory.getLogger(HandTest.class);

    @Test
    public void twoPair(){
        logger.info("Testing combinations of two pairs:");
        Hand h1 = new Hand(TWO.of(DIAMOND), TWO.of(HEART), FIVE.of(SPADE), FIVE.of(CLUB), ACE.of(HEART));
        Hand h2 = new Hand(TWO.of(DIAMOND), TWO.of(HEART), FIVE.of(SPADE), FIVE.of(CLUB), ACE.of(HEART));
        Hand h3 = new Hand(TWO.of(DIAMOND), TWO.of(HEART), FIVE.of(SPADE), FIVE.of(CLUB), SIX.of(CLUB));
        Hand h4 = new Hand(THREE.of(DIAMOND), THREE.of(HEART), FOUR.of(SPADE), FOUR.of(CLUB), ACE.of(HEART));
        assertThat(h1.compareTo(h2), is(0));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
        assertThat(h1.compareTo(h4), is(greaterThan(0)));
    }

    @Test
    public void straightFlush(){
        logger.info("Testing combinations of straight flush:");
        Hand h1 = new Hand(ACE.of(DIAMOND), QUEEN.of(DIAMOND), KING.of(DIAMOND), JACK.of(DIAMOND), TEN.of(DIAMOND));
        Hand h2 = new Hand(TEN.of(SPADE), JACK.of(SPADE), KING.of(SPADE), QUEEN.of(SPADE), ACE.of(SPADE));
        Hand h3 = new Hand(FIVE.of(CLUB), SIX.of(CLUB), SEVEN.of(CLUB), EIGHT.of(CLUB), NINE.of(CLUB));
        assertThat(h1.compareTo(h2), is(0));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
    }

    @Test
    public void fourOfAKind(){
        logger.info("Testing combinations of four of a kind:");
        Hand h1 = new Hand(FIVE.of(DIAMOND), FIVE.of(HEART), FIVE.of(SPADE), FIVE.of(CLUB), QUEEN.of(HEART));
        Hand h2 = new Hand(FIVE.of(DIAMOND), FIVE.of(HEART), FIVE.of(SPADE), FIVE.of(CLUB), FIVE.of(DIAMOND));
        Hand h3 = new Hand(TWO.of(DIAMOND), TWO.of(HEART), TWO.of(SPADE), TWO.of(CLUB), ACE.of(CLUB));
        Hand h4 = new Hand(FIVE.of(DIAMOND), FIVE.of(HEART), FIVE.of(SPADE), FIVE.of(CLUB), QUEEN.of(DIAMOND));

        assertThat(h1.compareTo(h2), is(greaterThan(0)));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
        assertThat(h1.compareTo(h4), is(0));
    }

    @Test
    public void highCard(){
        logger.info("Testing combinations of high card:");
        Hand h1 = new Hand(TWO.of(DIAMOND), ACE.of(HEART), FIVE.of(SPADE), SIX.of(CLUB), TEN.of(DIAMOND));
        Hand h2 = new Hand(TWO.of(SPADE), ACE.of(DIAMOND), FIVE.of(CLUB), SIX.of(CLUB), TEN.of(HEART));
        Hand h3 = new Hand(TEN.of(HEART), FIVE.of(CLUB), SIX.of(DIAMOND), QUEEN.of(HEART), TWO.of(SPADE));
        assertThat(h1.compareTo(h2), is(0));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
    }

    @Test
    public void circularStraight(){
        logger.info("Testing combinations of circular straight:");
        Hand h1 = new Hand(ACE.of(DIAMOND), TWO.of(DIAMOND), THREE.of(DIAMOND), FOUR.of(DIAMOND), FIVE.of(DIAMOND));
        Hand h2 = new Hand(SIX.of(DIAMOND), TWO.of(DIAMOND), THREE.of(DIAMOND), FOUR.of(DIAMOND), FIVE.of(DIAMOND));

        assertThat(h1.compareTo(h2), is(lessThan(0)));
    }
}