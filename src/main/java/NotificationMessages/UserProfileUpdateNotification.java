package NotificationMessages;

import MutualJsonObjects.ClientUserProfile;

/**
 * Created by rotemwald on 02/06/17.
 */
public class UserProfileUpdateNotification extends Notification {
    private ClientUserProfile clientUserProfile;

    public UserProfileUpdateNotification(String recipientUserName, ClientUserProfile clientUserProfile) {
        super(recipientUserName);
        this.clientUserProfile = clientUserProfile;
    }

    public UserProfileUpdateNotification() {
    }

    public ClientUserProfile getClientUserProfile() {
        return clientUserProfile;
    }

    public void setClientUserProfile(ClientUserProfile clientUserProfile) {
        this.clientUserProfile = clientUserProfile;
    }

    @Override
    public String toString() {
        return "UserProfileUpdateNotification{" +
                "clientUserProfile=" + clientUserProfile +
                '}';
    }
}
