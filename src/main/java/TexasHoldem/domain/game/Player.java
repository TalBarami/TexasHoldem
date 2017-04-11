package TexasHoldem.domain.game;

import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.users.User;
import java.util.*;

/**
 * Created by Hod and Rotem on 05/04/2017.
 */
public class Player {
    private User user;
    private Set<Card> cards;
    private int chipsAmount;
    private int lastBetSinceCardOpen;
    private int totalAmountPayedInRound;
    private int chipPolicy;

    public Player(User user, int chipsAmount, int policy){
        this.user=user;
        this.cards = new HashSet<>();
        this.chipsAmount = chipsAmount;
        this.lastBetSinceCardOpen = 0;
        this.chipPolicy = policy;
    }

    private Player(){

    }

    public void addChips(int amount) {
        if (chipPolicy == 0) {
            user.getWallet().setBalance(user.getWallet().getBalance() + amount);
        }
        else
            chipsAmount += amount;
    }

    public void payChips(int amount) {
        if (chipPolicy == 0) {
            if (user.getWallet().getBalance() >= amount) {
                user.getWallet().setBalance(user.getWallet().getBalance() - amount);
                lastBetSinceCardOpen += amount;
                totalAmountPayedInRound += amount;
            }

            else {
                lastBetSinceCardOpen += chipsAmount;
                totalAmountPayedInRound += chipsAmount;
                user.getWallet().setBalance(0);
            }
        }
        else {
            if (chipsAmount >= amount) {
                chipsAmount -= amount;
                lastBetSinceCardOpen += amount;
                totalAmountPayedInRound += amount;
            }

            else {
                lastBetSinceCardOpen += chipsAmount;
                totalAmountPayedInRound += chipsAmount;
                chipsAmount = 0;
            }
        }
    }

    // TODO :: Make choose dynamically
    public int chooseAmountToRaise(int minAmount) {
        return minAmount;
    }

    // TODO :: Make choose dynamically
    public Game.GameActions chooseAction(List<Game.GameActions> gameActions) {
        if (gameActions.contains(Game.GameActions.CALL)) {
            return Game.GameActions.CALL;
        }
        else {
            return Game.GameActions.CHECK;
        }
    }

    public void calculateEarnings(double ratio){
        updateWallet(ratio*chipsAmount);
    }

    public void updateWallet(double amount){
        user.getWallet().setBalance(user.getWallet().getBalance()+amount);
    }

    public void addCards(Collection<Card> cards){
        this.cards.addAll(cards);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public User getUser() {
        return user;
    }

    public int getChipsAmount() {
        return chipsAmount;
    }

    public int getLastBetSinceCardOpen() {
        return lastBetSinceCardOpen;
    }

    public int getTotalAmountPayedInRound() {
        return totalAmountPayedInRound;
    }

    public void setLastBetSinceCardOpen(int lastBetSinceCardOpen) {
        this.lastBetSinceCardOpen = lastBetSinceCardOpen;
    }

    public void setChipsAmount(int chipsAmount) {
        this.chipsAmount = chipsAmount;
    }

    public void setTotalAmountPayedInRound(int totalAmountPayedInRound) {
        this.totalAmountPayedInRound = totalAmountPayedInRound;
    }
}