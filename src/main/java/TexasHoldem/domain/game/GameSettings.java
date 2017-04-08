package TexasHoldem.domain.game;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by RonenB on 4/5/2017.
 */
public class GameSettings {
    public enum GamePolicy {
        LIMIT, NOLIMIT, POTLIMIT
    }

    private GamePolicy gameType;
    private double minBet;
    private double buyInPolicy;
    private int chipPolicy;
    private Pair<Integer,Integer> playerRange;
    private boolean acceptSpectating;

    public GameSettings(GamePolicy policy, double minBet, double buyInPolicy, int chipPolicy, int minPlyerAmount, int maxPlayerAmount, boolean specAccept){
        this.gameType=policy;
        this.minBet=minBet;
        this.buyInPolicy=buyInPolicy;
        this.chipPolicy=chipPolicy;
        playerRange=Pair.of(minPlyerAmount,maxPlayerAmount);
        this.acceptSpectating=specAccept;
    }

    public GamePolicy getGameType() {return gameType;}

    public void setGameType(GamePolicy gameType) {this.gameType = gameType;}

    public double getMinBet() {return minBet;}

    public void setMinBet(double minBet) {this.minBet = minBet;}

    public double getBuyInPolicy() {return buyInPolicy;}

    public void setBuyInPolicy(double buyInPolicy) {this.buyInPolicy = buyInPolicy;}

    public int getChipPolicy() {return chipPolicy;}

    public void setChipPolicy(int chipPolicy) {this.chipPolicy = chipPolicy;}

    public Pair<Integer, Integer> getPlayerRange() {return playerRange;}

    public void setPlayerRange(Pair<Integer, Integer> playerRange) {this.playerRange = playerRange;}

    public boolean isAcceptSpectating() {return acceptSpectating;}

    public void setAcceptSpectating(boolean acceptSpectating) {this.acceptSpectating = acceptSpectating;}
}
