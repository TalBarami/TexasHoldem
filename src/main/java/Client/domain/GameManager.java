package Client.domain;

import Client.common.exceptions.ArgumentNotInBoundsException;
import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.common.exceptions.NoBalanceForBuyInException;
import Client.communication.GameRequestHandler;
import Client.communication.entities.ClientGameDetails;
import Client.communication.entities.ClientGameRequest;

/**
 * Created by User on 15/05/2017.
 */
public class GameManager {
    private static GameManager instance;

    private GameRequestHandler gameRequestHandler;

    private GameManager(){
        gameRequestHandler = new GameRequestHandler();
    }

    public static GameManager getInstance(){
        if(instance == null)
            instance = new GameManager();
        return instance;
    }

    public void createGame(String gameName, String gamePolicy, int policyLimit, int minBet, int buyInPolicy, int chipPolicy,
                           int minPlayerAmount, int maxPlayerAmount, boolean specAccept) throws InvalidArgumentException, EntityDoesNotExistsException, NoBalanceForBuyInException, ArgumentNotInBoundsException {
        // FIXME: Handle game policy
        ClientGameDetails gameDetails = new ClientGameDetails(SessionManager.getInstance().user().getUsername(), gameName, 1, policyLimit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount, specAccept);
        gameRequestHandler.requestGameCreation(gameDetails);
    }

    public void joinGame(){

    }

    public void spectateGame(){

    }

    public void replayGame(){

    }

    public void leaveGame(){

    }

    public void deposit(int amount){

    }
}
