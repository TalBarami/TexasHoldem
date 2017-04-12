package TexasHoldem.domain.game.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private static Logger logger = LoggerFactory.getLogger(Deck.class);
    private List<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        prepare();
    }

    private void prepare(){
        logger.info("Preparing new deck.");
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                cards.add(rank.of(suit));
        shuffle();
    }

    private void shuffle(){
        Collections.shuffle(cards);
    }

    public List<Card> get(int amount){
        int deckSize = cards.size();
        List<Card> topCards = new ArrayList<>(cards.subList(deckSize - amount, deckSize));
        cards.removeAll(topCards);
        logger.info("Taking {} cards from the top of the deck: {}", amount, topCards);
        return topCards;
    }
}
