package Server.service;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Server.domain.system.GameCenter;
import javafx.util.Pair;

import java.util.List;

import static Server.service.TexasHoldemService.verifyStrings;
/**
 * Created by RonenB on 5/31/2017.
 */
public class StatisticsService {
    private GameCenter gameCenter;

    public StatisticsService(GameCenter gameCenter) {
        this.gameCenter = gameCenter;
    }

    public double getUserAvgNetoProfit(String userName) throws InvalidArgumentException, EntityDoesNotExistsException {
        verifyStrings(userName);
        return gameCenter.getUser(userName).getAvgNetoProfit();
    }

    public double getUserAvgGrossProfit(String userName) throws EntityDoesNotExistsException, InvalidArgumentException {
        verifyStrings(userName);
        return gameCenter.getUser(userName).getAvgGrossProfit();
    }

    public List<Pair<String, Integer>> getTop20UsersByGrossProfit(){
        return gameCenter.getTop20UsersByGrossProfit();
    }

    public List<Pair<String, Integer>> getTop20UsersByHighestCashGain(){
        return gameCenter.getTop20UsersByHighestCashGain();
    }

    public List<Pair<String, Integer>> getTop20UsersByNumOfGamesPlayed(){
        return gameCenter.getTop20UsersByNumOfGamesPlayed();
    }
}