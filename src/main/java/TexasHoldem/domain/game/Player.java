package TexasHoldem.domain.game;

import java.util.*;

/**
 * Created by Tal on 05/04/2017.
 */
public class Player {
    private Set<Card> cards;

    public Player(){
        cards = new HashSet<>();
    }

    public void addCards(Collection<Card> cards){
        this.cards.addAll(cards);
    }
}
