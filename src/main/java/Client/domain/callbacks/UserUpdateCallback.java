package Client.domain.callbacks;

import MutualJsonObjects.ClientUserProfile;

/**
 * Created by User on 03/06/2017.
 */
public interface UserUpdateCallback {
    void execute(ClientUserProfile profile);
}
