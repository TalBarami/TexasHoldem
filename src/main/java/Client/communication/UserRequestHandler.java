package Client.communication;

import MutualJsonObjects.ClientUserProfile;
import MutualJsonObjects.ResponseMessage;
import MutualJsonObjects.ClientTransactionRequest;

import Exceptions.ArgumentNotInBoundsException;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.ExceptionObject;
import Exceptions.InvalidArgumentException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by user on 13/05/2017.
 */
public class UserRequestHandler {
    public static String serviceURI;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    private SessionStorage seStorage;

    public UserRequestHandler() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.seStorage = SessionStorage.getInstance();
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION_ID", seStorage.getSessionId());
        HttpEntity<ClientUserProfile> request = new HttpEntity<>(userProfile,headers);


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

    public ClientUserProfile requestUserProfileEntity(String username) throws InvalidArgumentException {
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

    public void requestUserTransaction(String username, ClientTransactionRequest transaction) throws EntityDoesNotExistsException, ArgumentNotInBoundsException, InvalidArgumentException {
        String addr = serviceURI + "/" + username + "/balance";
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION_ID", seStorage.getSessionId());
        HttpEntity<ClientTransactionRequest> request = new HttpEntity<>(transaction, headers);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(addr, HttpMethod.PUT, request, ResponseMessage.class);
        } catch (HttpStatusCodeException e) {
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

            else if (e.getStatusCode() == HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE) {
                throw new ArgumentNotInBoundsException(exceptionObj.getMessage());
            }

            else {
                throw new InvalidArgumentException(exceptionObj.getMessage());
            }
        }
    }
}
