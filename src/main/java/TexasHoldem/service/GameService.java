package TexasHoldem.service;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.domain.events.chatEvents.MessageEvent;
import TexasHoldem.domain.events.chatEvents.WhisperEvent;
import TexasHoldem.domain.events.gameFlowEvents.MoveEvent;
import TexasHoldem.domain.events.gameFlowEvents.GameEvent;
import TexasHoldem.domain.game.*;
import TexasHoldem.domain.game.chat.Message;
import TexasHoldem.domain.game.participants.Participant;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.system.GameCenter;
import TexasHoldem.domain.user.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static TexasHoldem.service.TexasHoldemService.verifyPositiveNumbers;
import static TexasHoldem.service.TexasHoldemService.verifyStrings;

/**
 * Created by Tal on 05/05/2017.
 */
public class GameService {
    private GameCenter gameCenter;

    public GameService(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    public void startGame(String username,String gameName) throws GameException {
        verifyStrings(username,gameName);
        gameCenter.startGame(username,gameName);
    }


    public void createGame(String creatorUsername, String gameName, GamePolicy policy, int limit, int minBet, int buyInPolicy, int chipPolicy,
                           int minPlayerAmount, int maxPlayerAmount, boolean specAccept) throws NoBalanceForBuyInException, InvalidArgumentException, ArgumentNotInBoundsException, EntityDoesNotExistsException {
        verifyStrings(creatorUsername, gameName);
        verifyPositiveNumbers(limit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount);
        gameCenter.createGame(creatorUsername, new GameSettings(gameName, policy, limit, minBet, buyInPolicy, chipPolicy, minPlayerAmount, maxPlayerAmount, specAccept));
    }

    public void joinGame(String username, String gameName, boolean asSpectator) throws GameException {
        verifyStrings(username, gameName);
        gameCenter.joinGame(username, gameName, false);
    }

    public void spectateGame(String username, String gameName, boolean asSpectator) throws GameException {
        verifyStrings(username, gameName);
        gameCenter.joinGame(username, gameName, true);
    }

    public void leaveGame(String username, String gameName) throws GameException {
        verifyStrings(username, gameName);
        gameCenter.leaveGame(username, gameName);
    }

    public void playCall(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().equals(user)).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(player, GameActions.CALL, 0)));
    }

    public void playCheck(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().equals(user)).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(player, GameActions.CHECK, 0)));
    }

    public void playFold(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().equals(user)).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(player, GameActions.FOLD, 0)));
    }

    public void playRaise(String username, String gameName, int amount) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        verifyPositiveNumbers(amount);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().equals(user)).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(player, GameActions.RAISE, amount)));
    }

    public void sendMessage(String username, String gameName, String content) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        Game game = gameCenter.getGameByName(gameName);
        User user = gameCenter.getUser(username);
        List<Participant> allParInGame = new ArrayList<>();
        allParInGame.addAll(game.getPlayers());
        allParInGame.addAll(game.getSpectators());
        Participant participant = allParInGame.stream().filter(p -> p.getUser().equals(user)).findFirst().get();
        game.handleMessageFromParticipant(new MessageEvent(participant, new Message(content)));
    }

    public void sendWhisper(String username, String gameName, String content, String userNameToSend) throws InvalidArgumentException, EntityDoesNotExistsException, ArgumentNotInBoundsException {
        verifyStrings(username,gameName);
        Game game = gameCenter.getGameByName(gameName);
        User user = gameCenter.getUser(username);
        User userToSend = gameCenter.getUser(userNameToSend);
        List<Participant> allParInGame = new ArrayList<>();
        allParInGame.addAll(game.getPlayers());
        allParInGame.addAll(game.getSpectators());
        Participant participant = allParInGame.stream().filter(p -> p.getUser().equals(user)).findFirst().get();
        Participant parToSendTo = allParInGame.stream().filter(p -> p.getUser().equals(userNameToSend)).findFirst().get();
        game.handleWhisperFromParticipant(new WhisperEvent(participant, new Message(content), parToSendTo));
    }
  
    public List<GameEvent> replayGame(String gameName) throws EntityDoesNotExistsException {
        Game game = gameCenter.getArchivedGames().stream().filter(g -> g.getName().equals(gameName)).findFirst().orElse(null);
        if(game==null)
            throw new EntityDoesNotExistsException(String.format("There is no archived game with the name '%s'.",gameName));

        return Stream.concat(
                    game.getGameEvents().stream(),
                    game.getRounds().stream()
                        .flatMap(r -> r.getEvents().stream()))
                .sorted(Comparator.comparing(GameEvent::getEventTime))
                .collect(Collectors.toList());
    }
}
