package Server.domain.game.card;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

/**
 * Created by Tal on 12/04/2017.
 */
public class DeckTest {

    @Test
    public void get() throws Exception {
        Deck d = new Deck();
        List<Card> cards = d.get(0);
        assertThat(cards.size(), is(0));

        d = new Deck();
        cards = d.get(52);
        assertThat(cards.size(), is(52));
        for(Rank rank : Rank.values()){
            for(Suit suit : Suit.values()){
                Card card = new Card(rank, suit);
                assertThat(cards, hasItem(card));
            }
        }

        while(!cards.isEmpty()){
            Card card = cards.remove(0);
            if(cards.contains(card))
                fail("Found duplicates of card: " + card);
        }
    }

}