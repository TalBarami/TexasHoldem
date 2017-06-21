package Server.communication.converters;

import MutualJsonObjects.ClientGameDetails;
import MutualJsonObjects.ClientPlayer;
import Server.domain.game.Game;
import Server.domain.game.participants.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 13/05/2017.
 */
public class GameClientGameDetailsConverter {
    public static ClientGameDetails convert(Game game) {
        ClientGameDetails cgd = new ClientGameDetails();
        List<ClientPlayer> clientPlayerList = new LinkedList<>();

        cgd.setName(game.getName());
        cgd.setPolicyType(game.getSettings().getGameType().getPolicy());
        cgd.setPolicyLimitAmount(game.getSettings().getGameTypeLimit());
        cgd.setMinimumBet(game.getSettings().getMinBet());
        cgd.setBuyInAmount(game.getSettings().getBuyInPolicy());
        cgd.setChipPolicyAmount(game.getSettings().getChipPolicy());
        cgd.setMinimumPlayersAmount(game.getSettings().getPlayerRange().getLeft());
        cgd.setMaximumPlayersAmount(game.getSettings().getPlayerRange().getRight());
        cgd.setSpectateValid(game.getSettings().isAcceptSpectating());

        for (Player p : game.getPlayers()) {
            clientPlayerList.add(PlayerClientPlayerConverter.convert(p));
        }
        cgd.setPlayerList(clientPlayerList);

        if (game.getLastRound() != null && game.getLastRound().isRoundActive()) {
            cgd.setRunning(true);
        } else {
            cgd.setRunning(false);
        }

        if (game.canBeArchived()) {
            cgd.setArchived(true);
        } else {
            cgd.setArchived(false);
        }

        return cgd;
    }
}
