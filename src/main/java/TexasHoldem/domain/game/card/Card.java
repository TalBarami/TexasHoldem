package TexasHoldem.domain.game.card;


public class Card implements Comparable<Card>{
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    private Card(){

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

    public String toString() {
        return rank + " of " + suit;
    }

    public static Card fromString(String card){
        String[] arr = card.split(" ");
        return new Card(Rank.valueOf(arr[0].toUpperCase()), Suit.valueOf(arr[2].toUpperCase()));
    }
}

