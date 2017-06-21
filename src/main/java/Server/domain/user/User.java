package Server.domain.user;

import Exceptions.ArgumentNotInBoundsException;
import Server.data.users.Users;
import Server.domain.game.Game;
import Server.domain.game.participants.Participant;
import org.hibernate.annotations.*;
import Server.notification.NotificationService;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

@Entity
@Table(name = "users")
public class User extends Observable {
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
    @JoinColumn(name = "walletId")
    @Cascade( {org.hibernate.annotations.CascadeType.DELETE_ORPHAN} )
    private Wallet wallet;

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

    public User(){
        User thisUser = this;
        addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                NotificationService.getInstance().sendUserProfileUpdateNotification(thisUser);
            }
        });
    }

    public User(String user, String pass, String email, LocalDate date, BufferedImage image)
    {
        this.username = user;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] digest = md.digest();
            this.password = new String(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        this.wallet = new Wallet();
        this.email = email;
        this.dateOfBirth = date;
        this.img = image;
        this.numOfGamesPlayed = 0;
        this.totalNetoProfit = 0;
        this.totalGrossProfit = 0;
        this.highestCashGain = 0;
        this.currLeague = LeagueManager.defaultLeagueForNewUsers;

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

        Users.updateUser(this);
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

        Users.updateUser(this);
        setChanged();
        notifyObservers();

        return amountToReduce;
    }

    private void updateHighestCashGain(int amount) {
        if(amount > highestCashGain) {
            highestCashGain = amount;
            Users.updateUser(this);
        }
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
        setChanged();
    }

    public void setPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            this.password = new String(digest);
            setChanged();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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

    public void addGameParticipant(Game game,Participant p){
        Users.addGameParticipant(this, game, p);
    }

//    public void removeGameParticipant(Game game){
//        gameMapping.remove(game);
//    }

    public int getBalance(){
        return getWallet().getBalance();
    }

    public int getAmountEarnedInLeague() {
        return amountEarnedInLeague;
    }

    public void setAmountEarnedInLeague(int amountEarnedInLeague) {
        this.amountEarnedInLeague = amountEarnedInLeague;
        Users.updateUser(this);
    }

    public int getCurrLeague() {
        return currLeague;
    }

    public void setCurrLeague(int currLeague) {
        this.currLeague = currLeague;
        Users.updateUser(this);
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
        Users.updateUser(this);
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

    public double getAvgNetoProfit(){
        return getNumOfGamesPlayed() != 0 ? getTotalNetoProfit()/getNumOfGamesPlayed() : 0;
    }

    public double getAvgGrossProfit(){
        return getNumOfGamesPlayed() != 0 ? getTotalGrossProfit()/getNumOfGamesPlayed() : 0;
    }

    public Map<String, Participant> getGameMapping() {
        return Users.getGameParticipant(this);
    }

}
