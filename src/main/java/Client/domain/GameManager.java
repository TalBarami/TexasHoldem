package Client.domain;

import Client.domain.callbacks.ChatUpdateCallback;
import Client.domain.callbacks.GameUpdateCallback;
import Client.domain.callbacks.RoundUpdateCallback;
import Client.domain.callbacks.MoveUpdateCallback;
import Enumerations.Move;
import MutualJsonObjects.*;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.GameException;
import Exceptions.InvalidArgumentException;

import Client.communication.GameRequestHandler;
import NotificationMessages.ChatNotification;
import NotificationMessages.GameUpdateNotification;
import NotificationMessages.RoundUpdateNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15/05/2017.
 */
public class GameManager {
    private ClientGameDetails gameDetails;

    private GameRequestHandler gameRequestHandler;

    private List<GameUpdateCallback> gameUpdateCallbacks;
    private List<RoundUpdateCallback> roundUpdateCallbacks;
    private List<MoveUpdateCallback> moveUpdateCallbacks;
    private List<ChatUpdateCallback> chatUpdateCallbacks;

    public GameManager(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        gameDetails = SearchManager.getInstance().findGameByName(gameName).get(0);
        gameRequestHandler = new GameRequestHandler();

        gameUpdateCallbacks = new ArrayList<>();
        roundUpdateCallbacks = new ArrayList<>();
        moveUpdateCallbacks = new ArrayList<>();
        chatUpdateCallbacks = new ArrayList<>();

        SessionManager.getInstance().getSessionHandler().addGameUpdateCallback(gameName, this::updateGameDetails);
        SessionManager.getInstance().getSessionHandler().addRoundUpdateCallback(gameName, this::updateRoundDetails);
        SessionManager.getInstance().getSessionHandler().addChatUpdateCallback(gameName, this::updateChat);
        SessionManager.getInstance().getSessionHandler().addMoveUpdateCallback(gameName, this::updateGameMoves);
    }

    public ClientGameDetails getGameDetails(){
        return gameDetails;
    }

    public void startGame() throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setAction(5);

        gameRequestHandler.requestGameEventSend(request);
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
        request.setAction(2);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void playRaise(String amount) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setAction(Integer.parseInt(amount));
        request.setAction(1);

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
        request.setMessage(message);
        request.setAction(9);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void sentPrivateMessage(String message, String playerName) throws GameException{
        ClientGameRequest request = new ClientGameRequest();
        request.setGamename(gameDetails.getName());
        request.setUsername(SessionManager.getInstance().user().getUsername());
        request.setMessage(message);
        request.setRecipientUserName(playerName);
        request.setAction(9);

        gameRequestHandler.requestGameEventSend(request);
    }

    private void updateGameDetails(GameUpdateNotification gameUpdateNotification){
        gameUpdateCallbacks.parallelStream().forEach(c -> c.execute(gameUpdateNotification));
    }

    public void addGameUpdateCallback(GameUpdateCallback callback){
        gameUpdateCallbacks.add(callback);
    }

    private void updateRoundDetails(RoundUpdateNotification roundUpdateNotification){
        roundUpdateCallbacks.parallelStream().forEach(c -> c.execute(roundUpdateNotification));
    }

    public void addRoundUpdateCallback(RoundUpdateCallback callback){
        roundUpdateCallbacks.add(callback);
    }

    private void updateChat(ChatNotification message){
        chatUpdateCallbacks.parallelStream().forEach(c -> c.execute(message));
    }

    public void addChatUpdateCallback(ChatUpdateCallback callback){
        chatUpdateCallbacks.add(callback);
    }

    private void updateGameMoves(List<Move> possibleMoves){
        moveUpdateCallbacks.parallelStream().forEach(c -> c.execute(possibleMoves));
    }

    public void addMoveUpdateCallback(MoveUpdateCallback callback){
        moveUpdateCallbacks.add(callback);
    }
}
