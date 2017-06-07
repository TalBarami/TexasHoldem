package Client.communication;

import MutualJsonObjects.ClientUserLoginDetails;
import MutualJsonObjects.ResponseMessage;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.ExceptionObject;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by rotemwald on 14/05/17.
 */
public class SessionRequestHandler {
    public static String serviceURI;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public SessionRequestHandler() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void requestUserLogin(ClientUserLoginDetails loginDetails) throws LoginException, InvalidArgumentException, EntityDoesNotExistsException {
        HttpEntity<ClientUserLoginDetails> request = new HttpEntity<>(loginDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(serviceURI, HttpMethod.POST, request, ResponseMessage.class);
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
            else if (e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new EntityDoesNotExistsException(exceptionObj.getMessage());
            }
            else {
                throw new LoginException(exceptionObj.getMessage());
            }
        }
    }

    public void requestUserLogout(ClientUserLoginDetails logoutDetails) throws InvalidArgumentException {
        HttpEntity<ClientUserLoginDetails> request = new HttpEntity<>(logoutDetails);

        try {
            ResponseEntity<ResponseMessage> response = restTemplate.exchange(serviceURI, HttpMethod.DELETE, request, ResponseMessage.class);
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
