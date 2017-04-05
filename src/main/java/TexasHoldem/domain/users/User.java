package TexasHoldem.domain.users;

import org.joda.time.DateTime;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * Created by Tal on 05/04/2017.
 */

public class User {

    private String username;
    private String password;
    private String email;
    private DateTime dateofbirth;
    BufferedImage img;
    private Wallet wallet;

    public User(String user, String pass, String email, DateTime date)
    {
        this.username = user;
        this.password = pass;
        this.wallet = new Wallet();
        this.email = email;
        this.dateofbirth = date;
        img = null;
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

    public DateTime getDateofbirth() {
        return dateofbirth;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateofbirth(DateTime dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
