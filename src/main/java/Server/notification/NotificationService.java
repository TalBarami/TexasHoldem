package Server.notification;

import Enumerations.Move;
import MutualJsonObjects.ClientCard;
import MutualJsonObjects.ClientGameDetails;
import MutualJsonObjects.ClientPlayer;
import MutualJsonObjects.ClientUserProfile;
import NotificationMessages.*;
import Server.SpringApplicationContext;
import Server.communication.converters.CardClientCardConverter;
import Server.communication.converters.GameClientGameDetailsConverter;
import Server.communication.converters.PlayerClientPlayerConverter;
import Server.domain.events.chatEvents.MessageEvent;
import Server.domain.events.chatEvents.WhisperEvent;
import Server.domain.game.Game;
import Server.domain.game.GameActions;
import Server.domain.game.Round;
import Server.domain.game.card.Card;
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
        Object bean = SpringApplicationContext.getBean("MessageSender");

        if (bean != null) {
            messageSender = (MessageSender) bean;
        }
        else {
            messageSender = null;
        }
    }

    public static NotificationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationService();
        }

        return INSTANCE;
    }

    public void sendPlayMoveNotification(String gameName, Player player, List<GameActions> turnActions) {
        if (messageSender != null) {
            String userName = player.getUser().getUsername();
            List<Move> moveList = new LinkedList<>();

            for (GameActions action : turnActions) {
                if (action == GameActions.CHECK) {
                    moveList.add(Move.CHECK);
                } else if (action == GameActions.RAISE) {
                    moveList.add(Move.RAISE);
                } else if (action == GameActions.CALL) {
                    moveList.add(Move.CALL);
                } else {
                    moveList.add(Move.FOLD);
                }
            }

            PlayMoveNotification moveNotification = new PlayMoveNotification(gameName, userName, moveList);
            messageSender.sendPlayMoveNotification(moveNotification);
        }
    }

    public void sendMessageNotification(Participant participant, MessageEvent event) {
        if (messageSender != null) {
            String userName = participant.getUser().getUsername();
            String senderUserName = event.getCreatorUserName();
            String messageContent = event.getContent().getContent();
            String gameName = event.getGameName();

            ChatNotification msgNotification = new ChatNotification(userName, senderUserName, messageContent, gameName, false);
            messageSender.sendMessageNotification(msgNotification);
        }
    }

    public void sendWhisperNotification(WhisperEvent event) {
        if (messageSender != null) {
            String recipientUserName = event.getParticipantToSendTo().getUser().getUsername();
            String senderUserName = event.getCreatorUserName();
            String msgContent = event.getContent().getContent();
            String gameName = event.getGameName();

            ChatNotification msgNotification = new ChatNotification(recipientUserName, senderUserName, msgContent, gameName, true);
            messageSender.sendMessageNotification(msgNotification);
        }
    }

    public void sendUserProfileUpdateNotification(User user) {
        if (messageSender != null) {
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

    public void sendRoundUpdateNotification(Round round) {
        if (messageSender != null) {
            String gameName = round.getGameSettings().getName();
            int currentPotSize = round.getPotAmount();
            String currentPlayerName = round.getCurrentPlayer().getUser().getUsername();

            List<ClientPlayer> currentPlayers = new LinkedList<>();
            for (Player p : round.getActivePlayers()) {
                currentPlayers.add(PlayerClientPlayerConverter.convert(p));
            }

            List<ClientCard> currentOpenedCards = new LinkedList<>();
            for (Card c : round.getOpenedCards()) {
                currentOpenedCards.add(CardClientCardConverter.convert(c));
            }

            for (Player p : round.getActivePlayers()) {
                String userName = p.getUser().getUsername();

                if (round.isRoundActive()) {
                    RoundUpdateNotification notification = new RoundUpdateNotification(userName, gameName, currentPotSize, currentPlayerName, currentPlayers, currentOpenedCards, null, false);
                    messageSender.sendRoundUpdateNotification(notification);
                }

                else {
                    List<ClientPlayer> winnerList = new LinkedList<>();
                    for (Player p1 : round.getWinnerList()) {
                        winnerList.add(PlayerClientPlayerConverter.convert(p1));
                    }

                    RoundUpdateNotification notification = new RoundUpdateNotification(userName, gameName, currentPotSize, currentPlayerName, currentPlayers, currentOpenedCards, winnerList, true);
                    messageSender.sendRoundUpdateNotification(notification);
                }
            }
        }
    }

    public void sendGameUpdateNotification(GameActions action, String gameActionInitiator, Game game) {
        if (messageSender != null) {
            String gameName = game.getName();
            ClientGameDetails gameDetails = GameClientGameDetailsConverter.convert(game);

            for (Player p : game.getPlayers()) {
                String userName = p.getUser().getUsername();
                GameUpdateNotification notification = new GameUpdateNotification(userName, gameName, action, gameActionInitiator, gameDetails);
                messageSender.sendGameUpdateNotification(notification);
            }
        }
    }
}
