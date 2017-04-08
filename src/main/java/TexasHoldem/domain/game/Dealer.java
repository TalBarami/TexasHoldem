package TexasHoldem.domain.game;

import java.util.Collection;

/**
 * Created by Tal on 05/04/2017.
 */
public class Dealer {
    private Deck deck;
    public Dealer(){
        deck = new Deck();
    }

    public void dealCards(Collection<Player> players){
        players.forEach(p -> p.addCards(deck.get(2)));
    }


}
