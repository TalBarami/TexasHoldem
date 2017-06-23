package Server.domain.game;

import Enumerations.GamePolicy;
import org.apache.commons.lang3.tuple.Pair;

public class GameSettings implements java.io.Serializable {
    private String name;
    private GamePolicy gameType;
    private int gameTypeLimit;
    private int minBet;
    private int buyInPolicy;
    private int chipPolicy;
    private int minAmountPlayers;
    private int maxAmountPlayers;
    private boolean acceptSpectating;
    private int leagueCriteria;

    public GameSettings(String name, GamePolicy policy,int limit, int minBet, int buyInPolicy, int chipPolicy, int minPlyerAmount, int maxPlayerAmount, boolean specAccept){
        this.name = name;
        this.gameType = policy;
        this.gameTypeLimit = limit;
        this.minBet = minBet;
        this.buyInPolicy = buyInPolicy;
        this.chipPolicy = chipPolicy;
        this.minAmountPlayers = minPlyerAmount;
        this.maxAmountPlayers = maxPlayerAmount;
        this.acceptSpectating = specAccept;
    }

    private GameSettings(){}

    public String getName(){
        return this.name;
    }

    public GamePolicy getGameType() {return gameType;}

    public int getGameTypeLimit() {
        return gameTypeLimit;
    }

    public int getMinBet() {return minBet;}

    public int getBuyInPolicy() {return buyInPolicy;}

    public int getChipPolicy() {return chipPolicy;}

    public Pair<Integer, Integer> getPlayerRange() {return Pair.of(minAmountPlayers, maxAmountPlayers);}

    public boolean isAcceptSpectating() {return acceptSpectating;}

    public int getLeagueCriteria() {
        return leagueCriteria;
    }

    public void setLeagueCriteria(int leagueCriteria) {
        this.leagueCriteria = leagueCriteria;
    }

    public boolean tournamentMode(){
        return getChipPolicy()!= 0;
    }

    public void setMaximalPlayers(int amount){
        this.maxAmountPlayers = amount;
    }

    public void setMinimalPlayers(int amount){
        this.minAmountPlayers = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameType(GamePolicy gameType) {
        this.gameType = gameType;
    }

    public void setGameTypeLimit(int gameTypeLimit) {
        this.gameTypeLimit = gameTypeLimit;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }

    public void setBuyInPolicy(int buyInPolicy) {
        this.buyInPolicy = buyInPolicy;
    }

    public void setChipPolicy(int chipPolicy) {
        this.chipPolicy = chipPolicy;
    }

    public void setAcceptSpectating(boolean acceptSpectating) {
        this.acceptSpectating = acceptSpectating;
    }

    public int getMinAmountPlayers() {
        return minAmountPlayers;
    }

    public int getMaxAmountPlayers() {
        return maxAmountPlayers;
    }
}
