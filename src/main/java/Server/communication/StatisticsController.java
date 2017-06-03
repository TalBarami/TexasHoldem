package Server.communication;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import MutualJsonObjects.ResponseMessage;
import Server.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by RonenB on 6/3/2017.
 */
@CrossOrigin
@RestController
public class StatisticsController {
    private StatisticsService service;

    @Autowired
    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @RequestMapping(method = GET, value="/statistics/{username}/avgNeto")
    public ResponseMessage getUserAvgNetoProfit(@PathVariable("username") String userName) throws EntityDoesNotExistsException, InvalidArgumentException {
        double avgNeto=service.getUserAvgNetoProfit(userName);
        return new ResponseMessage<>("Average cash gain for user retrieved successfully", avgNeto);
    }

    @RequestMapping(method = GET, value="/statistics/{username}/avgGross")
    public ResponseMessage getUserAvgGrossProfit(@PathVariable("username") String userName) throws EntityDoesNotExistsException, InvalidArgumentException {
        double avgGross=service.getUserAvgGrossProfit(userName);
        return new ResponseMessage<>("Average cash gain for user retrieved successfully", avgGross);
    }

}