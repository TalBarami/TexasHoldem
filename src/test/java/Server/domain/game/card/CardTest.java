package Server.domain.game.card;

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
public class CardTest {
    private static Logger logger = LoggerFactory.getLogger(CardTest.class);

    @Test
    public void equality() throws Exception{
        Card card1 = TEN.of(HEATS);
        Card card2 = new Card(TEN, HEATS);
        assertThat(card1, equalTo(card2));
    }

    @Test
    public void compareRank() throws Exception {
        Card thisCard = TEN.of(HEATS);
        Card thatCard = NINE.of(HEATS);
        logger.info("Testing {} against {} (Expected: {})", thisCard, thatCard, thisCard);
        assertThat(thisCard.compareTo(thatCard), is(greaterThan(0)));

        thisCard = TEN.of(HEATS);
        thatCard = TEN.of(CLUBS);
        logger.info("Testing {} against {} (Expected: Tie)", thisCard, thatCard);
        assertThat(thisCard.compareTo(thatCard), is(0));

        thisCard = ACE.of(CLUBS);
        thatCard = ACE.of(CLUBS);
        logger.info("Testing {} against {} (Expected: Tie)", thisCard, thatCard);
        assertThat(thisCard.compareTo(thatCard), is(0));
    }

    @Test
    public void fromString() throws Exception{
        Card card = Card.fromString("five of diamond");
        assertThat(card, is(FIVE.of(DIAMONDS)));
    }

    @Test
    public void equals() throws Exception {
    }

}