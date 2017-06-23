package MutualJsonObjects;

import java.util.List;

/**
 * Created by user on 13/05/2017.
 */
public class ClientGameDetails {
    private String username;
    private String name;
    private int policyType;
    private int policyLimitAmount;
    private int minimumBet;
    private int buyInAmount;
    private int chipPolicyAmount;
    private int minimumPlayersAmount;
    private int maximumPlayersAmount;
    private boolean isSpectateValid;
    private List<ClientPlayer> playerList;
    private boolean isRunning;
    private boolean isArchived;

    public ClientGameDetails(String username, String name, int policyType, int policyLimitAmount, int minimumBet, int buyInAmount, int chipPolicyAmount, int minimumPlayersAmount, int maximumPlayersAmount, boolean isSpectateValid, List<ClientPlayer> playerList, boolean isRunning, boolean isArchived) {
        this.username = username;
        this.name = name;
        this.policyType = policyType;
        this.policyLimitAmount = policyLimitAmount;
        this.minimumBet = minimumBet;
        this.buyInAmount = buyInAmount;
        this.chipPolicyAmount = chipPolicyAmount;
        this.minimumPlayersAmount = minimumPlayersAmount;
        this.maximumPlayersAmount = maximumPlayersAmount;
        this.isSpectateValid = isSpectateValid;
        this.playerList = playerList;
        this.isRunning = isRunning;
        this.isArchived = isArchived;
    }

    public ClientGameDetails() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPolicyType() {
        return policyType;
    }

    public void setPolicyType(int policyType) {
        this.policyType = policyType;
    }

    public int getPolicyLimitAmount() {
        return policyLimitAmount;
    }

    public void setPolicyLimitAmount(int policyLimitAmount) {
        this.policyLimitAmount = policyLimitAmount;
    }

    public int getMinimumBet() {
        return minimumBet;
    }

    public void setMinimumBet(int minimumBet) {
        this.minimumBet = minimumBet;
    }

    public int getBuyInAmount() {
        return buyInAmount;
    }

    public void setBuyInAmount(int buyInAmount) {
        this.buyInAmount = buyInAmount;
    }

    public int getChipPolicyAmount() {
        return chipPolicyAmount;
    }

    public void setChipPolicyAmount(int chipPolicyAmount) {
        this.chipPolicyAmount = chipPolicyAmount;
    }

    public int getMinimumPlayersAmount() {
        return minimumPlayersAmount;
    }

    public void setMinimumPlayersAmount(int minimumPlayersAmount) {
        this.minimumPlayersAmount = minimumPlayersAmount;
    }

    public int getMaximumPlayersAmount() {
        return maximumPlayersAmount;
    }

    public void setMaximumPlayersAmount(int maximumPlayersAmount) {
        this.maximumPlayersAmount = maximumPlayersAmount;
    }

    public boolean isSpectateValid() {
        return isSpectateValid;
    }

    public void setSpectateValid(boolean spectateValid) {
        isSpectateValid = spectateValid;
    }

    public List<ClientPlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<ClientPlayer> playerList) {
        this.playerList = playerList;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    @Override
    public String toString() {
        return "ClientGameDetails{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", policyType=" + policyType +
                ", policyLimitAmount=" + policyLimitAmount +
                ", minimumBet=" + minimumBet +
                ", buyInAmount=" + buyInAmount +
                ", chipPolicyAmount=" + chipPolicyAmount +
                ", minimumPlayersAmount=" + minimumPlayersAmount +
                ", maximumPlayersAmount=" + maximumPlayersAmount +
                ", isSpectateValid=" + isSpectateValid +
                ", playerList=" + playerList +
                '}';
    }
}
