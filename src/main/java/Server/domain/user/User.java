package Server.domain.user;

import Exceptions.ArgumentNotInBoundsException;
import Server.domain.game.Game;
import Server.domain.game.participants.Participant;
import Server.notification.NotificationService;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class User extends Observable {

    private String username;
    private String password;
    private String email;
    private LocalDate dateOfBirth;
    private BufferedImage img;
    private Wallet wallet;
    private Map<Game,Participant> gameMapping;

    private int amountEarnedInLeague;
    private int currLeague;
    private int totalNetoProfit;
    private int totalGrossProfit;
    private int highestCashGain;
    private int numOfGamesPlayed;

    private User(){}

    public User(String user, String pass, String email, LocalDate date, BufferedImage image)
    {
        this.username = user;
        this.password = pass;
        this.wallet = new Wallet();
        this.email = email;
        this.dateOfBirth = date;
        this.img = image;
        this.gameMapping = new HashMap<>();
        this.numOfGamesPlayed = 0;
        this.totalNetoProfit = 0;
        this.totalGrossProfit = 0;
        this.highestCashGain = 0;

        User thisUser = this;
        addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                NotificationService.getInstance().sendUserProfileUpdateNotification(thisUser);
            }
        });
    }

    public void deposit(int amount, boolean selfDeposit) throws ArgumentNotInBoundsException {
        if(amount < 0)
            throw new ArgumentNotInBoundsException("Amount less than zero , should be greater.");
        getWallet().setBalance(getWallet().getBalance() + amount);

        if(!selfDeposit) {
            amountEarnedInLeague += amount;
            totalNetoProfit += amount;
            totalGrossProfit += amount;
            updateHighestCashGain(amount);
        }

        setChanged();
        notifyObservers();
    }

    public int withdraw(int amount, boolean selfWithraw) throws ArgumentNotInBoundsException {
        int amountToReduce = amount;
        if(amount < 0)
            throw new ArgumentNotInBoundsException("Amount less than zero , should be greater.");
            //withdraw as many as he can
        else if(amount > getWallet().getBalance())
            amountToReduce = getWallet().getBalance();
        getWallet().setBalance(getWallet().getBalance() - amountToReduce);

        if(!selfWithraw) {
            amountEarnedInLeague -= amountToReduce;
            totalNetoProfit -= amountToReduce;
        }

        setChanged();
        notifyObservers();

        return amountToReduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (amountEarnedInLeague != user.amountEarnedInLeague) return false;
        if (currLeague != user.currLeague) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(user.dateOfBirth) : user.dateOfBirth != null) return false;
        if (img != null ? !img.equals(user.img) : user.img != null) return false;
        if (wallet != null ? !wallet.equals(user.wallet) : user.wallet != null) return false;
        return gameMapping != null ? gameMapping.equals(user.gameMapping) : user.gameMapping == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (wallet != null ? wallet.hashCode() : 0);
        result = 31 * result + (gameMapping != null ? gameMapping.hashCode() : 0);
        result = 31 * result + amountEarnedInLeague;
        result = 31 * result + currLeague;
        return result;
    }

    private void updateHighestCashGain(int amount) {
        if(amount > highestCashGain)
            highestCashGain = amount;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    private Wallet getWallet() {
        return wallet;
    }

    public void setUsername(String username) {
        this.username = username;
        setChanged();
    }

    public void setPassword(String password) {
        this.password = password;
        setChanged();
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
        setChanged();
    }

    public void setDateOfBirth(LocalDate date) {
        this.dateOfBirth = date;
        setChanged();
    }

    public Map<Game,Participant> getGamePlayerMappings(){
        return gameMapping;
    }

    public void addGameParticipant(Game game,Participant p){
        gameMapping.put(game,p);
    }

    public void removeGameParticipant(Game game){
        gameMapping.remove(game);
    }

    public int getBalance(){
        return getWallet().getBalance();
    }

    public int getAmountEarnedInLeague() {
        return amountEarnedInLeague;
    }

    public void setAmountEarnedInLeague(int amountEarnedInLeague) {
        this.amountEarnedInLeague = amountEarnedInLeague;
    }

    public int getCurrLeague() {
        return currLeague;
    }

    public void setCurrLeague(int currLeague) {
        this.currLeague = currLeague;
        setChanged();
        notifyObservers();
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getNumOfGamesPlayed() {
        return numOfGamesPlayed;
    }

    public void updateGamesPlayed() {
        this.numOfGamesPlayed++;
        setChanged();
        notifyObservers();
    }

    public int getTotalProfit() {
        return totalNetoProfit;
    }

    public int getHighestCashGain() {
        return highestCashGain;
    }

    public int getTotalNetoProfit() {
        return totalNetoProfit;
    }

    public int getTotalGrossProfit() {
        return totalGrossProfit;
    }

    public double getAvgNetoProfit(){
        return getNumOfGamesPlayed() != 0 ? getTotalNetoProfit()/getNumOfGamesPlayed() : 0;
    }

    public double getAvgGrossProfit(){
        return getNumOfGamesPlayed() != 0 ? getTotalGrossProfit()/getNumOfGamesPlayed() : 0;
    }
}
