package Server.communication;

import Exceptions.*;
import MutualJsonObjects.*;
import Server.communication.converters.GameClientGameDetailsConverter;
import Server.domain.game.Game;
import Enumerations.GamePolicy;
import Server.service.GameService;
import Server.service.SearchService;
import Server.service.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by user on 13/05/2017.
 */
@CrossOrigin
@RestController
public class GameController {
    private GameService gameService;
    private SearchService searchService;
    private SessionManager manager;

    @Autowired
    public GameController(GameService gameService, SearchService searchService) {
        this.gameService = gameService;
        this.searchService = searchService;
        this.manager = SessionManager.getInstance();
    }

    @RequestMapping(method=GET, value="/game/{roomname}")
    public ResponseMessage findGame(@PathVariable("roomname") String roomName) throws InvalidArgumentException, EntityDoesNotExistsException {
        Game game = searchService.findGameByName(roomName);
        ClientGameDetails clientGame = GameClientGameDetailsConverter.convert(game);

        return new ResponseMessage("Game found successfully", clientGame);
    }

    @RequestMapping(method=POST, value="/game/{roomname}")
    public ResponseMessage createGame(@PathVariable("roomname") String roomName, @RequestBody ClientGameDetails gameDetails,@RequestHeader("SESSION_ID") String sessionID) throws InvalidArgumentException, NoBalanceForBuyInException, ArgumentNotInBoundsException, EntityDoesNotExistsException {
        String userName = gameDetails.getUsername();
        String gameName = gameDetails.getName();
        manager.validate(userName, sessionID);

        if (!roomName.equals(gameName)) {
            throw new InvalidArgumentException("Room name is not compatible with request data.");
        }

        int policyType = gameDetails.getPolicyType();
        int policyLimitAmount = gameDetails.getPolicyLimitAmount();
        int minimumBet = gameDetails.getMinimumBet();
        int buyInAmount = gameDetails.getBuyInAmount();
        int chipPolicyAmount = gameDetails.getChipPolicyAmount();
        int minimumPlayersAmount = gameDetails.getMinimumPlayersAmount();
        int maximumPlayerAmount = gameDetails.getMaximumPlayersAmount();
        boolean isSpectateValid = gameDetails.isSpectateValid();

        gameService.createGame(userName, gameName, GamePolicy.values()[policyType], policyLimitAmount, minimumBet, buyInAmount, chipPolicyAmount, minimumPlayersAmount, maximumPlayerAmount, isSpectateValid);
        return new ResponseMessage("Game created successfully", null);
    }

    @RequestMapping(method=PUT, value="/game/{roomname}")
    public ResponseMessage handleGameEvent(@PathVariable("roomname") String roomName, @RequestBody ClientGameRequest gameRequest,@RequestHeader("SESSION_ID") String sessionID) throws GameException {
        String gameName = gameRequest.getGameName();
        String userName = gameRequest.getUsername();

        manager.validate(userName, sessionID);

        if (!roomName.equals(gameName)) {
            throw new InvalidArgumentException("Room name is not compatible with request data.");
        }


        int actionToPerform = gameRequest.getAction();
        int amountForAction = gameRequest.getAmount();
        boolean spectateOption = gameRequest.getSpectating();
        String messageContent = gameRequest.getMessage();
        String userNameToSend = gameRequest.getRecipientUserName();

        if (actionToPerform == 0) {
            gameService.playCheck(userName,gameName);
            return new ResponseMessage("Check succeeded", null);
        }

        else if(actionToPerform == 1){
            gameService.playRaise(userName,gameName,amountForAction);
            return new ResponseMessage("Raise succeeded", null);
        }

        else if(actionToPerform == 2){
            gameService.playCall(userName,gameName);
            return new ResponseMessage("Call succeeded", null);
        }

        else if(actionToPerform == 3){
            gameService.playFold(userName,gameName);
            return new ResponseMessage("Fold succeeded", null);
        }

        else if (actionToPerform == 4 && !spectateOption)
        {
            gameService.joinGame(userName,gameName);
            return new ResponseMessage("Join game succeeded", null);
        }

        else if(actionToPerform == 4 && spectateOption){
            gameService.spectateGame(userName,gameName);
            return new ResponseMessage("Spectate game succeeded", null);
        }

        else if(actionToPerform == 5) {
            gameService.startGame(userName,gameName);
            return new ResponseMessage("Start game succeeded", null);
        }
        else
        {
            if (!userNameToSend.isEmpty()) {
                gameService.sendWhisper(userName,gameName,messageContent,userNameToSend);
                return new ResponseMessage("Whisper sent successfully", null);
            }

            else {
                gameService.sendMessage(userName, gameName, messageContent);
                return new ResponseMessage("Message sent successfully", null);
            }
        }
    }

    @RequestMapping(method = DELETE, value = "/game/{roomname}")
    public ResponseMessage leaveGame(@PathVariable("roomname") String roomName, @RequestBody ClientLeaveGameDetails leaveDetails,@RequestHeader("SESSION_ID") String sessionID) throws GameException {
        String gameName = leaveDetails.getGameName();
        String userName = leaveDetails.getUsername();

        manager.validate(userName, sessionID);

        if (!roomName.equals(gameName)) {
            throw new InvalidArgumentException("Room name is not compatible with request data.");
        }

        gameService.leaveGame(userName, gameName);
        return new ResponseMessage("Leave game succeeded", null);
    }

    // TODO :: Find a solution to POST (originally was GET) and also update the fixes in GameRequestHandler
    @RequestMapping(method = POST, value = "/game")
    public ResponseMessage getActiveGames(@RequestBody ClientGamePreferences gamePreferences) throws EntityDoesNotExistsException, InvalidArgumentException {
        String userName = gamePreferences.getUsername();
        List<Game> gameList = null;
        List<ClientGameDetails> clientGameList = new LinkedList<>();

        if (gamePreferences.isDisplayAllAvailableGames()) {
            gameList = searchService.findAvailableGames(userName);
        }

        else {
            if (gamePreferences.isSearchByUsername()) {
                gameList = searchService.findGamesByUsername(gamePreferences.getUsernameToSearch());
            }

            else if (gamePreferences.isSearchByTypePolicy()) {
                gameList = searchService.findGamesByGamePolicy(GamePolicy.values()[gamePreferences.getPolicyNumberToSearch()]);
            }

            else if (gamePreferences.isSearchByBuyIn()) {
                gameList = searchService.findGamesByMaximumBuyIn(gamePreferences.getBuyInAmount());
            }

            else if (gamePreferences.isSearchByChipPolicy()) {
                gameList = searchService.findGamesByChipPolicy(gamePreferences.getChipPolicyAmount());
            }

            else if (gamePreferences.isSearchByMinimumBet()) {
                gameList = searchService.findGamesByMinimumBet(gamePreferences.getMinimumBetAmount());
            }

            else if (gamePreferences.isSearchByMinimumPlayersAmount()) {
                gameList = searchService.findGamesByMinimumPlayers(gamePreferences.getMinimumPlayersAmount());
            }

            else if (gamePreferences.isSearchByMaximumPlayersAmount()) {
                gameList = searchService.findGamesByMaximumPlayers(gamePreferences.getMaximumPlayersAmount());
            }

            else if (gamePreferences.isSearchByPotSize()) {
                gameList = searchService.findGamesByPotSize(gamePreferences.getPotSizeToSearch());
            }

            else if (gamePreferences.isSearchForReplayableGames()) {
                List<String> gameNames = searchService.findArchivedGamesNames();
                List<ClientGameDetails> gameList1 = new LinkedList<>();

                for (String s : gameNames) {
                    ClientGameDetails cgd = new ClientGameDetails();
                    cgd.setName(s);
                    cgd.setArchived(true);
                    gameList1.add(cgd);
                }

                return new ResponseMessage("Games found successfully", gameList1);
            }

            else {
                gameList = searchService.findSpectatableGames();
            }
        }

        convertGamesToClientGames(clientGameList, gameList);
        return new ResponseMessage("Games found successfully", clientGameList);
    }

    private void convertGamesToClientGames(List<ClientGameDetails> clientGameList, List<Game> gameList) {
        for (Game g : gameList) {
            clientGameList.add(GameClientGameDetailsConverter.convert(g));
        }
    }
}
