package Server.communication.converters;

import MutualJsonObjects.ClientCard;
import Server.domain.game.card.Card;

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
