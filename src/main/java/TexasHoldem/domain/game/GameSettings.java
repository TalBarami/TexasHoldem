package TexasHoldem.domain.game;

import org.apache.commons.lang3.tuple.Pair;


public class GameSettings {
    public enum GamePolicy {
        LIMIT, NOLIMIT, POTLIMIT
    }

    private GamePolicy gameType;
    private int minBet;
    private int buyInPolicy;
    private int chipPolicy;
    private Pair<Integer,Integer> playerRange;
    private boolean acceptSpectating;

    public GameSettings(GamePolicy policy, int minBet, int buyInPolicy, int chipPolicy, int minPlyerAmount, int maxPlayerAmount, boolean specAccept){
        this.gameType=policy;
        this.minBet=minBet;
        this.buyInPolicy=buyInPolicy;
        this.chipPolicy=chipPolicy;
        playerRange=Pair.of(minPlyerAmount,maxPlayerAmount);
        this.acceptSpectating=specAccept;
    }

    private GameSettings(){

    }

    public GamePolicy getGameType() {return gameType;}

    public int getMinBet() {return minBet;}

    public int getBuyInPolicy() {return buyInPolicy;}

    public int getChipPolicy() {return chipPolicy;}

    public Pair<Integer, Integer> getPlayerRange() {return playerRange;}

    public boolean isAcceptSpectating() {return acceptSpectating;}
}
