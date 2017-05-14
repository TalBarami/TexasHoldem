package Client.domain;

import Client.communication.entities.ClientGameDetails;

import java.util.List;

/**
 * Created by User on 14/05/2017.
 */
public interface SearchPolicy {
    List<ClientGameDetails> find(String value);

}
