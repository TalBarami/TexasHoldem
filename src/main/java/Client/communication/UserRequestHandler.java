package Client.communication;

import Client.common.exceptions.InvalidArgumentException;
import TexasHoldem.communication.entities.ClientUserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
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

    public void requestUserRegistration(ClientUserProfile userProfile) throws InvalidArgumentException {
        String addr = serviceURI + "/" + userProfile.getUsername();
        ResponseEntity<Object> ans = restTemplate.postForEntity(addr, userProfile, Object.class);

        if (ans.getStatusCode().is4xxClientError()) {
            throw new InvalidArgumentException("x");
        }
    }

    public void requestUserUpdate(ClientUserProfile userDetails) throws RestClientException {
        String addr = serviceURI + "/" + userDetails.getUsername();
        restTemplate.put(addr, userDetails);
    }
}
