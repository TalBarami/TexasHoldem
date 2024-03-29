package Server.domain.game.participants;

import Exceptions.ArgumentNotInBoundsException;
import Server.domain.game.card.Card;
import Server.domain.game.Game;
import Server.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Hod and Rotem on 05/04/2017.
 */


public class Player extends Participant{
    private static Logger logger = LoggerFactory.getLogger(Player.class);
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
                logger.error("amount to add to player cannot be negative");
            }
        }
        else {
            chipsAmount += amount;
            logger.info("Player {} earned {} chips", this.getUser().getUsername(), amount);
        }
    }

    public int payChips(int amount) {
        if (chipPolicy == 0) {
            int amountWasReduced = 0;
            try {
                amountWasReduced = user.withdraw(amount, false);
            } catch (ArgumentNotInBoundsException e) {
                logger.error("amount to add to player cannot be negative");
            }
            lastBetSinceCardOpen += amountWasReduced;
            totalAmountPayedInRound += amountWasReduced;

            return amountWasReduced;
        }
        else {
            if (chipsAmount >= amount) {
                chipsAmount -= amount;
                lastBetSinceCardOpen += amount;
                totalAmountPayedInRound += amount;

                logger.info("Player {} payed {} chips", this.getUser().getUsername(), amount);
                return amount;
            }

            else {
                lastBetSinceCardOpen += chipsAmount;
                totalAmountPayedInRound += chipsAmount;
                int amountToReturn = chipsAmount;
                chipsAmount = 0;

                logger.info("Player {} payed {} chips", this.getUser().getUsername(), amountToReturn);
                return amountToReturn;
            }
        }
    }

    public void addCards(Collection<Card> cards){
        this.cards.addAll(cards);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public int getChipsAmount() {
        if(chipPolicy == 0)
            return user.getBalance();
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

    public void updateGamesPlayed(){
        user.updateGamesPlayed();
    }

    public void clearCards(){
        cards.clear();
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public int getChipPolicy() {
        return chipPolicy;
    }

    public void setChipPolicy(int chipPolicy) {
        this.chipPolicy = chipPolicy;
    }
}