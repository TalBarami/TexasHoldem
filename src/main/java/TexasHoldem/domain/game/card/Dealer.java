package TexasHoldem.domain.game.card;

import TexasHoldem.domain.game.participants.Player;
import org.hibernate.annotations.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Tal on 05/04/2017.
 */

@Entity
@Table(name = "dealer")
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dealer_id")
    private int id;

    @Transient
    private static Logger logger = LoggerFactory.getLogger(Dealer.class);

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deck_id")
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE_ORPHAN} )
    private Deck deck;

    public Dealer(){
        deck = new Deck();
    }

    public void deal(Collection<Player> players){
        players.forEach(p -> {
            List<Card> cards = deck.get(2);
            logger.info("{} received 2 cards: {}", p.getUser().getUsername(), cards);
            p.addCards(cards);
        });
    }

    public List<Card> open(int numOfCards) {
        List<Card> cards = deck.get(numOfCards);
        logger.info("Revealing {} new cards: {}", numOfCards, cards);
        return cards;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public void setDeck(Deck deck)
    {
        this.deck = deck;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
