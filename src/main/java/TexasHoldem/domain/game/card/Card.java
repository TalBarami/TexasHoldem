package TexasHoldem.domain.game.card;


import javax.persistence.*;


@Entity
@Table(name = "card")
public class Card implements Comparable<Card>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rank")
    private Rank rank;

    @Enumerated(EnumType.STRING)
    @Column(name = "suit")
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(){

    }

    @Override
    public int compareTo(Card that) {
        return this.rank.compareTo(that.rank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = rank.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    public int rank() {
        return this.rank.getRank();
    }

    public int suit() {
        return this.suit.ordinal();
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setRank(Rank rank)
    {
        this.rank = rank;
    }

    public void setSuit(Suit suit)
    {
        this.suit = suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public static Card fromString(String card){
        String[] arr = card.split(" ");
        return new Card(Rank.valueOf(arr[0].toUpperCase()), Suit.valueOf(arr[2].toUpperCase()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

