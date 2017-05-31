package TexasHoldem.service;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.system.GameCenter;
import TexasHoldem.domain.user.User;

import java.util.List;

import static TexasHoldem.service.TexasHoldemService.verifyStrings;
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

    public List<User> getTop20UsersByGrossProfit(){
        return gameCenter.getTop20UsersByGrossProfit();
    }

    public List<User> getTop20UsersByHighestCashGain(){
        return gameCenter.getTop20UsersByHighestCashGain();
    }

    public List<User> getTop20UsersByNumOfGamesPlayed(){
        return gameCenter.getTop20UsersByNumOfGamesPlayed();
    }
}