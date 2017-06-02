package Server.domain.game.card;

import Server.domain.game.participants.Player;
import Server.domain.user.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Tal on 12/04/2017.
 */
public class DealerTest {
    @Test
    public void deal() throws Exception {
        Dealer dealer = new Dealer();
        List<Player> players = Arrays.asList(
                new Player(new User("user1", "", "", null, null), 0, 0),
                new Player(new User("user2", "", "", null, null), 0, 0),
                new Player(new User("user3", "", "", null, null), 0, 0));
        dealer.deal(players);
        players.forEach(p -> assertThat(p.getCards().size(), is(2)));

        dealer.deal(players);
        players.forEach(p -> assertThat(p.getCards().size(), is(4)));
    }

    @Test
    public void open() throws Exception {
        Dealer dealer = new Dealer();

        List<Card> cards = dealer.open(0);
        assertThat(cards.size(), is(0));

        dealer = new Dealer();
        cards = dealer.open(52);
        assertThat(cards.size(), is(52));
        for(Rank rank : Rank.values()){
            for(Suit suit : Suit.values()){
                Card card = new Card(rank, suit);
                assertThat(cards, hasItem(card));
            }
        }
    }

}