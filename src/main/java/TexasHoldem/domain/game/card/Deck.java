package TexasHoldem.domain.game.card;

import org.hibernate.annotations.CascadeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "Deck")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deckId")
    private int id;

    @Transient
    private static Logger logger = LoggerFactory.getLogger(Deck.class);

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumns ({ @JoinColumn( name="rank" ),@JoinColumn( name="suit" )})
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

    public List<Card> getCards()
    {
        return cards;
    }

    public void setCards(List<Card> cards)
    {
        this.cards = cards;
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
