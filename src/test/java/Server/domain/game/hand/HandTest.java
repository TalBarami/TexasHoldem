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
        Hand h1 = new Hand(TWO.of(DIAMONDS), TWO.of(HEARTS), FIVE.of(SPADES), FIVE.of(CLUBS), ACE.of(HEARTS));
        Hand h2 = new Hand(TWO.of(DIAMONDS), TWO.of(HEARTS), FIVE.of(SPADES), FIVE.of(CLUBS), ACE.of(HEARTS));
        Hand h3 = new Hand(TWO.of(DIAMONDS), TWO.of(HEARTS), FIVE.of(SPADES), FIVE.of(CLUBS), SIX.of(CLUBS));
        Hand h4 = new Hand(THREE.of(DIAMONDS), THREE.of(HEARTS), FOUR.of(SPADES), FOUR.of(CLUBS), ACE.of(HEARTS));
        assertThat(h1.compareTo(h2), is(0));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
        assertThat(h1.compareTo(h4), is(greaterThan(0)));
    }

    @Test
    public void straightFlush(){
        logger.info("Testing combinations of straight flush:");
        Hand h1 = new Hand(ACE.of(DIAMONDS), QUEEN.of(DIAMONDS), KING.of(DIAMONDS), JACK.of(DIAMONDS), TEN.of(DIAMONDS));
        Hand h2 = new Hand(TEN.of(SPADES), JACK.of(SPADES), KING.of(SPADES), QUEEN.of(SPADES), ACE.of(SPADES));
        Hand h3 = new Hand(FIVE.of(CLUBS), SIX.of(CLUBS), SEVEN.of(CLUBS), EIGHT.of(CLUBS), NINE.of(CLUBS));
        assertThat(h1.compareTo(h2), is(0));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
    }

    @Test
    public void fourOfAKind(){
        logger.info("Testing combinations of four of a kind:");
        Hand h1 = new Hand(FIVE.of(DIAMONDS), FIVE.of(HEARTS), FIVE.of(SPADES), FIVE.of(CLUBS), QUEEN.of(HEARTS));
        Hand h2 = new Hand(FIVE.of(DIAMONDS), FIVE.of(HEARTS), FIVE.of(SPADES), FIVE.of(CLUBS), FIVE.of(DIAMONDS));
        Hand h3 = new Hand(TWO.of(DIAMONDS), TWO.of(HEARTS), TWO.of(SPADES), TWO.of(CLUBS), ACE.of(CLUBS));
        Hand h4 = new Hand(FIVE.of(DIAMONDS), FIVE.of(HEARTS), FIVE.of(SPADES), FIVE.of(CLUBS), QUEEN.of(DIAMONDS));

        assertThat(h1.compareTo(h2), is(greaterThan(0)));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
        assertThat(h1.compareTo(h4), is(0));
    }

    @Test
    public void highCard(){
        logger.info("Testing combinations of high card:");
        Hand h1 = new Hand(TWO.of(DIAMONDS), ACE.of(HEARTS), FIVE.of(SPADES), SIX.of(CLUBS), TEN.of(DIAMONDS));
        Hand h2 = new Hand(TWO.of(SPADES), ACE.of(DIAMONDS), FIVE.of(CLUBS), SIX.of(CLUBS), TEN.of(HEARTS));
        Hand h3 = new Hand(TEN.of(HEARTS), FIVE.of(CLUBS), SIX.of(DIAMONDS), QUEEN.of(HEARTS), TWO.of(SPADES));
        assertThat(h1.compareTo(h2), is(0));
        assertThat(h1.compareTo(h3), is(greaterThan(0)));
    }

    @Test
    public void circularStraight(){
        logger.info("Testing combinations of circular straight:");
        Hand h1 = new Hand(ACE.of(DIAMONDS), TWO.of(DIAMONDS), THREE.of(DIAMONDS), FOUR.of(DIAMONDS), FIVE.of(DIAMONDS));
        Hand h2 = new Hand(SIX.of(DIAMONDS), TWO.of(DIAMONDS), THREE.of(DIAMONDS), FOUR.of(DIAMONDS), FIVE.of(DIAMONDS));

        assertThat(h1.compareTo(h2), is(lessThan(0)));
    }
}