package Client.communication;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.common.exceptions.LoginException;
import TexasHoldem.communication.entities.ClientUserDetails;
import TexasHoldem.communication.entities.ResponseMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rotemwald on 14/05/17.
 */
public class SessionRequestHandler {
    public final static String serviceURI = "http://localhost:8080/session";
    public RestTemplate restTemplate;

    public SessionRequestHandler() {
        this.restTemplate = new RestTemplate();
    }

    public void requestUserLogin(ClientUserDetails loginDetails) throws javax.security.auth.login.LoginException, InvalidArgumentException, EntityDoesNotExistsException {
        HttpEntity<ClientUserDetails> request = new HttpEntity<>(loginDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(serviceURI, HttpMethod.POST, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new InvalidArgumentException(e.getMessage());
            }
            else if (e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new EntityDoesNotExistsException(e.getMessage());
            }
            else {
                throw new LoginException(e.getMessage());
            }
        }
    }

    public void requestUserLogout(ClientUserDetails logoutDetails) throws InvalidArgumentException {
        HttpEntity<ClientUserDetails> request = new HttpEntity<>(logoutDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(serviceURI, HttpMethod.DELETE, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
                throw new InvalidArgumentException(e.getMessage());
        }
    }
}
