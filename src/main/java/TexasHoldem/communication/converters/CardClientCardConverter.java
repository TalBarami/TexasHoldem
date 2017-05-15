package TexasHoldem.communication.converters;

import TexasHoldem.communication.entities.ClientCard;
import TexasHoldem.domain.game.card.Card;

/**
 * Created by rotemwald on 15/05/17.
 */
public class CardClientCardConverter {
    public static ClientCard convert(Card card) {
        ClientCard clientCard = new ClientCard();

        clientCard.setRank(card.getRank());
        clientCard.setSuit(card.getSuit());

        return clientCard;
    }
}
