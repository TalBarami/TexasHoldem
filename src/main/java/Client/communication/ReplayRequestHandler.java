package Client.communication;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.ExceptionObject;
import MutualJsonObjects.ClientGameEvent;
import MutualJsonObjects.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Created by rotemwald on 17/06/17.
 */
public class ReplayRequestHandler {
    public static String serviceURI;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public ReplayRequestHandler() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<ClientGameEvent> requestGameReplay(String gameName) throws EntityDoesNotExistsException{
        String addr = serviceURI + "/" + gameName;

        try {
            ResponseEntity<ResponseMessage<List<ClientGameEvent>>> response = restTemplate.exchange(addr, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<List<ClientGameEvent>>>() {});
            return response.getBody().getData();
        } catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();

            ExceptionObject exceptionObj = null;
            try {
                exceptionObj = objectMapper.readValue(responseBody, ExceptionObject.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw new EntityDoesNotExistsException(exceptionObj.getMessage());
        }
    }
}
