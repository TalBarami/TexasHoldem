package Server.domain.user;


import javax.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walletId")
    private int id;

    @Column(name = "balance")
    private int balance;


    public Wallet(int balance){
        this.balance = balance;
    }

    public Wallet()
    {
        balance = 0;
    }

    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
