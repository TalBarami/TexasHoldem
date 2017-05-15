package TexasHoldem.communication.converters;

import TexasHoldem.communication.entities.ClientCard;
import TexasHoldem.communication.entities.ClientPlayer;
import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.participants.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rotemwald on 15/05/17.
 */
public class PlayerClientPlayerConverter {
    public static ClientPlayer convert(Player player) {
        ClientPlayer clientPlayer = new ClientPlayer();
        Set<ClientCard> clientCards = new HashSet<>();

        clientPlayer.setPlayerName(player.getUser().getUsername());
        clientPlayer.setChipAmount(player.getChipsAmount());

        for (Card c : player.getCards()) {
            clientCards.add(CardClientCardConverter.convert(c));
        }
        clientPlayer.setPlayerCards(clientCards);

        return clientPlayer;
    }
}
