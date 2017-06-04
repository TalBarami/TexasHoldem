package Client.domain.callbacks;

import NotificationMessages.ChatNotification;

/**
 * Created by User on 03/06/2017.
 */
public interface ChatUpdateCallback {
    void execute(ChatNotification chatNotification);
}
