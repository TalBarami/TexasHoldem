package TexasHoldem.domain.game;

import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.card.Dealer;
import TexasHoldem.domain.game.hand.Hand;
import TexasHoldem.domain.game.hand.HandCalculator;
import TexasHoldem.domain.game.participants.Player;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static TexasHoldem.domain.game.GameActions.*;

/**
 * Created by Hod and Rotem on 05/04/2017.
 */
public class Round {
    private static Logger logger = LoggerFactory.getLogger(Round.class);

    private boolean isRoundActive;
    private GameSettings gameSettings;
    private List<Player> activePlayers;
    private List<Player> originalPlayersInRound;
    private Dealer dealer;
    private int chipsToCall;
    private Player currentPlayer;
    private Player lastPlayer;
    private int potAmount;
    private List<Card> openedCards;
    private Player currentDealerPlayer;

    public Round(List<Player> players, GameSettings settings, int dealerIndex) {
        this.gameSettings = settings;
        this.activePlayers = new ArrayList<Player>(players);
        this.originalPlayersInRound = new ArrayList<Player>(players);
        this.dealer = new Dealer();
        this.currentDealerPlayer = activePlayers.get(dealerIndex);
        this.chipsToCall = gameSettings.getMinBet();
        this.potAmount = 0;
        this.openedCards = new ArrayList<Card>();

        int startingPlayerIndex = (dealerIndex + 3) % activePlayers.size();
        this.currentPlayer = activePlayers.get(startingPlayerIndex);
        this.lastPlayer = getBigPlayer();
        initPlayersTotalAmountPayedInRound();
    }

    private Round(){

    }

    public void startRound() {
        setRoundActive(true);

        dealer.deal(activePlayers);
        paySmallAndBigBlind();
        playPreFlopRound();

        if (activePlayers.size() > 1) {
            chipsToCall = 0;
            initPlayersLastBetSinceCardOpen();
            playFlopOrTurnRound();
        }
        if (activePlayers.size() > 1) {
            chipsToCall = 0;
            initPlayersLastBetSinceCardOpen();
            playFlopOrTurnRound();
        }
        if (activePlayers.size() > 1) {
            chipsToCall = 0;
            initPlayersLastBetSinceCardOpen();
            playRiverRound();
        }
        if (activePlayers.size() > 1) {
            chipsToCall = 0;
            initPlayersLastBetSinceCardOpen();
            endRound();
        }

        logger.info("Round finished.");
    }

    public void notifyPlayerExited(Player player) {
        if (lastPlayer == player) {
            int lastPlayerIndex = activePlayers.indexOf(lastPlayer);
            int newLastPlayerIndex = (lastPlayerIndex == 0) ? (activePlayers.size() - 1) : ((lastPlayerIndex - 1) % activePlayers.size());
            lastPlayer = activePlayers.get(newLastPlayerIndex);
        }

        if (currentPlayer == player) {
            int currentPlayerIndex = activePlayers.indexOf(player);
            int newCurrentPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());
            currentPlayer = activePlayers.get(newCurrentPlayerIndex);
        }

        if (currentDealerPlayer == player) {
            int currentPlayerIndex = activePlayers.indexOf(player);
            int newCurrentPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());
            currentDealerPlayer = activePlayers.get(newCurrentPlayerIndex);
        }

        activePlayers.remove(player);
        logger.info("{} has left the game {} during a played round.", player.getUser().getUsername(), gameSettings.getName());

        if (activePlayers.size() == 1) {
            endRound();
        }
    }

    private void endRound() {
        if (activePlayers.size() == 1) {
            Player winner = activePlayers.get(0);
            winner.addChips(potAmount);
            logger.info("{} is the winner!!!", winner.getUser().getUsername());
        }
        else {
            calculateWinner();
        }

        initPlayersTotalAmountPayedInRound();
        setRoundActive(false);
    }

    private void paySmallAndBigBlind() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int smallBlindPlayerIndex = (dealerIndex + 1) % activePlayers.size();
        int bigBlindPlayerIndex = (dealerIndex + 2) % activePlayers.size();
        Player smallPlayer = activePlayers.get(smallBlindPlayerIndex);
        Player bigPlayer = activePlayers.get(bigBlindPlayerIndex);

        int smallBlind = gameSettings.getMinBet() / 2;
        int bigBlind = gameSettings.getMinBet();

        potAmount += smallPlayer.payChips(smallBlind);
        logger.info("Small blind payed by {}", smallPlayer.getUser().getUsername());
        potAmount += bigPlayer.payChips(bigBlind);
        logger.info("Big blind payed by {}", bigPlayer.getUser().getUsername());
    }

    private void playRoundFlow() {
        boolean isLastPlayerPlayed = false;

        while (!isLastPlayerPlayed && isRoundActive) {
            GameActions chosenAction;
            chosenAction = currentPlayer.chooseAction(calculateTurnOptions());

            if (currentPlayer == lastPlayer && chosenAction != GameActions.RAISE)
                isLastPlayerPlayed = true;

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

        initLastPlayer();
    }

    private void playPreFlopRound() {
        playRoundFlow();

        openedCards.addAll(dealer.open(3));
        logger.info("Cards opened are: ");

        for (Card c : openedCards) {
            logger.info("{} , ", c);
        }
    }

    private void playFlopOrTurnRound() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int newCurrentPlayerIndex = (dealerIndex + 1) % (activePlayers.size());
        currentPlayer = activePlayers.get(newCurrentPlayerIndex);

        playRoundFlow();
        openedCards.addAll(dealer.open(1));
        logger.info("Cards opened are: ");

        for (Card c : openedCards) {
            logger.info("{} , ", c);
        }
    }

    private void playRiverRound() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int newCurrentPlayerIndex = (dealerIndex + 1) % (activePlayers.size());
        currentPlayer = activePlayers.get(newCurrentPlayerIndex);

        playRoundFlow();
    }

    private void playerRaiseTurn(int amountToRaise) {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());

        int lastBet = currentPlayer.getLastBetSinceCardOpen();
        potAmount += currentPlayer.payChips(amountToRaise - lastBet);
        logger.info("Player {} raised {}$", currentPlayer.getUser().getUsername(), amountToRaise);

        int newLastPlayerIndex = (currentPlayerIndex == 0) ? (activePlayers.size() - 1) : ((currentPlayerIndex - 1) % activePlayers.size());
        lastPlayer = activePlayers.get(newLastPlayerIndex);
        chipsToCall = amountToRaise;
        currentPlayer = activePlayers.get(nextPlayerIndex);
    }

    private void playerCheckTurn() {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());

        logger.info("Player {} checked", currentPlayer.getUser().getUsername());
        currentPlayer = activePlayers.get(nextPlayerIndex);
    }

    private void playerFoldTurn() {
        logger.info("Player {} folded", currentPlayer.getUser().getUsername());
        notifyPlayerExited(currentPlayer);
    }

    private void playerCallTurn() {
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());

        potAmount += currentPlayer.payChips(chipsToCall - currentPlayer.getLastBetSinceCardOpen());
        logger.info("Player {} called.", currentPlayer.getUser().getUsername());

        currentPlayer = activePlayers.get(nextPlayerIndex);
    }

    private List<GameActions> calculateTurnOptions() {
        List<GameActions> gameActions = new LinkedList<GameActions>();
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

    private void calculateWinner() {
        while(!activePlayers.isEmpty()) {
            List<Player> winners = new LinkedList<Player>();
            Player currentPlayer = activePlayers.get(0);
            winners.add(currentPlayer);

            List<Card> cardList = new LinkedList<Card>();
            cardList.addAll(openedCards);
            cardList.addAll(currentPlayer.getCards());

            Hand bestHand = HandCalculator.getHand(cardList);

            for (Player p : activePlayers.subList(1, activePlayers.size())) {
                List<Card> newCardList = new LinkedList<Card>();
                newCardList.addAll(openedCards);
                newCardList.addAll(p.getCards());

                Hand hand = HandCalculator.getHand(newCardList);
                int resultOfHandsCompare = hand.compareTo(bestHand);

                if (resultOfHandsCompare > 0) {
                    winners.clear();
                    winners.add(p);
                    bestHand = hand;
                } else if (resultOfHandsCompare == 0) {
                    winners.add(p);
                }
            }

            Player winnerWithMinChips = findMinWinner(winners);
            divideWinningsBetweenWinners(winners, winnerWithMinChips);
            updateActivePlayers();
        }
    }

    private void divideWinningsBetweenWinners(List<Player> winners, Player winnerWithMinChips) {
        int sumToDivide = 0;
        int sumMinWinnerPut = winnerWithMinChips.getTotalAmountPayedInRound();

        for (Player p : originalPlayersInRound) {
            if (sumMinWinnerPut < p.getTotalAmountPayedInRound()) {
                sumToDivide += sumMinWinnerPut;
                p.setTotalAmountPayedInRound(p.getTotalAmountPayedInRound() - sumMinWinnerPut);
            }
            else {
                sumToDivide += p.getTotalAmountPayedInRound();
                p.setTotalAmountPayedInRound(0);
            }
        }

        sumToDivide =  sumToDivide/winners.size();
        for(Player p : winners) {
            p.addChips(sumToDivide);
            logger.info("Player {} earned {}$", p.getUser().getUsername(), sumToDivide);
        }
    }

    private Player findMinWinner(List<Player> winners) {
        Player playerWithMinChips = winners.get(0);
        int minPlayerAmountInPot = playerWithMinChips.getTotalAmountPayedInRound();

        for (Player p : winners) {
            int currentPlayerAmountInPot = p.getTotalAmountPayedInRound();
            if (currentPlayerAmountInPot < minPlayerAmountInPot) {
                playerWithMinChips = p;
            }
        }

        return playerWithMinChips;
    }

    private void updateActivePlayers() {
        List<Player> playersToRemove = new LinkedList<Player>();
        for (Player p : activePlayers) {
            if(p.getTotalAmountPayedInRound() == 0)
                playersToRemove.add(p);
        }

        activePlayers.removeAll(playersToRemove);
    }

    private void initPlayersTotalAmountPayedInRound() {
        for (Player p : activePlayers) {
            p.setTotalAmountPayedInRound(0);
        }
    }

    private void initPlayersLastBetSinceCardOpen() {
        for (Player p : activePlayers) {
            p.setLastBetSinceCardOpen(0);
        }
    }

    public void setOpenedCards(List<Card> openedCards) {
        this.openedCards = openedCards;
    }

    public void initLastPlayer() {
        lastPlayer = currentDealerPlayer;
    }

    public void setRoundActive(boolean roundActive) {
        isRoundActive = roundActive;
    }

    public boolean isRoundActive() {
        return isRoundActive;
    }

    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    public List<Player> getOriginalPlayersInRound() {
        return originalPlayersInRound;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public int getChipsToCall() {
        return chipsToCall;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public int getPotAmount() {
        return potAmount;
    }

    public List<Card> getOpenedCards() {
        return openedCards;
    }

    public Player getCurrentDealerPlayer() {
        return currentDealerPlayer;
    }

    public Player getSmallPlayer() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int smallBlindPlayerIndex = (dealerIndex + 1) % activePlayers.size();

        return activePlayers.get(smallBlindPlayerIndex);
    }

    public Player getBigPlayer() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int bigBlindPlayerIndex = (dealerIndex + 2) % activePlayers.size();

        return activePlayers.get(bigBlindPlayerIndex);
    }
}