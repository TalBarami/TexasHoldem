package TexasHoldem.domain.users;

public class Wallet {

    private int balance;

    public Wallet(int balance){
        this.balance = balance;
    }

    public Wallet()
    {
        balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
