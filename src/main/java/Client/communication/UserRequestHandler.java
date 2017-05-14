package Client.communication;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import TexasHoldem.communication.entities.ClientUserProfile;
import TexasHoldem.communication.entities.ResponseMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by user on 13/05/2017.
 */
public class UserRequestHandler {
    public final static String serviceURI = "http://localhost:8080/user";
    public RestTemplate restTemplate;

    public UserRequestHandler() {
        this.restTemplate = new RestTemplate();
    }

    public void requestUserProfileRegistration(ClientUserProfile userProfile) throws InvalidArgumentException {
        String addr = serviceURI + "/" + userProfile.getUsername();
        HttpEntity<ClientUserProfile> request = new HttpEntity<>(userProfile);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.POST, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            throw new InvalidArgumentException(e.getMessage());
        }
    }

    public void requestUserProfileUpdate(String oldUserName, ClientUserProfile userProfile) throws InvalidArgumentException, EntityDoesNotExistsException {
        String addr = serviceURI + "/" + oldUserName;
        HttpEntity<ClientUserProfile> request = new HttpEntity<>(userProfile);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.PUT, request, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new InvalidArgumentException(e.getMessage());
            }
            else {
                throw new EntityDoesNotExistsException(e.getMessage());
            }
        }
    }


    public void requestUserProfileDelete(String username) throws InvalidArgumentException, EntityDoesNotExistsException {
        String addr = serviceURI + "/" + username;

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.DELETE, null, ResponseMessage.class);
        }
        catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new InvalidArgumentException(e.getMessage());
            }
            else {
                throw new EntityDoesNotExistsException(e.getMessage());
            }
        }
    }

    public Client.communication.entities.ClientUserProfile requestUserProfileEntity(String username) throws InvalidArgumentException {
        String addr = serviceURI + "/" + username;

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.GET, null, ResponseMessage.class);
            return (Client.communication.entities.ClientUserProfile)response.getBody().getData();
        }
        catch (HttpStatusCodeException e) {
            throw new InvalidArgumentException(e.getMessage());
        }
    }
}
