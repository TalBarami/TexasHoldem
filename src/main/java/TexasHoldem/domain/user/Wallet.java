package TexasHoldem.domain.user;

public class Wallet {

    private int balance;

    public Wallet(int balance){
        this.balance = balance;
    }

    public Wallet()
    {
        balance = 10000;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
