package Server.notification;

import Enumerations.Move;
import MutualJsonObjects.ClientUserProfile;
import NotificationMessages.MessageNotification;
import NotificationMessages.PlayMoveNotification;
import NotificationMessages.UserProfileUpdateNotification;
import Server.SpringApplicationContext;
import Server.domain.events.chatEvents.MessageEvent;
import Server.domain.events.chatEvents.WhisperEvent;
import Server.domain.game.GameActions;
import Server.domain.game.participants.Participant;
import Server.domain.game.participants.Player;
import Server.domain.user.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rotemwald on 02/06/17.
 */
public class NotificationService {
    private static NotificationService INSTANCE = null;
    private MessageSender messageSender;

    private NotificationService() {
        messageSender = (MessageSender) SpringApplicationContext.getBean("MessageSender");
    }

    public static NotificationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationService();
        }

        return INSTANCE;
    }

    public void sendPlayMoveNotification(Player player, List<GameActions> turnActions) {
        String userName = player.getUser().getUsername();
        List<Move> moveList = new LinkedList<>();

        for (GameActions action : turnActions) {
            if (action == GameActions.CHECK) {
                moveList.add(Move.CHECK);
            }

            else if (action == GameActions.RAISE) {
                moveList.add(Move.RAISE);
            }

            else if (action == GameActions.CALL) {
                moveList.add(Move.CALL);
            }

            else {
                moveList.add(Move.FOLD);
            }
        }

        PlayMoveNotification moveNotification = new PlayMoveNotification(userName, moveList);
        messageSender.sendPlayMoveNotification(moveNotification);
    }

    public void sendMessageNotification(Participant participant, MessageEvent event) {
        String userName = participant.getUser().getUsername();
        String senderUserName = event.getEventInitiator().getUser().getUsername();
        String messageContent = event.getContent().getContent();

        MessageNotification msgNotification = new MessageNotification(userName, senderUserName, messageContent);
        messageSender.sendMessageNotification(msgNotification);
    }

    public void sendWhisperNotification(WhisperEvent event) {
        String recipientUserName = event.getParticipantToSendTo().getUser().getUsername();
        String senderUserName = event.getEventInitiator().getUser().getUsername();
        String msgContent = event.getContent().getContent();

        MessageNotification msgNotification = new MessageNotification(recipientUserName, senderUserName, msgContent);
        messageSender.sendMessageNotification(msgNotification);
    }

    public void sendUserProfileUpdateNotification(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        int dayOfBirth = user.getDateOfBirth().getDayOfMonth();
        int monthOfBirth = user.getDateOfBirth().getMonthValue();
        int yearOfBirth = user.getDateOfBirth().getYear();
        int balance = user.getBalance();
        int currLeague = user.getCurrLeague();
        int numOfGamesPlayed = user.getNumOfGamesPlayed();
        int amountEarnedInLeague = user.getAmountEarnedInLeague();

        ClientUserProfile profile = new ClientUserProfile(username, password, email, dayOfBirth, monthOfBirth, yearOfBirth, balance, currLeague, numOfGamesPlayed, amountEarnedInLeague);
        UserProfileUpdateNotification profileNotification = new UserProfileUpdateNotification(username, profile);

        messageSender.sendUserProfileUpdateNotification(profileNotification);
    }
}
