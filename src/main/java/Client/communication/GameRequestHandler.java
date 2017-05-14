package Client.communication;

import Client.common.exceptions.*;
import Client.communication.entities.ClientGameDetails;
import Client.communication.entities.ResponseMessage;
import Client.communication.entities.ClientGameRequest;
import TexasHoldem.communication.entities.ClientLeaveGameDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rotemwald on 14/05/17.
 */
public class GameRequestHandler {
    public final static String serviceURI = "http://localhost:8080/game";
    public RestTemplate restTemplate;

    public GameRequestHandler() {
        restTemplate = new RestTemplate();
    }

    public void requestGameCreation(ClientGameDetails gameDetails) throws InvalidArgumentException, NoBalanceForBuyInException, ArgumentNotInBoundsException, EntityDoesNotExistsException {
        String addr = serviceURI + "/" + gameDetails.getName();
        HttpEntity<ClientGameDetails> request = new HttpEntity<>(gameDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.POST, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new InvalidArgumentException(e.getMessage());
            }

            else if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new ArgumentNotInBoundsException(e.getMessage());
            }

            else if (e.getStatusCode() == HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE) {
                throw new ArgumentNotInBoundsException(e.getMessage());
            }

            else {
                throw new NoBalanceForBuyInException(e.getMessage());
            }
        }
    }

    public void requestGameEventSend(ClientGameRequest gameRequest) throws GameException {
        String addr = serviceURI + "/" + gameRequest.getGamename();
        HttpEntity<ClientGameRequest> request = new HttpEntity<>(gameRequest);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.PUT, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            throw new GameException(e.getMessage());
        }
    }

    public void requestGameLeave(ClientLeaveGameDetails leaveGameDetails) throws GameException {
        String addr = serviceURI + "/" + leaveGameDetails.getGameName();
        HttpEntity<ClientLeaveGameDetails> request = new HttpEntity<>(leaveGameDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.DELETE, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            throw new GameException(e.getMessage());
        }
    }
}
