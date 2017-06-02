package TexasHoldem.notification;

import Enumerations.Move;
import NotificationMessages.MessageNotification;
import NotificationMessages.Notification;
import NotificationMessages.PlayMoveNotification;
import TexasHoldem.SpringApplicationContext;
import TexasHoldem.domain.events.chatEvents.MessageEvent;
import TexasHoldem.domain.events.chatEvents.WhisperEvent;
import TexasHoldem.domain.game.GameActions;
import TexasHoldem.domain.game.participants.Participant;
import TexasHoldem.domain.game.participants.Player;

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

    public void sendPlayTurnNotification(Player player, List<GameActions> turnActions) {
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

        Notification turnNotification = new PlayMoveNotification(userName, moveList);
        messageSender.sendNotification(turnNotification);
    }

    public void sendMessageNotification(Participant participant, MessageEvent event) {
        String userName = participant.getUser().getUsername();
        String senderUserName = event.getEventInitiator().getUser().getUsername();
        String messageContent = event.getContent().getContent();

        Notification msgNotification = new MessageNotification(userName, senderUserName, messageContent);
        messageSender.sendNotification(msgNotification);
    }

    public void sendWhisperNotification(WhisperEvent event) {
        String recipientUserName = event.getParticipantToSendTo().getUser().getUsername();
        String senderUserName = event.getEventInitiator().getUser().getUsername();
        String msgContent = event.getContent().getContent();

        Notification msgNotification = new MessageNotification(recipientUserName, senderUserName, msgContent);
        messageSender.sendNotification(msgNotification);
    }
}
