package TexasHoldem.domain.users;

/**
 * Created by אחיעד on 05/04/2017.
 */
public class Wallet {

    private double balance;

    public Wallet(double balance){
        this.balance = balance;
    }

    public Wallet()
    {
        balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount){
        balance=+amount;
    }
}
