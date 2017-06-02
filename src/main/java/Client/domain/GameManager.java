package Client.domain;

import MutualJsonObjects.*;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.GameException;
import Exceptions.InvalidArgumentException;

import Client.communication.GameRequestHandler;

import java.util.List;

/**
 * Created by User on 15/05/2017.
 */
public class GameManager {
    private ClientGameDetails gameDetails;
    private GameRequestHandler gameRequestHandler;

    private List<GameUpdateCallback> updateCallbacks;

    public GameManager(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        gameDetails = SearchManager.getInstance().findGameByName(gameName).get(0);
        gameRequestHandler = new GameRequestHandler();
    }

    public ClientGameDetails getGameDetails(){
        return gameDetails;
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
        request.setAction(Integer.parseInt(amount));
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

    public void sendMessage() throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setAction(9);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void updateGameDetails(ClientGameDetails details){
        for(GameUpdateCallback callback : updateCallbacks){
            callback.update(details);
        }
    }

    public interface GameUpdateCallback {
        void update(ClientGameDetails details);
    }

    public void addUpdateCallback(GameUpdateCallback callback){
        updateCallbacks.add(callback);
    }
}
