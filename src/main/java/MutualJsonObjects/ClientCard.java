package MutualJsonObjects;

import Server.domain.game.card.Rank;
import Server.domain.game.card.Suit;
/**
 * Created by rotemwald on 15/05/17.
 */
public class ClientCard {
    private Rank rank;
    private Suit suit;

    public ClientCard() {
    }

    public ClientCard(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public String getImageName(){
        return String.format("%s_of_%s.png", rank.getRank() <= 10 ? String.valueOf(rank.getRank()) : rank.toString(), suit).toLowerCase();
    }

}
