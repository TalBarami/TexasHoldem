package TexasHoldem.domain.users;

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

}
