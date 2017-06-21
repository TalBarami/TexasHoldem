package Server.data.games;

import Exceptions.InvalidArgumentException;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.events.gameFlowEvents.MoveEvent;
import Server.domain.game.Game;
import Enumerations.GamePolicy;

import java.util.List;

/**
 * Created by RotemWald on 05/04/2017.
 */
public interface IGames {
    boolean addGame(Game game) throws InvalidArgumentException;
    void archiveGame(Game game);
    boolean isArchived(Game g);
    List<String> getAllGameNames();
    List<Game> getActiveGames();
    List<Game> getActiveGamesByName(String name);
    List<Game> getActiveGamesByPlayerName(String playerName);
    List<Game> getActiveGamesByPotSize(int potSize);
    List<Game> getActiveGamesByGamePolicy(GamePolicy policy);
    List<Game> getActiveGamesByMinimumBetAmount(int minBetAmount);
    List<Game> getActiveGamesByMaximumBuyInAmount(int maxBuyInAmount);
    List<Game> getActiveGamesByChipPolicyAmount(int chipPolicyAmount);
    List<Game> getActiveGamesByMinimumPlayersAmount(int minimumPlayersAmount);
    List<Game> getActiveGamesByMaximumPlayersAmount(int maximumPlayersAmount);
    List<Game> getActiveGamesBySpectationAllowed(boolean spectationAllowed);
    List<String> getArchivedGames();
    List<GameEvent> getAllGameEvents(String gameName);
//    List<MoveEvent> getAllMoveEvents(String gameName);
}
