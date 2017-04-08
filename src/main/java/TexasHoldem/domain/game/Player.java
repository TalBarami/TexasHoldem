package TexasHoldem.domain.game;

import TexasHoldem.domain.users.User;
import java.util.*;

/**
 * Created by Tal on 05/04/2017.
 */
public class Player {
    private User user;
    private Set<Card> cards;
    private int chipsAmount;
    private int lastBetSinceCardOpen;
    private int totalAmountPayedInRound;

    public Player(User user){
        this.user=user;
        this.cards = new HashSet<>();
        this.chipsAmount = 0;
        this.lastBetSinceCardOpen = 0;
    }

    public void addChips(int amount) {
        chipsAmount += amount;
    }

    public void addCards(Collection<Card> cards){
        this.cards.addAll(cards);
    }

    public void payChips(int amount) {
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

    public User getUser() {
        return user;
    }

    public int getLastBetSinceCardOpen() {
        return lastBetSinceCardOpen;
    }

    public void setLastBetSinceCardOpen(int lastBetSinceCardOpen) {
        this.lastBetSinceCardOpen = lastBetSinceCardOpen;
    }

    public int getChipsAmount() {
        return chipsAmount;
    }

    public void setChipsAmount(int chipsAmount) {
        this.chipsAmount = chipsAmount;
    }

    public void playTurn(List<GameActions> gameActions, double minRaise) {
    }

    public int chooseAmountToRaise(double minAmount) {
        return 0;
    }

    public int getTotalAmountPayedInRound() {
        return totalAmountPayedInRound;
    }

    public void setTotalAmountPayedInRound(int totalAmountPayedInRound) {
        this.totalAmountPayedInRound = totalAmountPayedInRound;
    }
}