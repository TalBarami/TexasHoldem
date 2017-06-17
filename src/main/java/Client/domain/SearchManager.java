package Client.domain;

import MutualJsonObjects.ClientGameDetails;
import MutualJsonObjects.ClientGamePreferences;
import Enumerations.GamePolicy;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;

import Client.communication.GameRequestHandler;

import java.util.*;

/**
 * Created by User on 14/05/2017.
 */
public class SearchManager {
    private static SearchManager instance;
    private GameRequestHandler gameRequestHandler;
    private Map<String, SearchPolicy> searchPolicies;

    public static final String GAME_NAME ="Game name", USERNAME = "User name", GAME_POLICY = "Game policy",
            POT_SIZE = "Pot size", MAX_BUYIN = "Maximum buy-in", CHIP_POLICY = "Chip policy", MIN_BET = "Minimum bet",
            MIN_PLAYERS = "Minimum players", MAX_PLAYERS = "Maximum players",
            AVAILABLE = "Available", SPECTATEABLE = "Spectateable", REPLAYABLE = "Replayable";

    private SearchManager(){
        gameRequestHandler = new GameRequestHandler();
        generateSearchTypes();
    }

    public static SearchManager getInstance(){
        if(instance == null)
            instance = new SearchManager();
        return instance;
    }

    public List<ClientGameDetails> findAvailableGames(String ignore) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setDisplayAllAvailableGames(true);
        String sessionUser = SessionManager.getInstance().user().getUsername();
        pref.setUsername(sessionUser);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGameByName(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        return Collections.singletonList(gameRequestHandler.requestGameEntity(gameName));
    }

    public List<ClientGameDetails> findSpectateableGames(String ignore) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSerachBySpectatingAvailable(true);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findReplayableGames(String ignore) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        // FIXME: Add "find replayable"
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByUsername(String username) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByUsername(true);
        pref.setUsernameToSearch(username);
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByPotSize(String potSize) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        // FIXME: Set pot size & boolean field
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByGamePolicy(String policy) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByTypePolicy(true);
        pref.setPolicyNumberToSearch(GamePolicy.valueOf(policy).getPolicy());
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMaximumBuyIn(String maximumBuyIn) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByBuyIn(true);
        pref.setBuyInAmount(Integer.parseInt(maximumBuyIn));
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByChipPolicy(String chipPolicy) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByChipPolicy(true);
        pref.setChipPolicyAmount(Integer.parseInt(chipPolicy));
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMinimumBet(String minimumBet) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByMinimumBet(true);
        pref.setMinimumBetAmount(Integer.parseInt(minimumBet));
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMinimumPlayers(String minimumPlayers) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByMinimumPlayersAmount(true);
        pref.setMinimumPlayersAmount(Integer.parseInt(minimumPlayers));
        return gameRequestHandler.requestGameSearch(pref);
    }

    public List<ClientGameDetails> findGamesByMaximumPlayers(String maximumPlayers) throws EntityDoesNotExistsException, InvalidArgumentException {
        ClientGamePreferences pref = new ClientGamePreferences();
        pref.setSearchByMaximumPlayersAmount(true);
        pref.setMaximumPlayersAmount(Integer.parseInt(maximumPlayers));
        return gameRequestHandler.requestGameSearch(pref);
    }

    private void generateSearchTypes(){
        searchPolicies = new HashMap<>();
        searchPolicies.put(GAME_NAME, this::findGameByName);
        searchPolicies.put(USERNAME, this::findGamesByUsername);
        searchPolicies.put(POT_SIZE, this::findGamesByPotSize);
        searchPolicies.put(GAME_POLICY, this::findGamesByGamePolicy);
        searchPolicies.put(MAX_BUYIN, this::findGamesByMaximumBuyIn);
        searchPolicies.put(CHIP_POLICY, this::findGamesByChipPolicy);
        searchPolicies.put(MIN_BET, this::findGamesByMinimumBet);
        searchPolicies.put(MIN_PLAYERS, this::findGamesByMinimumPlayers);
        searchPolicies.put(MAX_PLAYERS, this::findGamesByMaximumPlayers);
        searchPolicies.put(AVAILABLE, this::findAvailableGames);
        searchPolicies.put(SPECTATEABLE, this::findSpectateableGames);
        searchPolicies.put(REPLAYABLE, this::findReplayableGames);
    }

    public SearchPolicy getPolicy(String value){
        return searchPolicies.get(value);
    }

    public Set<String> getPoliciesNames(){
        return searchPolicies.keySet();
    }

    public interface SearchPolicy {
        List<ClientGameDetails> find(String value) throws EntityDoesNotExistsException, InvalidArgumentException;
    }
}
