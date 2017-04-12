package TexasHoldem.domain.game.card;

import TexasHoldem.domain.game.participants.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tal on 05/04/2017.
 */
public class Dealer {
    private static Logger logger = LoggerFactory.getLogger(Dealer.class);
    private Deck deck;
    public Dealer(){
        deck = new Deck();
    }

    public void deal(Collection<Player> players){
        for(Player p : players){
            List<Card> cards = deck.get(2);
            logger.info("{} received 2 cards: {}", p.getUser().getUsername(), cards);
            p.addCards(cards);
        }
    }

    public List<Card> open(int numOfCards) {
        List<Card> cards = deck.get(numOfCards);
        logger.info("Revealing {} new cards: {}", numOfCards, cards);
        return deck.get(numOfCards);
    }
}
