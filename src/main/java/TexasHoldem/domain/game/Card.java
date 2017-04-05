package TexasHoldem.domain.game;

public class Card {
    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE
    }

    public enum Rank {
        DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);
        private int Rankpoints;

        Rank(int points) {
            this.Rankpoints = points;
        }

        public int getRankpoints() {
            return this.Rankpoints;
        }
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank rank() {
        return this.rank;
    }

    public Suit suit() {
        return this.suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }
}