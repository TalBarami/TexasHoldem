package TexasHoldem.domain.user;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.participants.Participant;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "userName")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "birthDate")
    private LocalDate dateOfBirth;

    @Transient
    private BufferedImage img;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    @Transient
    private Map<Game,Participant> gameMapping;

    @Column(name = "amountEarnedInLeague")
    private int amountEarnedInLeague;

    @Column(name = "currLeague")
    private int currLeague;

    @Column(name = "netoProfit")
    private int totalNetoProfit;

    @Column(name = "grossProfit")
    private int totalGrossProfit;

    @Column(name = "highestCashGain")
    private int highestCashGain;

    @Column(name = "numOfGamesPlayed")
    private int numOfGamesPlayed;

    public User(){}

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



    public Wallet getWallet() {
        return wallet;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate date) {
        this.dateOfBirth = date;
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

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setTotalNetoProfit(int totalNetoProfit) {
        this.totalNetoProfit = totalNetoProfit;
    }

    public void setTotalGrossProfit(int totalGrossProfit) {
        this.totalGrossProfit = totalGrossProfit;
    }

    public void setHighestCashGain(int highestCashGain) {
        this.highestCashGain = highestCashGain;
    }

    public void setNumOfGamesPlayed(int numOfGamesPlayed) {
        this.numOfGamesPlayed = numOfGamesPlayed;
    }


}
