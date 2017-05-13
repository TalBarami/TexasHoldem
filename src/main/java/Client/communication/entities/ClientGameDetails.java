package Client.communication.entities;

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
}
