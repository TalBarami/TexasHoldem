package Client.domain;

import Client.domain.callbacks.ChatUpdateCallback;
import Client.domain.callbacks.GameUpdateCallback;
import Client.domain.callbacks.MoveUpdateCallback;
import Enumerations.Move;
import MutualJsonObjects.*;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.GameException;
import Exceptions.InvalidArgumentException;

import Client.communication.GameRequestHandler;
import NotificationMessages.MessageNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15/05/2017.
 */
public class GameManager {
    private ClientGameDetails gameDetails;
    private GameRequestHandler gameRequestHandler;

    private List<GameUpdateCallback> gameUpdateCallbacks;
    private List<MoveUpdateCallback> moveUpdateCallbacks;
    private List<ChatUpdateCallback> chatUpdateCallbacks;

    public GameManager(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        gameDetails = SearchManager.getInstance().findGameByName(gameName).get(0);
        gameRequestHandler = new GameRequestHandler();

        gameUpdateCallbacks = new ArrayList<>();
        moveUpdateCallbacks = new ArrayList<>();
        chatUpdateCallbacks = new ArrayList<>();
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

    public void sendMessage(String message) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setMassage(message);
        request.setAction(9);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void updateGameDetails(ClientGameDetails gameDetails){
        this.gameDetails = gameDetails;
        gameUpdateCallbacks.parallelStream().forEach(c -> c.execute(gameDetails));
    }

    public void addGameUpdateCallback(GameUpdateCallback callback){
        gameUpdateCallbacks.add(callback);
    }

    public void updateChat(MessageNotification message){
        chatUpdateCallbacks.parallelStream().forEach(c -> c.execute(message.getSenderUserName() + ": " + message.getMessageContent()));
    }

    public void addChatUpdateCallback(ChatUpdateCallback callback){
        chatUpdateCallbacks.add(callback);
    }

    public void updateGameMoves(List<Move> possibleMoves){
        moveUpdateCallbacks.parallelStream().forEach(c -> c.execute(possibleMoves));
    }

    public void addMoveUpdateCallback(MoveUpdateCallback callback){
        moveUpdateCallbacks.add(callback);
    }
}
