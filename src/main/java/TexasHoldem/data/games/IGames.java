package TexasHoldem.data.games;

import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GamePolicy;

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
}
