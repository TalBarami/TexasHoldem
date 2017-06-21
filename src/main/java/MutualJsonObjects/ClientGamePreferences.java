package MutualJsonObjects;

/**
 * Created by user on 13/05/2017.
 */
public class ClientGamePreferences {
    private String username;
    private boolean displayAllAvailableGames;

    private boolean searchByUsername;
    private String usernameToSearch;
    private boolean searchByTypePolicy;
    private int policyNumberToSearch;
    private boolean searchByBuyIn;
    private int buyInAmount;
    private boolean searchByChipPolicy;
    private int chipPolicyAmount;
    private boolean searchByMinimumBet;
    private int minimumBetAmount;
    private boolean searchByMinimumPlayersAmount;
    private int minimumPlayersAmount;
    private boolean searchByMaximumPlayersAmount;
    private int maximumPlayersAmount;
    private boolean serachBySpectatingAvailable;
    private boolean searchByPotSize;
    private int potSizeToSearch;
    private boolean searchForReplayableGames;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDisplayAllAvailableGames() {
        return displayAllAvailableGames;
    }

    public void setDisplayAllAvailableGames(boolean displayAllAvailableGames) {
        this.displayAllAvailableGames = displayAllAvailableGames;
    }

    public boolean isSearchByUsername() {
        return searchByUsername;
    }

    public void setSearchByUsername(boolean searchByUsername) {
        this.searchByUsername = searchByUsername;
    }

    public String getUsernameToSearch() {
        return usernameToSearch;
    }

    public void setUsernameToSearch(String usernameToSearch) {
        this.usernameToSearch = usernameToSearch;
    }

    public boolean isSearchByTypePolicy() {
        return searchByTypePolicy;
    }

    public void setSearchByTypePolicy(boolean searchByTypePolicy) {
        this.searchByTypePolicy = searchByTypePolicy;
    }

    public int getPolicyNumberToSearch() {
        return policyNumberToSearch;
    }

    public void setPolicyNumberToSearch(int policyNumberToSearch) {
        this.policyNumberToSearch = policyNumberToSearch;
    }

    public boolean isSearchByBuyIn() {
        return searchByBuyIn;
    }

    public void setSearchByBuyIn(boolean searchByBuyIn) {
        this.searchByBuyIn = searchByBuyIn;
    }

    public int getBuyInAmount() {
        return buyInAmount;
    }

    public void setBuyInAmount(int buyInAmount) {
        this.buyInAmount = buyInAmount;
    }

    public boolean isSearchByChipPolicy() {
        return searchByChipPolicy;
    }

    public void setSearchByChipPolicy(boolean searchByChipPolicy) {
        this.searchByChipPolicy = searchByChipPolicy;
    }

    public int getChipPolicyAmount() {
        return chipPolicyAmount;
    }

    public void setChipPolicyAmount(int chipPolicyAmount) {
        this.chipPolicyAmount = chipPolicyAmount;
    }

    public boolean isSearchByMinimumBet() {
        return searchByMinimumBet;
    }

    public void setSearchByMinimumBet(boolean searchByMinimumBet) {
        this.searchByMinimumBet = searchByMinimumBet;
    }

    public int getMinimumBetAmount() {
        return minimumBetAmount;
    }

    public void setMinimumBetAmount(int minimumBetAmount) {
        this.minimumBetAmount = minimumBetAmount;
    }

    public boolean isSearchByMinimumPlayersAmount() {
        return searchByMinimumPlayersAmount;
    }

    public void setSearchByMinimumPlayersAmount(boolean searchByMinimumPlayersAmount) {
        this.searchByMinimumPlayersAmount = searchByMinimumPlayersAmount;
    }

    public int getMinimumPlayersAmount() {
        return minimumPlayersAmount;
    }

    public void setMinimumPlayersAmount(int minimumPlayersAmount) {
        this.minimumPlayersAmount = minimumPlayersAmount;
    }

    public boolean isSearchByMaximumPlayersAmount() {
        return searchByMaximumPlayersAmount;
    }

    public void setSearchByMaximumPlayersAmount(boolean searchByMaximumPlayersAmount) {
        this.searchByMaximumPlayersAmount = searchByMaximumPlayersAmount;
    }

    public int getMaximumPlayersAmount() {
        return maximumPlayersAmount;
    }

    public void setMaximumPlayersAmount(int maximumPlayersAmount) {
        this.maximumPlayersAmount = maximumPlayersAmount;
    }

    public boolean isSerachBySpectatingAvailable() {
        return serachBySpectatingAvailable;
    }

    public void setSerachBySpectatingAvailable(boolean serachBySpectatingAvailable) {
        this.serachBySpectatingAvailable = serachBySpectatingAvailable;
    }

    public boolean isSearchByPotSize() {
        return searchByPotSize;
    }

    public void setSearchByPotSize(boolean searchByPotSize) {
        this.searchByPotSize = searchByPotSize;
    }

    public int getPotSizeToSearch() {
        return potSizeToSearch;
    }

    public void setPotSizeToSearch(int potSizeToSearch) {
        this.potSizeToSearch = potSizeToSearch;
    }

    public boolean isSearchForReplayableGames() {
        return searchForReplayableGames;
    }

    public void setSearchForReplayableGames(boolean searchForReplayableGames) {
        this.searchForReplayableGames = searchForReplayableGames;
    }
}
