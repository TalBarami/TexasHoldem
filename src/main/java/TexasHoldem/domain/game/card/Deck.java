package TexasHoldem.domain.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private List<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        prepareDeck();
    }

    public void prepareDeck(){
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                cards.add(rank.of(suit));
        shuffle();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public List<Card> get(int amount){
        int deckSize = cards.size();
        List<Card> topCards = cards.subList(deckSize - amount, deckSize);
        cards.removeAll(topCards);
        return topCards;
    }
}
