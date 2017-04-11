package TexasHoldem.domain.users;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.Player;

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
    private Map<Game,Player> playerInGames;

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

    private User(){

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

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateofbirth() {
        return dateOfBirth;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate date) {
        this.dateOfBirth = date;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public Map<Game,Player> getGamePlayerMappings(){
        return playerInGames;
    }

    public void addGamePlayer(Game game,Player p){
        playerInGames.put(game,p);
    }
}
