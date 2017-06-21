package Server.service;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Server.domain.game.Game;
import Enumerations.GamePolicy;
import Server.domain.system.GameCenter;

import java.util.List;
import java.util.stream.Collectors;

import static Server.service.TexasHoldemService.verifyObjects;
import static Server.service.TexasHoldemService.verifyStrings;

/**
 * Created by Tal on 05/05/2017.
 */
public class SearchService {
    private GameCenter gameCenter;

    public SearchService(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public List<Game> findAvailableGames(String username) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username);
        return gameCenter.findAvailableGames(username);
    }

    public Game findGameByName(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        verifyStrings(gameName);
        return gameCenter.findGameByName(gameName);
    }

    public List<Game> findSpectatableGames(){
        return gameCenter.findSpectateableGames();
    }

    public List<Game> findGamesByUsername(String username) throws InvalidArgumentException {
        verifyStrings(username);
        return gameCenter.findGamesByUsername(username);
    }

    public List<Game> findGamesByPotSize(int potSize){
        return gameCenter.findGamesByPotSize(potSize);
    }

    public List<Game> findGamesByGamePolicy(GamePolicy policy) throws InvalidArgumentException {
        verifyObjects(policy);
        return gameCenter.findGamesByGamePolicy(policy);
    }

    public List<Game> findGamesByMaximumBuyIn(int maximumBuyIn){
        return gameCenter.findGamesByMaximumBuyIn(maximumBuyIn);
    }

    public List<Game> findGamesByChipPolicy(int chipPolicy){
        return gameCenter.findGamesByChipPolicy(chipPolicy);
    }

    public List<Game> findGamesByMinimumBet(int minimumBet){
        return gameCenter.findGamesByMinimumBet(minimumBet);
    }

    public List<Game> findGamesByMinimumPlayers(int minimumPlayers){
        return gameCenter.findGamesByMinimumPlayers(minimumPlayers);
    }

    public List<Game> findGamesByMaximumPlayers(int maximumPlayers){
        return gameCenter.findGamesByMaximumPlayers(maximumPlayers);
    }

    public List<String> findArchivedGamesNames() throws EntityDoesNotExistsException {
       return gameCenter.getArchivedGames();
    }
}
