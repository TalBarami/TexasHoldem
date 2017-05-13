package TexasHoldem.service;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.domain.events.GameEvent;
import TexasHoldem.domain.events.MoveEvent;
import TexasHoldem.domain.game.*;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.system.GameCenter;
import TexasHoldem.domain.user.User;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static TexasHoldem.service.TexasHoldemService.verifyObjects;
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
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(currentRound, player, GameActions.CALL, 0)));
    }

    public void playCheck(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().equals(user)).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(currentRound, player, GameActions.CHECK, 0)));
    }

    public void playFold(String username, String gameName) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().equals(user)).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(currentRound, player, GameActions.FOLD, 0)));
    }

    public void playRaise(String username, String gameName, int amount) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(username,gameName);
        Round currentRound = gameCenter.getGameByName(gameName).getLastRound();
        User user = gameCenter.getUser(username);
        Optional<Player> optPlayer = currentRound.getActivePlayers().stream().filter(p -> p.getUser().equals(user)).findFirst();
        optPlayer.ifPresent(player -> currentRound.playTurnOfPlayer(new MoveEvent(currentRound, player, GameActions.RAISE, amount)));
    }

    public List<GameEvent> replayGame(String gameName) throws EntityDoesNotExistsException {
        Game game = gameCenter.getGameByName(gameName);

        return Stream.concat(
                    game.getGameEvents().stream(),
                    game.getRounds().stream()
                        .flatMap(r -> r.getEvents().stream()))
                .sorted(Comparator.comparing(GameEvent::getEventTime))
                .collect(Collectors.toList());
    }
}
