package Server.communication.converters;

import MutualJsonObjects.ClientCard;
import MutualJsonObjects.ClientPlayer;
import Server.domain.game.card.Card;
import Server.domain.game.participants.Player;

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
