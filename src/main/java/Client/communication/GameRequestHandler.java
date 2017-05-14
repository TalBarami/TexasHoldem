package Client.communication;

import Client.common.exceptions.ArgumentNotInBoundsException;
import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.common.exceptions.NoBalanceForBuyInException;
import Client.communication.entities.ClientGameDetails;
import Client.communication.entities.ResponseMessage;
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
}
