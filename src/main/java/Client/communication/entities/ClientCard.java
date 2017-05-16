package Client.communication.entities;

import TexasHoldem.domain.game.card.Rank;
import TexasHoldem.domain.game.card.Suit;

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
}
