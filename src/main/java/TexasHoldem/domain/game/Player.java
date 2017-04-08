package TexasHoldem.domain.game;

import TexasHoldem.domain.users.User;
import java.util.*;

/**
 * Created by Tal on 05/04/2017.
 */
public class Player {
    private User user;
    private Set<Card> cards;
    public Player(User user){
        this.user=user;
        cards = new HashSet<>();
    }

    public void addCards(Collection<Card> cards){
        this.cards.addAll(cards);
    }
}