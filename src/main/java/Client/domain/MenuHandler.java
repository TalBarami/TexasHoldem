package Client.domain;

import Client.communication.ReplayRequestHandler;
import Enumerations.GamePolicy;
import MutualJsonObjects.*;
import Exceptions.*;

import Client.communication.GameRequestHandler;
import Client.communication.UserRequestHandler;
import Server.domain.user.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler {
    private static Logger logger = LoggerFactory.getLogger(MenuHandler.class);
    private static MenuHandler instance;

    private GameRequestHandler gameRequestHandler;
    private UserRequestHandler userRequestHandler;
    private ReplayRequestHandler replayRequestHandler;

    private MenuHandler(){
        gameRequestHandler = new GameRequestHandler();
        userRequestHandler = new UserRequestHandler();
        replayRequestHandler = new ReplayRequestHandler();
    }

    public static MenuHandler getInstance(){
        if(instance == null)
            instance = new MenuHandler();
        return instance;
    }

    public void createGame(String gameName, GamePolicy gamePolicy, int policyLimit, int minBet, int buyInPolicy, int chipPolicy,
                           int minPlayerAmount, int maxPlayerAmount, boolean specAccept) throws InvalidArgumentException, EntityDoesNotExistsException, NoBalanceForBuyInException, ArgumentNotInBoundsException {
        ClientGameDetails gameDetails = new ClientGameDetails(SessionHandler.getInstance().user().getUsername(),
                gameName, gamePolicy.getPolicy(), policyLimit, minBet, buyInPolicy,
                chipPolicy, minPlayerAmount, maxPlayerAmount, specAccept, new ArrayList<>(), false, false);
        logger.info("Sent game creation request for {}", gameDetails);

        gameRequestHandler.requestGameCreation(gameDetails);
    }

    public void joinGame(String username, String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameName);
        request.setUsername(username);
        request.setAction(4);
        logger.info("Sent game join request {}", request);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void spectateGame(String username, String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameName);
        request.setUsername(username);
        request.setAction(4);
        request.setSpectating(true);
        logger.info("Sent game spectate request {}", request);

        gameRequestHandler.requestGameEventSend(request);
    }

    public List<ClientGameEvent> replayGame(String gameName) throws GameException {
        logger.info("Sent game replay request {}", gameName);
        return replayRequestHandler.requestGameReplay(gameName);
    }

    public void leaveGame(String username, String gameName) throws GameException {
        ClientLeaveGameDetails leaveGameDetails = new ClientLeaveGameDetails();
        leaveGameDetails.setGameName(gameName);
        leaveGameDetails.setUsername(username);
        logger.info("Sent game leave request {}", leaveGameDetails);

        gameRequestHandler.requestGameLeave(leaveGameDetails);
    }

    public void deposit(String username, int amount) throws InvalidArgumentException, ArgumentNotInBoundsException, EntityDoesNotExistsException {
        ClientTransactionRequest request = new ClientTransactionRequest();
        request.setAction(Transaction.DEPOSIT);
        request.setAmount(amount);
        logger.info("Sent game deposit request {}", request);

        userRequestHandler.requestUserTransaction(username, request);
    }
}
