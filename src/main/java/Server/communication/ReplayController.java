package Server.communication;

import Exceptions.EntityDoesNotExistsException;
import MutualJsonObjects.ClientGameEvent;
import MutualJsonObjects.ResponseMessage;
import Server.communication.converters.GameEventClientGameEvent;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.service.GameService;
import Server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by rotemwald on 17/06/17.
 */
@CrossOrigin
@RestController
public class ReplayController {
    private GameService gameService;
    private SearchService searchService;

    @Autowired
    public ReplayController(GameService gameService, SearchService searchService) {
        this.gameService = gameService;
        this.searchService = searchService;
    }

    @RequestMapping(method=GET, value="/replay/{roomname}")
    public ResponseMessage getReplay(@PathVariable("roomname") String roomName) throws EntityDoesNotExistsException {
        List<GameEvent> replays = gameService.replayGame(roomName);
        List<ClientGameEvent> replaysToSend = new LinkedList<>();

        for (GameEvent e : replays) {
            ClientGameEvent ce = GameEventClientGameEvent.convert(e);
            replaysToSend.add(ce);
        }

        return new ResponseMessage("Replay found successfully", replaysToSend);
    }
}
