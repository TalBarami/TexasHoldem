package TexasHoldem.domain.users;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.participants.Participant;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;
    private String password;
    private String email;
    private LocalDate dateOfBirth;
    BufferedImage img;
    private Wallet wallet;
    private Map<Game,Participant> playerInGames;

    public User(String user, String pass, String email, LocalDate date)
    {
        this.username = user;
        this.password = pass;
        this.wallet = new Wallet();
        this.email = email;
        dateOfBirth = date;
        img = null;
        playerInGames=new HashMap<>();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    //todo : make it private player changes (delete usages of getWallet)
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
        return playerInGames;
    }

    public void addGameParticipant(Game game,Participant p){
        playerInGames.put(game,p);
    }

    public void deposit(double amount,boolean selfDeposit) throws InvalidArgumentException {
        if(amount < 0)
            throw new InvalidArgumentException("Amount less than zero , should be greater.");
        getWallet().setBalance(getWallet().getBalance() + amount);

        if(!selfDeposit)
            System.out.println("");//todo : calculate league someway
    }

    public void withdraw(double amount) throws InvalidArgumentException, ArgumentNotInBoundsException {
        if(amount < 0)
            throw new InvalidArgumentException("Amount less than zero , should be greater.");
        else if(amount > getWallet().getBalance())
            throw new ArgumentNotInBoundsException("Amount to be withdrawn is more than player's balance.");
        getWallet().setBalance(getWallet().getBalance() - amount);
    }

    public double getBalance(){
        return getWallet().getBalance();
    }
}
