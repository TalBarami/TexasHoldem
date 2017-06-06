package Client.domain.callbacks;

import NotificationMessages.GameUpdateNotification;

/**
 * Created by User on 06/06/2017.
 */
public interface GameUpdateCallback {
    void execute(GameUpdateNotification gameUpdateNotification);
}
