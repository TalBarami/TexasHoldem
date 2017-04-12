package TexasHoldem.domain.game.participants;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.user.User;
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
        g.removeParticipant(this);
    }
}