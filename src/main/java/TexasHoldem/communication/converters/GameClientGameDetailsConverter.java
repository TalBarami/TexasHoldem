package TexasHoldem.communication.converters;

import TexasHoldem.communication.entities.ClientGameDetails;
import TexasHoldem.domain.game.Game;

/**
 * Created by user on 13/05/2017.
 */
public class GameClientGameDetailsConverter {
    public static ClientGameDetails convert(Game game) {
        ClientGameDetails cgd = new ClientGameDetails();

        cgd.setName(game.getName());
        cgd.setPolicyType(game.getSettings().getGameType().getPolicy());
        cgd.setPolicyLimitAmount(game.getSettings().getGameTypeLimit());
        cgd.setMinimumBet(game.getSettings().getMinBet());
        cgd.setBuyInAmount(game.getSettings().getBuyInPolicy());
        cgd.setChipPolicyAmount(game.getSettings().getChipPolicy());
        cgd.setMinimumPlayersAmount(game.getSettings().getPlayerRange().getLeft());
        cgd.setMaximumPlayersAmount(game.getSettings().getPlayerRange().getRight());
        cgd.setSpectateValid(game.getSettings().isAcceptSpectating());

        return cgd;
    }
}
