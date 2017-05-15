package Client.domain;

import Client.common.exceptions.*;
import Client.communication.GameRequestHandler;
import Client.communication.entities.ClientGameDetails;
import Client.communication.entities.ClientGameRequest;

/**
 * Created by User on 15/05/2017.
 */
public class MenuManager {
    private static MenuManager instance;

    private GameRequestHandler gameRequestHandler;

    private MenuManager(){
        gameRequestHandler = new GameRequestHandler();
    }

    public static MenuManager getInstance(){
        if(instance == null)
            instance = new MenuManager();
        return instance;
    }

    public void createGame(String gameName, String gamePolicy, int policyLimit, int minBet, int buyInPolicy, int chipPolicy,
                           int minPlayerAmount, int maxPlayerAmount, boolean specAccept) throws InvalidArgumentException, EntityDoesNotExistsException, NoBalanceForBuyInException, ArgumentNotInBoundsException {
        // FIXME: Handle game policy
        ClientGameDetails gameDetails = new ClientGameDetails(SessionManager.getInstance().user().getUsername(), gameName, 1, policyLimit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount, specAccept);
        gameRequestHandler.requestGameCreation(gameDetails);

    }

    public void joinGame(String username, String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameName);
        request.setUsername(username);
        request.setAction(4);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void spectateGame(String username, String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameName);
        request.setUsername(username);
        request.setAction(4);
        request.setSpectating(true);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void replayGame(String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameName);
        request.setAction(-1);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void leaveGame(String username, String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameName);
        request.setUsername(username);
        request.setAction(6);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void deposit(int amount){

    }
}
