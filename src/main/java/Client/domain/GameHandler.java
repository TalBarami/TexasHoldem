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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 15/05/2017.
 */
public class GameHandler {
    private static Logger logger = LoggerFactory.getLogger(GameHandler.class);

    private ClientGameDetails gameDetails;
    private boolean isGameRunning;

    private GameRequestHandler gameRequestHandler;

    private List<GameUpdateCallback> gameUpdateCallbacks;
    private List<RoundUpdateCallback> roundUpdateCallbacks;
    private List<MoveUpdateCallback> moveUpdateCallbacks;
    private List<ChatUpdateCallback> chatUpdateCallbacks;

    public GameHandler(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        gameDetails = SearchHandler.getInstance().findGameByName(gameName).get(0);
        gameRequestHandler = new GameRequestHandler();

        gameUpdateCallbacks = new ArrayList<>();
        roundUpdateCallbacks = new ArrayList<>();
        moveUpdateCallbacks = new ArrayList<>();
        chatUpdateCallbacks = new ArrayList<>();

        SessionHandler.getInstance().getSessionHandler().addGameUpdateCallback(gameName, this::updateGameDetails);
        SessionHandler.getInstance().getSessionHandler().addRoundUpdateCallback(gameName, this::updateRoundDetails);
        SessionHandler.getInstance().getSessionHandler().addChatUpdateCallback(gameName, this::updateChat);
        SessionHandler.getInstance().getSessionHandler().addMoveUpdateCallback(gameName, this::updateGameMoves);
    }

    public ClientGameDetails getGameDetails(){
        return gameDetails;
    }

    public void startGame() throws GameException {
        handleGameAction(5);
    }

    public void playCheck() throws GameException {
        handleGameAction(0);
    }

    public void playCall() throws GameException {
        handleGameAction(2);
    }

    public void playFold() throws GameException {
        handleGameAction(3);
    }

    public void handleGameAction(int actionID) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameDetails.getName());
        request.setUsername(SessionHandler.getInstance().user().getUsername());
        request.setAction(actionID);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void playRaise(String amount) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameDetails.getName());
        request.setUsername(SessionHandler.getInstance().user().getUsername());
        request.setAmount(Integer.parseInt(amount));
        request.setAction(1);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void sendMessage(String message) throws GameException {
        sendMessageHandler(message, "");
    }

    public void sentPrivateMessage(String message, String playerName) throws GameException{
        sendMessageHandler(message, playerName);
    }

    private void sendMessageHandler(String message,String recipientUser) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameDetails.getName());
        request.setUsername(SessionHandler.getInstance().user().getUsername());
        request.setMessage(message);
        request.setRecipientUserName(recipientUser);
        request.setAction(9);

        gameRequestHandler.requestGameEventSend(request);
    }

    public boolean isGameRunning(){
        return isGameRunning;
    }

    public void setGameRunning(Boolean isGameRunning){
        this.isGameRunning = isGameRunning;
    }

    private void updateGameDetails(GameUpdateNotification gameUpdateNotification){
        logger.info("Received game update notification: {}", gameUpdateNotification);
        gameDetails = gameUpdateNotification.getGameDetails();
        gameUpdateCallbacks.forEach(c -> c.execute(gameUpdateNotification));
    }

    public void addGameUpdateCallback(GameUpdateCallback callback){
        gameUpdateCallbacks.add(callback);
    }

    private void updateRoundDetails(RoundUpdateNotification roundUpdateNotification){
        logger.info("Received round update notification: {}", roundUpdateNotification);
        roundUpdateCallbacks.forEach(c -> c.execute(roundUpdateNotification));
    }

    public void addRoundUpdateCallback(RoundUpdateCallback callback){
        roundUpdateCallbacks.add(callback);
    }

    private void updateChat(ChatNotification message){
        logger.info("Received chat update notification: {}", message);
        chatUpdateCallbacks.forEach(c -> c.execute(message));
    }

    public void addChatUpdateCallback(ChatUpdateCallback callback){
        chatUpdateCallbacks.add(callback);
    }

    private void updateGameMoves(List<Move> possibleMoves){
        logger.info("Received moves update notification: {}", possibleMoves);
        moveUpdateCallbacks.forEach(c -> c.execute(possibleMoves));
    }

    public void addMoveUpdateCallback(MoveUpdateCallback callback){
        moveUpdateCallbacks.add(callback);
    }
}
