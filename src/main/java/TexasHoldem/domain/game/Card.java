package TexasHoldem.domain.game;

public class Card implements Comparable<Card>{
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;

    }

    @Override
    public int compareTo(Card that) {
        if (rank.equals(that.rank))
            return suit.compareTo(that.suit);
        else
            return rank.compareTo(that.rank);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (rank != card.rank) return false;
        return suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = rank.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    public int rank() {
        return this.rank.ordinal();
    }

    public int suit() {
        return this.suit.ordinal();
    }

    public String toString() {
        return rank + " of " + suit;
    }

}

