package Client.communication;

import Client.common.exceptions.*;
import Client.communication.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Created by rotemwald on 14/05/17.
 */
public class GameRequestHandler {
    public final static String serviceURI = "http://localhost:8080/game";
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public GameRequestHandler() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void requestGameCreation(ClientGameDetails gameDetails) throws InvalidArgumentException, NoBalanceForBuyInException, ArgumentNotInBoundsException, EntityDoesNotExistsException {
        String addr = serviceURI + "/" + gameDetails.getName();
        HttpEntity<ClientGameDetails> request = new HttpEntity<>(gameDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.POST, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();

            ExceptionObject exceptionObj = null;
            try {
                exceptionObj = objectMapper.readValue(responseBody, ExceptionObject.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new InvalidArgumentException(exceptionObj.getMessage());
            }

            else if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new ArgumentNotInBoundsException(exceptionObj.getMessage());
            }

            else if (e.getStatusCode() == HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE) {
                throw new ArgumentNotInBoundsException(exceptionObj.getMessage());
            }

            else {
                throw new NoBalanceForBuyInException(exceptionObj.getMessage());
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
            String responseBody = e.getResponseBodyAsString();

            ExceptionObject exceptionObj = null;
            try {
                exceptionObj = objectMapper.readValue(responseBody, ExceptionObject.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw new GameException(exceptionObj.getMessage());
        }
    }

    public void requestGameLeave(ClientLeaveGameDetails leaveGameDetails) throws GameException {
        String addr = serviceURI + "/" + leaveGameDetails.getGameName();
        HttpEntity<ClientLeaveGameDetails> request = new HttpEntity<>(leaveGameDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.DELETE, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();

            ExceptionObject exceptionObj = null;
            try {
                exceptionObj = objectMapper.readValue(responseBody, ExceptionObject.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw new GameException(exceptionObj.getMessage());
        }
    }

    public List<ClientGameDetails> requestGameSearch(ClientGamePreferences gamePreferences) throws EntityDoesNotExistsException, InvalidArgumentException {
        HttpEntity<ClientGamePreferences> request = new HttpEntity<>(gamePreferences);

        try {
            ResponseEntity<ResponseMessage<List<ClientGameDetails>>> response = restTemplate.exchange(serviceURI, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<List<ClientGameDetails>>>() {});
            return response.getBody().getData();
        }
        catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();

            ExceptionObject exceptionObj = null;
            try {
                exceptionObj = objectMapper.readValue(responseBody, ExceptionObject.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new EntityDoesNotExistsException(exceptionObj.getMessage());
            }

            else {
                throw new InvalidArgumentException(exceptionObj.getMessage());
            }
        }
    }
}
