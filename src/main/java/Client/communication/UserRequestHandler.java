package Client.communication;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.ExceptionObject;
import Client.common.exceptions.InvalidArgumentException;
import Client.communication.entities.ClientUserProfile;
import Client.communication.entities.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by user on 13/05/2017.
 */
public class UserRequestHandler {
    public final static String serviceURI = "http://localhost:8080/user";
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public UserRequestHandler() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void requestUserProfileRegistration(ClientUserProfile userProfile) throws InvalidArgumentException {
        String addr = serviceURI + "/" + userProfile.getUsername();
        HttpEntity<ClientUserProfile> request = new HttpEntity<>(userProfile);

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

            throw new InvalidArgumentException(exceptionObj.getMessage());
        }
    }

    public void requestUserProfileUpdate(String oldUserName, ClientUserProfile userProfile) throws InvalidArgumentException, EntityDoesNotExistsException {
        String addr = serviceURI + "/" + oldUserName;
        HttpEntity<ClientUserProfile> request = new HttpEntity<>(userProfile);

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

            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new InvalidArgumentException(exceptionObj.getMessage());
            }
            else {
                throw new EntityDoesNotExistsException(exceptionObj.getMessage());
            }
        }
    }


    public void requestUserProfileDelete(String username) throws InvalidArgumentException, EntityDoesNotExistsException {
        String addr = serviceURI + "/" + username;

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.DELETE, null, ResponseMessage.class);
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
            else {
                throw new EntityDoesNotExistsException(exceptionObj.getMessage());
            }
        }
    }

    public Client.communication.entities.ClientUserProfile requestUserProfileEntity(String username) throws InvalidArgumentException {
        String addr = serviceURI + "/" + username;

        try {
            ResponseEntity<ResponseMessage<ClientUserProfile>> response = restTemplate.exchange(addr, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<ClientUserProfile>>() {});
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

            throw new InvalidArgumentException(exceptionObj.getMessage());
        }
    }
}
