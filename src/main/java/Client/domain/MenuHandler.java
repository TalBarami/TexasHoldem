package Client.domain;

import Client.communication.ReplayRequestHandler;
import Enumerations.GamePolicy;
import MutualJsonObjects.*;
import Exceptions.*;

import Client.communication.GameRequestHandler;
import Client.communication.UserRequestHandler;
import Server.domain.user.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler {
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
                chipPolicy, minPlayerAmount, maxPlayerAmount, specAccept, new ArrayList<>(), false);
        gameRequestHandler.requestGameCreation(gameDetails);
    }

    public void joinGame(String username, String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameName);
        request.setUsername(username);
        request.setAction(4);

        gameRequestHandler.requestGameEventSend(request);
    }

    public void spectateGame(String username, String gameName) throws GameException {
        ClientGameRequest request = new ClientGameRequest();
        request.setGameName(gameName);
        request.setUsername(username);
        request.setAction(4);
        request.setSpectating(true);

        gameRequestHandler.requestGameEventSend(request);
    }

    public List<ClientGameEvent> replayGame(String gameName) throws GameException {
        return replayRequestHandler.requestGameReplay(gameName);
    }

    public void leaveGame(String username, String gameName) throws GameException {
        ClientLeaveGameDetails leaveGameDetails = new ClientLeaveGameDetails();
        leaveGameDetails.setGameName(gameName);
        leaveGameDetails.setUsername(username);
        gameRequestHandler.requestGameLeave(leaveGameDetails);
    }

    public void deposit(String username, int amount) throws InvalidArgumentException, ArgumentNotInBoundsException, EntityDoesNotExistsException {
        ClientTransactionRequest request = new ClientTransactionRequest();
        request.setAction(Transaction.DEPOSIT);
        request.setAmount(amount);
        userRequestHandler.requestUserTransaction(username, request);
    }
}
