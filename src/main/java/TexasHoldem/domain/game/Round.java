package TexasHoldem.domain.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static TexasHoldem.domain.game.Game.*;
import static TexasHoldem.domain.game.Game.GameActions.*;

/**
 * Created by Hod and Rotem on 05/04/2017.
 */
public class Round {
    private boolean isRoundActive;
    private GameSettings gameSettings;
    private List<Player> activePlayers;
    private Dealer dealer;
    private double chipsToCall;
    private Player currentPlayer;
    private Player lastPlayer;
    private int potAmount;
    private List<Card> openedCards;
    private Player currentDealerPlayer;

    public Round(List<Player> players, GameSettings settings, int dealerIndex) {
        this.gameSettings = settings;
        this.activePlayers = players;
        this.dealer = new Dealer();
        this.currentDealerPlayer = activePlayers.get(dealerIndex);
        this.chipsToCall = gameSettings.getMinBet();
        this.potAmount = 0;
        this.openedCards = new ArrayList<Card>();

        int startingPlayerIndex = (dealerIndex + 3) % activePlayers.size();
        int lastPlayerIndex = (dealerIndex + 2) % activePlayers.size();
        this.currentPlayer = activePlayers.get(startingPlayerIndex);
        this.lastPlayer = activePlayers.get(lastPlayerIndex);
        initPlayersTotalAmountPayedInRound();
    }

    private void initPlayersTotalAmountPayedInRound() {
        for (Player p : activePlayers) {
            p.setTotalAmountPayedInRound(0);
        }
    }

    public void startRound() {
        dealer.dealCardsToPlayers(activePlayers);
        paySmallAndBigBlind();
        playPreFlopRound();

        if (activePlayers.size() > 1) {
            playFlopRound();
        }
        if (activePlayers.size() > 1) {
            playTurnRound();
        }
        if (activePlayers.size() > 1) {
            playRiverRound();
        }
        if (activePlayers.size() > 1) {
            endRound();
        }
    }

    private void playRiverRound() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int newCurrentPlayerIndex = (dealerIndex + 1) % (activePlayers.size());
        currentPlayer = activePlayers.get(newCurrentPlayerIndex);

        playRoundFlow();
    }

    private void playTurnRound() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int newCurrentPlayerIndex = (dealerIndex + 1) % (activePlayers.size());
        currentPlayer = activePlayers.get(newCurrentPlayerIndex);

        playRoundFlow();
        openedCards.addAll(dealer.openCards(1));
        System.out.println("Cards opened are: ");

        for (Card c : openedCards) {
            System.out.println(c + ", ");
        }
    }

    private void paySmallAndBigBlind() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int smallBlindPlayerIndex = (dealerIndex + 1) % activePlayers.size();
        int bigBlindPlayerIndex = (dealerIndex + 2) % activePlayers.size();
        Player smallPlayer = activePlayers.get(smallBlindPlayerIndex);
        Player bigPlayer = activePlayers.get(bigBlindPlayerIndex);

        int smallBlind = gameSettings.getMinBet() / 2;
        int bigBlind = gameSettings.getMinBet();

        smallPlayer.payChips(smallBlind);
        System.out.println("Small blind payed by " + smallPlayer.getUser().getUsername());
        bigPlayer.payChips(bigBlind);
        System.out.println("Big blind payed by " + bigPlayer.getUser().getUsername());
    }

    private void playFlopRound() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int newCurrentPlayerIndex = (dealerIndex + 1) % (activePlayers.size());
        currentPlayer = activePlayers.get(newCurrentPlayerIndex);

        playRoundFlow();
        openedCards.addAll(dealer.openCards(1));
        System.out.println("Cards opened are: ");

        for (Card c : openedCards) {
            System.out.println(c + ", ");
        }
    }

    private void playPreFlopRound() {
        playRoundFlow();

        openedCards.addAll(dealer.openCards(3));
        System.out.println("Cards opened are: ");

        for (Card c : openedCards) {
            System.out.println(c + ", ");
        }
    }

    private void playRoundFlow() {
        while (currentPlayer != lastPlayer) {
            GameActions chosenAction;
            chosenAction = currentPlayer.chooseAction(calculateTurnOptions());

            switch (chosenAction) {
                case RAISE:
                    int amountToRaise = currentPlayer.chooseAmountToRaise(chipsToCall * 2);
                    playerRaiseTurn(amountToRaise);
                    break;
                case CHECK:
                    playerCheckTurn();
                    break;
                case FOLD:
                    playerFoldTurn();
                    break;
                case CALL:
                    playerCallTurn();
                    break;
            }
        }
    }

    private void playerRaiseTurn(double amountToRaise) {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());

        currentPlayer.payChips(amountToRaise - currentPlayer.getLastBetSinceCardOpen());
        lastPlayer = currentPlayer;
        chipsToCall = amountToRaise;
        currentPlayer = activePlayers.get(nextPlayerIndex);
    }

    private void playerCheckTurn() {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());

        currentPlayer = activePlayers.get(nextPlayerIndex);
    }

    private void playerFoldTurn() {
        removePlayerFromRound(currentPlayer);
    }

    private void playerCallTurn() {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());

        currentPlayer.payChips(chipsToCall - currentPlayer.getLastBetSinceCardOpen());
        currentPlayer = activePlayers.get(nextPlayerIndex);
    }

    private List<Game.GameActions> calculateTurnOptions() {
        List<Game.GameActions> gameActions = new LinkedList<Game.GameActions>();
        double difference = chipsToCall - currentPlayer.getLastBetSinceCardOpen();

        if (difference == 0)
            gameActions.add(CHECK);

        if (difference > 0)
            gameActions.add(CALL);

        if (currentPlayer.getChipsAmount() > difference)
            gameActions.add(RAISE);

        gameActions.add(FOLD);
        return gameActions;
    }

    private void removePlayerFromRound(Player player) {
        potAmount += player.getLastBetSinceCardOpen();

        if (lastPlayer == player) {
            int lastPlayerIndex = activePlayers.indexOf(lastPlayer);
            int newLastPlayerIndex = (lastPlayerIndex - 1) % (activePlayers.size());
            lastPlayer = activePlayers.get(newLastPlayerIndex);
        }

        if (currentPlayer == player) {
            int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
            int newCurrentPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());
            currentPlayer = activePlayers.get(newCurrentPlayerIndex);
        }

        if (currentDealerPlayer == player) {
            int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
            int newCurrentPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());
            currentDealerPlayer = activePlayers.get(newCurrentPlayerIndex);
        }

        activePlayers.remove(player);
        System.out.println(player.getUser().getUsername() + " has left the game");

        if (activePlayers.size() == 1) {
            endRound();
        }
    }

    private void endRound() {
        if (activePlayers.size() == 1) {
            Player winner = activePlayers.get(0);
            winner.addChips(potAmount);
            System.out.println(winner.getUser().getUsername() + " is the winner!!!");
        }
        else {
            calculateWinner();
        }

        setRoundActive(false);
    }

    private void calculateWinner() {

    }

    public void notifyPlayerExited(Player p) {

    }

    public boolean isRoundActive() {
        return isRoundActive;
    }

    public void setRoundActive(boolean roundActive) {
        isRoundActive = roundActive;
    }
}
