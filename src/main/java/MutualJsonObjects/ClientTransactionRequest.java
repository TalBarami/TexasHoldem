package MutualJsonObjects;

import Server.domain.user.Transaction;

/**
 * Created by rotemwald on 16/05/17.
 */
public class ClientTransactionRequest {
    private Transaction action;
    private int amount;

    public ClientTransactionRequest() {
    }

    public ClientTransactionRequest(Transaction action, int amount) {
        this.action = action;
        this.amount = amount;
    }

    public Transaction getAction() {
        return action;
    }

    public void setAction(Transaction action) {
        this.action = action;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
