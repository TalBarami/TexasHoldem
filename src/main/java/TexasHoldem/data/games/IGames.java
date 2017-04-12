package TexasHoldem.data.games;

import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameSettings;

import java.util.List;

/**
 * Created by RotemWald on 05/04/2017.
 */
public interface IGames {
    boolean addGame(Game game) throws InvalidArgumentException;
    List<String> getAllGameNames();
    List<Game> getActiveGamesByName(String name);
    List<Game> getActiveGamesByPlayerName(String playerName);
    List<Game> getActiveGamesByPotSize(int potSize);
    List<Game> getActiveGamesByGamePolicy(GameSettings.GamePolicy policy);
    List<Game> getActiveGamesByMinimumBetAmount(int minBetAmount);
    List<Game> getActiveGamesByMaximumBuyInAmount(int maxBuyInAmount);
    List<Game> getActiveGamesByChipPolicyAmount(int chipPolicyAmount);
    List<Game> getActiveGamesByActivePlayersAmount(int playersAmount);
    List<Game> getActiveGamesBySpectationAllowed(boolean spectationAllowed);
}
