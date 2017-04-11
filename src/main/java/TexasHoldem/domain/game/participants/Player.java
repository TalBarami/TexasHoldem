package TexasHoldem.domain.game.participants;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.users.User;
import java.util.*;

/**
 * Created by Hod and Rotem on 05/04/2017.
 */
public class Player extends Participant{
    private Set<Card> cards;
    private int chipsAmount;
    private int lastBetSinceCardOpen;
    private int totalAmountPayedInRound;
    private int chipPolicy;

    public Player(User user,int chipsAmount, int policy){
        super(user);
        this.cards = new HashSet<>();
        this.chipsAmount = chipsAmount;
        this.lastBetSinceCardOpen = 0;
        this.chipPolicy = policy;
    }

    private Player(){
        super();
    }

    //todo : remove getWallet and user other methods (deposit/withdraw)
    public void addChips(int amount) {
        if (chipPolicy == 0) {
            try {
                user.deposit(amount, false);
            } catch (ArgumentNotInBoundsException e) {
                System.out.println("amount to add to player cannot be negative");
            }
        }
        else
            chipsAmount += amount;
    }

    //todo : remove getWallet and user other methods (deposit/withdraw)
    public void payChips(int amount) {
        if (chipPolicy == 0) {
            int amountWasReduced = 0;
            try {
                amountWasReduced = user.withdraw(amount, false);
            } catch (ArgumentNotInBoundsException e) {
                System.out.println("amount for player to pay cannot be negative");
            }
            lastBetSinceCardOpen += amountWasReduced;
            totalAmountPayedInRound += amountWasReduced;
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

    //TODO :: Ronen will decide what to do with it
//    public void calculateEarnings(double ratio){
//        updateWallet(ratio*chipsAmount);
//    }

    //TODO :: Ronen will decide what to do with it
//    public void updateWallet(double amount){
//        user.getWallet().setBalance(user.getWallet().getBalance()+amount);
//    }

    public void addCards(Collection<Card> cards){
        this.cards.addAll(cards);
    }

    public Set<Card> getCards() {
        return cards;
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

    public void removeFromGame(Game g){
        g.removePlayer(this);
    }
}