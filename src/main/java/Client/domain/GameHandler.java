package Client.domain;

import Client.ClientUtils;
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
import com.google.common.collect.EvictingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by User on 15/05/2017.
 */
public class GameHandler {
    private static Logger logger = LoggerFactory.getLogger(GameHandler.class);

    private ClientGameDetails gameDetails;

    private GameRequestHandler gameRequestHandler;

    private List<GameUpdateCallback> gameUpdateCallbacks;
    private List<RoundUpdateCallback> roundUpdateCallbacks;
    private List<MoveUpdateCallback> moveUpdateCallbacks;
    private List<ChatUpdateCallback> chatUpdateCallbacks;

    private Queue<String> notificationsMessages;

    public GameHandler(String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        gameDetails = SearchHandler.getInstance().findGameByName(gameName).get(0);
        gameRequestHandler = new GameRequestHandler();

        gameUpdateCallbacks = new ArrayList<>();
        roundUpdateCallbacks = new ArrayList<>();
        moveUpdateCallbacks = new ArrayList<>();
        chatUpdateCallbacks = new ArrayList<>();

        notificationsMessages = EvictingQueue.create(3);

        SessionHandler.getInstance().getSessionHandler().addGameUpdateCallback(gameName, this::updateGameDetails);
        SessionHandler.getInstance().getSessionHandler().addRoundUpdateCallback(gameName, this::updateRoundDetails);
        SessionHandler.getInstance().getSessionHandler().addChatUpdateCallback(gameName, this::updateChat);
        SessionHandler.getInstance().getSessionHandler().addMoveUpdateCallback(gameName, this::updateGameMoves);
    }

    public ClientGameDetails getGameDetails(){
        return gameDetails;
    }

    public void startGame() throws GameException {
        logger.info("Start game called.");
        handleGameAction(5);
    }

    public void playCheck() throws GameException {
        logger.info("Play check called.");
        handleGameAction(0);
    }

    public void playCall() throws GameException {
        logger.info("Play call called.");
        handleGameAction(2);
    }

    public void playFold() throws GameException {
        logger.info("Play fold called.");
        handleGameAction(3);
    }

    private void handleGameAction(int actionID) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameDetails.getName());
        request.setUsername(SessionHandler.getInstance().user().getUsername());
        request.setAction(actionID);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void playRaise(String amount) throws GameException {
        logger.info("Play raise called for {}.", amount);
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameDetails.getName());
        request.setUsername(SessionHandler.getInstance().user().getUsername());
        request.setAmount(Integer.parseInt(amount));
        request.setAction(1);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void sendMessage(String message) throws GameException {
        logger.info("Sent message: {}", message);
        sendMessage(message, "");
    }

    public void sentPrivateMessage(String message, String playerName) throws GameException{
        logger.info("Sent private message to {}: \"{}\"", playerName, message);
        sendMessage(message, playerName);
    }

    private void addNotificationsMessages(GameUpdateNotification gameUpdateNotification) {
        String action = "";
        switch(gameUpdateNotification.getAction()){
            case ENTER:
                action = "joined";
                break;
            case EXIT:
                action = "left";
                break;
            case NEWROUND:
                action = "started";
                break;
        }

        notificationsMessages.add(String.format("%s has %s the game.", gameUpdateNotification.getGameActionInitiator(), action));
    }

    public List<String> getNotificationMessages(){
        return new ArrayList<>(notificationsMessages);
    }

    private void addNotificationsMessages(RoundUpdateNotification roundUpdateNotification){
        if(roundUpdateNotification.isFinished()){
            List<String> winnersNames = roundUpdateNotification.getWinnerPlayers().stream().map(ClientPlayer::getPlayerName).collect(Collectors.toList());

            notificationsMessages.add(winnersNames.size() > 1 ? String.format("Round is finished. Winners are: %s", ClientUtils.prettyList(winnersNames))
                    : String.format("Round is finished. Winner is: %s", winnersNames.get(0)));
        } else {
            notificationsMessages.add(String.format("It's %s turn.", roundUpdateNotification.getCurrentPlayerName()));
        }
    }

    private void sendMessage(String message, String recipientUser) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameDetails.getName());
        request.setUsername(SessionHandler.getInstance().user().getUsername());
        request.setMessage(message);
        request.setRecipientUserName(recipientUser);
        request.setAction(9);

        gameRequestHandler.requestGameEventSend(request);
    }

    private void updateGameDetails(GameUpdateNotification gameUpdateNotification){
        logger.info("Received game update notification: {}", gameUpdateNotification.toString());
        gameDetails = gameUpdateNotification.getGameDetails();
        addNotificationsMessages(gameUpdateNotification);
        gameUpdateCallbacks.forEach(c -> c.execute(gameUpdateNotification));
    }

    public void addGameUpdateCallback(GameUpdateCallback callback){
        gameUpdateCallbacks.add(callback);
    }

    private void updateRoundDetails(RoundUpdateNotification roundUpdateNotification){
        logger.info("Received round update notification: {}", roundUpdateNotification.toString());
        addNotificationsMessages(roundUpdateNotification);
        roundUpdateCallbacks.forEach(c -> c.execute(roundUpdateNotification));
    }

    public void addRoundUpdateCallback(RoundUpdateCallback callback){
        roundUpdateCallbacks.add(callback);
    }

    private void updateChat(ChatNotification message){
        logger.info("Received chat update notification: {}", message.toString());
        chatUpdateCallbacks.forEach(c -> c.execute(message));
    }

    public void addChatUpdateCallback(ChatUpdateCallback callback){
        chatUpdateCallbacks.add(callback);
    }

    private void updateGameMoves(List<Move> possibleMoves){
        logger.info("Received moves update notification: {}", possibleMoves.toString());
        moveUpdateCallbacks.forEach(c -> c.execute(possibleMoves));
    }

    public void addMoveUpdateCallback(MoveUpdateCallback callback){
        moveUpdateCallbacks.add(callback);
    }
}
