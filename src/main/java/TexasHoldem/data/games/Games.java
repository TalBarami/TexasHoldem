package TexasHoldem.data.games;

import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by RotemWald on 05/04/2017.
 */
public class Games implements IGames {
    private HashMap<Integer, Game> _games;
    private List<Game> archivedGames;
    int _newGameId;

    public Games() {
        _games = new HashMap<Integer, Game>();
        archivedGames=new ArrayList<>();
        _newGameId = 0;
    }

    public boolean addGame(Game game) throws InvalidArgumentException {
        if(getAllGameNames().contains(game.getName()))
            throw new InvalidArgumentException("Game name already exists, please choose another one.");

        int gameId = getNewGameId();

        game.setGameId(gameId);
        _games.put(gameId, game);

        return true;
    }

    public void archiveGame(Game game){
        archivedGames.add(game);
        _games.remove(game.getId());
    }

    public LinkedList<Game> getActiveGamesByPlayerName(String playerName) {
        return searchActiveGame(g -> g.getPlayers().stream().anyMatch(p -> p.getUser().getUsername().equals(playerName)));
    }

    public List<String> getAllGameNames(){
        return _games.values().stream().map(Game::getName).collect(Collectors.toList());
    }

    public LinkedList<Game> getActiveGamesByName(String name){
        return searchActiveGame(g -> g.getName().equals(name));
    }

    public LinkedList<Game> getActiveGamesByPotSize(int potSize) {
        return searchActiveGame(g -> g.getLastRound().getPotAmount() >= potSize);
    }

    public LinkedList<Game> getActiveGamesByGamePolicy(GameSettings.GamePolicy policy) {
        return searchActiveGame(g -> g.getSettings().getGameType() == policy);
    }

    public LinkedList<Game> getActiveGamesByMinimumBetAmount(int minBetAmount) {
        return searchActiveGame(g -> g.getSettings().getMinBet() == minBetAmount);
    }

    public LinkedList<Game> getActiveGamesByMaximumBuyInAmount(int maxBuyInAmount) {
        return searchActiveGame(g -> g.getBuyInPolicy() <= maxBuyInAmount);
    }

    public LinkedList<Game> getActiveGamesByChipPolicyAmount(int chipPolicyAmount) {
        return searchActiveGame(g -> g.getSettings().getChipPolicy() <= chipPolicyAmount);
    }

    public LinkedList<Game> getActiveGamesByActivePlayersAmount(int playersAmount) {
        return searchActiveGame(g -> g.getPlayers().size() <= playersAmount && g.getPlayers().size() < g.getSettings().getPlayerRange().getRight());
    }

    public LinkedList<Game> getActiveGamesBySpectationAllowed(boolean spectationAllowed) {
        return searchActiveGame(g -> g.canBeSpectated() == spectationAllowed);
    }

    public List<Game> getActiveGames(){
        return searchActiveGame(g -> true);
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