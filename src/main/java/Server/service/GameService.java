package Server.service;

import Enumerations.GamePolicy;
import Exceptions.*;
import Server.domain.events.chatEvents.MessageEvent;
import Server.domain.events.chatEvents.WhisperEvent;
import Server.domain.events.gameFlowEvents.MoveEvent;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.game.*;
import Server.domain.game.chat.Message;
import Server.domain.game.participants.Participant;
import Server.domain.game.participants.Player;
import Server.domain.game.participants.Spectator;
import Server.domain.system.GameCenter;
import Server.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Server.service.TexasHoldemService.verifyPositiveNumbers;
import static Server.service.TexasHoldemService.verifyStrings;

/**
 * Created by Tal on 05/05/2017.
 */
public class GameService {
    private static Logger logger = LoggerFactory.getLogger(GameService.class);
    private GameCenter gameCenter;

    public GameService(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public void startGame(String username,String gameName) throws GameException {
        logger.info("New start game request with the following info [user name: {}, game name: {}]", username, gameName);
        verifyStrings(username,gameName);
        gameCenter.startGame(username,gameName);
    }


    public void createGame(String creatorUsername, String gameName, GamePolicy policy, int limit, int minBet, int buyInPolicy, int chipPolicy,
                           int minPlayerAmount, int maxPlayerAmount, boolean specAccept) throws NoBalanceForBuyInException, InvalidArgumentException, ArgumentNotInBoundsException, EntityDoesNotExistsException {
        logger.info("New create game request with the following info [user name: {}, game name: {}]", creatorUsername, gameName);
        verifyStrings(creatorUsername, gameName);
        verifyPositiveNumbers(limit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount);
        gameCenter.createGame(creatorUsername, new GameSettings(gameName, policy, limit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount, specAccept));
    }

    public void joinGame(String username, String gameName) throws GameException {
        logger.info("New join game request with the following info [user name: {}, game name: {}]", username, gameName);
        verifyStrings(username, gameName);
        gameCenter.joinGame(username, gameName, false);
    }

    public void spectateGame(String username, String gameName) throws GameException {
        logger.info("New spectate game request with the following info [user name: {}, game name: {}]", username, gameName);
        verifyStrings(username, gameName);
        gameCenter.joinGame(username, gameName, true);
    }

    public void leaveGame(String username, String gameName) throws GameException {
        logger.info("New leave game request with the following info [user name: {}, game name: {}]", username, gameName);
        verifyStrings(username, gameName);
        gameCenter.leaveGame(username, gameName);
    }

    public void playCall(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("New call move request with the following info [user name: {}, game name: {}]", username, gameName);
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(username, GameActions.CALL, 0, gameName)));
    }

    public void playCheck(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("New check move request with the following info [user name: {}, game name: {}]", username, gameName);
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(username, GameActions.CHECK, 0, gameName)));
    }

    public void playFold(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("New fold move request with the following info [user name: {}, game name: {}]", username, gameName);
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(username, GameActions.FOLD, 0, gameName)));
    }

    public void playRaise(String username, String gameName, int amount) throws InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("New raise move request with the following info [user name: {}, game name: {}, amount: {}]", username, gameName, amount);
        verifyStrings(username,gameName);
        verifyPositiveNumbers(amount);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(username, GameActions.RAISE, amount, gameName)));
    }

    /*
    public List<GameEvent> replayGame(String gameName) throws EntityDoesNotExistsException {
        Game game = gameCenter.getGameByName(gameName);

        return Stream.concat(
                    game.getGameEvents().stream(),
                    game.getRounds().stream()
                        .flatMap(r -> r.getEvents().stream()))
                .sorted(Comparator.comparing(GameEvent::getEventTime))
                .collect(Collectors.toList());
    }
    */

    public void sendMessage(String username, String gameName, String content) throws InvalidArgumentException, EntityDoesNotExistsException {
        logger.info("New send message request with the following info [from user name: {}, game  name: {}, content: {}]", username, gameName, content);
        verifyStrings(username,gameName);
        Game game = gameCenter.getGameByName(gameName);
        User user = gameCenter.getUser(username);

        List<Player> allPlayersInGame = new ArrayList<>();
        allPlayersInGame.addAll(game.getPlayers());

        try { //check if he is a player
            Player player = allPlayersInGame.stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst().get();
            game.handleMessageFromPlayer(new MessageEvent(username, new Message(content), gameName));
        }catch (NoSuchElementException e){
            List<Spectator> allSpectators = new ArrayList<>();
            allSpectators.addAll(game.getSpectators());
            Spectator spectator = allSpectators.stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst().get();
            game.handleMessageFromSpectator(new MessageEvent(username, new Message(content), gameName));
        }
    }

    public void sendWhisper(String username, String gameName, String content, String userNameToSend) throws InvalidArgumentException, EntityDoesNotExistsException, ArgumentNotInBoundsException {
        logger.info("New send whisper message request with the following info [from user name: {}, to user name: {}, game name: {}, content: {}]", username, userNameToSend, gameName, content);
        verifyStrings(username,gameName);
        Game game = gameCenter.getGameByName(gameName);
        User user = gameCenter.getUser(username);
        User userToSend = gameCenter.getUser(userNameToSend);

        List<Participant> allParInGame = new ArrayList<>();
        allParInGame.addAll(game.getPlayers());
        allParInGame.addAll(game.getSpectators());
        Participant parToSendTo = allParInGame.stream().filter(p -> p.getUser().getUsername().equals(userNameToSend)).findFirst().get();

        List<Player> allPlayersInGame = new ArrayList<>();
        allPlayersInGame.addAll(game.getPlayers());

        try { //check the sender is a player
            Player player = allPlayersInGame.stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst().get();
            game.handleWhisperFromPlayer(new WhisperEvent(username, new Message(content), parToSendTo, gameName));
        }catch (NoSuchElementException e){
            List<Spectator> allSpectators = new ArrayList<>();
            allSpectators.addAll(game.getSpectators());
            Spectator spectator = allSpectators.stream().filter(p -> p.getUser().getUsername().equals(user.getUsername())).findFirst().get();
            game.handleWhisperFromSpectator(spectator, new WhisperEvent(username, new Message(content), parToSendTo, gameName));
        }
    }

    public List<GameEvent> replayGame(String gameName) throws EntityDoesNotExistsException {
        logger.info("New replay request with the following info [game name: {}]", gameName);
        List<GameEvent> gameEvents = gameCenter.getAllGameEvents(gameName);
//        List<MoveEvent> moveEvents = gameCenter.getAllMoveEvents(gameName);

        if (gameEvents.isEmpty())
            throw new EntityDoesNotExistsException(String.format("There is no archived game with the name '%s'.",gameName));

        return gameEvents.stream()
                .sorted(Comparator.comparing(GameEvent::getEventTime))
                .collect(Collectors.toList());
    }

}
