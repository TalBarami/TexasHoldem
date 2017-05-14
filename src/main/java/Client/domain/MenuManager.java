package Client.domain;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.communication.GameRequestHandler;
import Client.communication.entities.ClientGameDetails;
import Client.communication.entities.ClientGamePreferences;

import java.util.List;

/**
 * Created by User on 14/05/2017.
 */
public class MenuManager {
    private GameRequestHandler gameRequestHandler;

    private MenuManager(){
        gameRequestHandler = new GameRequestHandler();
    }

    public List<ClientGameDetails> findAvailableGames(String username) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setDisplayAllAvailableGames(true);
        pref.setUsername(username);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public ClientGameDetails findGameByName(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        // FIXME: Whats the name?
        return gameRequestHandler.requestGameSearch(pref).get(0);
    }

    public List<ClientGameDetails> findSpectatableGames() throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSerachBySpectatingAvailable(true);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByUsername(String username) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByUsername(true);
        pref.setUsernameToSearch(username);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByPotSize(int potSize) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        // FIXME: ????
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByGamePolicy(String policy) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByTypePolicy(true);
        // FIXME: What policy?
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMaximumBuyIn(int maximumBuyIn) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByBuyIn(true);
        pref.setBuyInAmount(maximumBuyIn);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByChipPolicy(int chipPolicy) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByChipPolicy(true);
        pref.setChipPolicyAmount(chipPolicy);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMinimumBet(int minimumBet) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setDisplayAllAvailableGames(true);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMinimumPlayers(int minimumPlayers) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByMinimumPlayersAmount(true);
        pref.setMinimumPlayersAmount(minimumPlayers);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMaximumPlayers(int maximumPlayers) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByMaximumPlayersAmount(true);
        pref.setMaximumPlayersAmount(maximumPlayers);
        return gameRequestHandler.requestGameSearch(pref);
    }
}
