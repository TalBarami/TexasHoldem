package Server.service;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Server.domain.game.Game;
import Enumerations.GamePolicy;
import Server.domain.system.GameCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static Server.service.TexasHoldemService.verifyObjects;
import static Server.service.TexasHoldemService.verifyStrings;

/**
 * Created by Tal on 05/05/2017.
 */
public class SearchService {
    private static Logger logger = LoggerFactory.getLogger(SearchService.class);
    private GameCenter gameCenter;

    public SearchService(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public List<Game> findAvailableGames(String username) throws InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("New search for available games request with the following info [user name requester: {}]", username);
        verifyStrings(username);
        return gameCenter.findAvailableGames(username);
    }

    public Game findGameByName(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        logger.info("New search for game by name request with the following info [game name: {}]", gameName);
        verifyStrings(gameName);
        return gameCenter.findGameByName(gameName);
    }

    public List<Game> findSpectatableGames(){
        logger.info("New search for spectateable game request.");
        return gameCenter.findSpectateableGames();
    }

    public List<Game> findGamesByUsername(String username) throws InvalidArgumentException {
        logger.info("New search for game by user name request with the following info [search by user: {}]", username);
        verifyStrings(username);
        return gameCenter.findGamesByUsername(username);
    }

    public List<Game> findGamesByPotSize(int potSize){
        logger.info("New search for game by pot size request with the following info [pot size: {}]", potSize);
        return gameCenter.findGamesByPotSize(potSize);
    }

    public List<Game> findGamesByGamePolicy(GamePolicy policy) throws InvalidArgumentException {
        logger.info("New search for game by policy request with the following info [policy: {}]", policy);
        verifyObjects(policy);
        return gameCenter.findGamesByGamePolicy(policy);
    }

    public List<Game> findGamesByMaximumBuyIn(int maximumBuyIn){
        logger.info("New search for game by maximum buy in request with the following info [maximal buy in: {}]", maximumBuyIn);
        return gameCenter.findGamesByMaximumBuyIn(maximumBuyIn);
    }

    public List<Game> findGamesByChipPolicy(int chipPolicy){
        logger.info("New search for game by chip policy request with the following info [chip policy: {}]", chipPolicy);
        return gameCenter.findGamesByChipPolicy(chipPolicy);
    }

    public List<Game> findGamesByMinimumBet(int minimumBet){
        logger.info("New search for game by minimum bet request with the following info [minimum bet: {}]", minimumBet);
        return gameCenter.findGamesByMinimumBet(minimumBet);
    }

    public List<Game> findGamesByMinimumPlayers(int minimumPlayers){
        logger.info("New search for game by minimum players request with the following info [minimum players: {}]", minimumPlayers);
        return gameCenter.findGamesByMinimumPlayers(minimumPlayers);
    }

    public List<Game> findGamesByMaximumPlayers(int maximumPlayers){
        logger.info("New search for game by maximum players request with the following info [maximum players: {}]", maximumPlayers);
        return gameCenter.findGamesByMaximumPlayers(maximumPlayers);
    }

    public List<String> findArchivedGamesNames() throws EntityDoesNotExistsException {
        logger.info("New search for archived games request with the following info.");
       return gameCenter.getArchivedGames();
    }
}
