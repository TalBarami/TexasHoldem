package TexasHoldem.data.games;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameSettings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * Created by RotemWald on 05/04/2017.
 */
public class Games implements IGames {
    private HashMap<Integer, Game> _games;
    int _newGameId;

    public Games() {
        _games = new HashMap<Integer, Game>();
        _newGameId = 0;
    }

    public boolean addGame(Game game) {
        int gameId = getNewGameId();

        game.setGameId(gameId);
        _games.put(gameId, game);

        return true;
    }

    public LinkedList<Game> getActiveGamesByPlayerName(String playerName) {
        return searchActiveGame(g -> g.getPlayers().stream().anyMatch(p -> p.getUser().getUsername() == playerName));
    }

    public LinkedList<Game> getActiveGamesByPotSize(int potSize) {
        return searchActiveGame(g -> g.getRounds().get(g.getRounds().size() - 1).getPotAmount() >= potSize);
    }

    public LinkedList<Game> getActiveGamesByGamePolicy(GameSettings.GamePolicy policy) {
        return searchActiveGame(g -> g.getSettings().getGameType() == policy);
    }

    public LinkedList<Game> getActiveGamesByMinimumBetAmount(int minBetAmount) {
        return searchActiveGame(g -> g.getSettings().getMinBet() == minBetAmount);
    }

    public LinkedList<Game> getActiveGamesByMaximumBuyInAmount(int maxBuyInAmount) {
        return searchActiveGame(g -> g.getSettings().getBuyInPolicy() <= maxBuyInAmount);
    }

    public LinkedList<Game> getActiveGamesByChipPolicyAmount(int chipPolicyAmount) {
        return searchActiveGame(g -> g.getSettings().getChipPolicy() <= chipPolicyAmount);
    }

    public LinkedList<Game> getActiveGamesByActivePlayersAmount(int playersAmount) {
        return searchActiveGame(g -> g.getPlayers().size() <= playersAmount && g.getPlayers().size() < g.getSettings().getPlayerRange().getRight());
    }

    public LinkedList<Game> getActiveGamesBySpectationAllowed(boolean spectationAllowed) {
        return searchActiveGame(g -> g.getSettings().isAcceptSpectating() == spectationAllowed);
    }

    private LinkedList<Game> searchActiveGame(Predicate<Game> p) {
        LinkedList<Game> result = new LinkedList<Game>();

        for (Game g : _games.values()) {
            if (g.isActive() && p.test(g)) {
                result.add(g);
            }

        }
        return result;
    }

    private int getNewGameId() {
        return _newGameId++;
    }
}