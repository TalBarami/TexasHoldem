package Client.domain.callbacks;

import NotificationMessages.RoundUpdateNotification;

/**
 * Created by User on 03/06/2017.
 */
public interface GameUpdateCallback {
    void execute(RoundUpdateNotification roundUpdateNotification);
}
