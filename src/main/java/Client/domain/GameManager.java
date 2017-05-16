package Client.domain;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.GameException;
import Client.common.exceptions.InvalidArgumentException;
import Client.communication.GameRequestHandler;
import Client.communication.entities.ClientGameDetails;
import Client.communication.entities.ClientGameRequest;
import Client.communication.entities.ClientUserDetails;
import Client.communication.entities.ClientUserProfile;

/**
 * Created by User on 15/05/2017.
 */
public class GameManager {
    private ClientGameDetails gameDetails;
    private GameRequestHandler gameRequestHandler;

    public GameManager(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        gameDetails = SearchManager.getInstance().findGameByName(gameName).get(0);
        gameRequestHandler = new GameRequestHandler();
    }

    public ClientGameDetails getGameDetails(){
        return gameDetails;
    }

    public ClientUserProfile getUser(){
        return SessionManager.getInstance().user();
    }

    public void playCheck() throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setAction(0);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void playCall() throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setAction(1);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void playRaise(String amount) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setAction(2);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void playFold() throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setAction(3);

        gameRequestHandler.requestGameEventSend(request);
    }

}