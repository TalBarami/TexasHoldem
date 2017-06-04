package Client.domain.callbacks;

import NotificationMessages.MessageNotification;

/**
 * Created by User on 03/06/2017.
 */
public interface ChatUpdateCallback {
    void execute(MessageNotification messageNotification);
}
