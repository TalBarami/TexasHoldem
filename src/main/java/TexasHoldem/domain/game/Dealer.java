package TexasHoldem.domain.game;

import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.card.Deck;

import TexasHoldem.domain.game.participants.Player;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tal on 05/04/2017.
 */
public class Dealer {
    private Deck deck;
    public Dealer(){
        deck = new Deck();
    }

    public void dealCardsToPlayers(Collection<Player> players){
        players.forEach(p -> p.addCards(deck.get(2)));
    }

    public List<Card> openCards(int numOfCards) {
        return deck.get(3);
    }
}
