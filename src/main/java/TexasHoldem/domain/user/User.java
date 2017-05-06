package TexasHoldem.domain.user;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
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
    private BufferedImage img;
    private Wallet wallet;
    private Map<Game,Participant> gameMapping;

    private int amountEarnedInLeague;
    private int currLeague;
    private int numOfGamesPlayed;

    private User(){}

    public User(String user, String pass, String email, LocalDate date, BufferedImage image)
    {
        this.username = user;
        this.password = pass;
        this.wallet = new Wallet();
        this.amountEarnedInLeague = 0;
        this.email = email;
        this.dateOfBirth = date;
        this.img = image;
        this.gameMapping = new HashMap<>();
        this.numOfGamesPlayed = 0;
    }

    public void deposit(int amount, boolean selfDeposit) throws ArgumentNotInBoundsException {
        if(amount < 0)
            throw new ArgumentNotInBoundsException("Amount less than zero , should be greater.");
        getWallet().setBalance(getWallet().getBalance() + amount);

        if(!selfDeposit)
            amountEarnedInLeague += amount;
    }

    public int withdraw(int amount, boolean selfDeposit) throws ArgumentNotInBoundsException {
        int amountToReduce = amount;
        if(amount < 0)
            throw new ArgumentNotInBoundsException("Amount less than zero , should be greater.");
            //withdraw as many as he can
        else if(amount > getWallet().getBalance())
            amountToReduce = getWallet().getBalance();
        getWallet().setBalance(getWallet().getBalance() - amountToReduce);

        if(!selfDeposit)
            amountEarnedInLeague -= amountToReduce;
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
}
