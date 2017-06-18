package Server.domain.game;

import Enumerations.GamePolicy;
import Server.domain.events.gameFlowEvents.MoveEvent;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.game.card.Card;
import Server.domain.game.card.Dealer;
import Server.domain.game.hand.Hand;
import Server.domain.game.hand.HandCalculator;
import Server.domain.game.participants.Player;
import Server.domain.game.participants.Spectator;
import Server.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static Server.domain.game.GameActions.*;

/**
 * Created by Hod and Rotem on 05/04/2017.
 */
public class Round {
    private static Logger logger = LoggerFactory.getLogger(Round.class);

    private boolean isRoundActive;
    private GameSettings gameSettings;
    private List<Player> activePlayers;
    private List<Player> originalPlayersInRound;
    private List<Spectator> spectatorList;
    private Dealer dealer;
    private int chipsToCall;
    private Player currentPlayer;
    private Player lastPlayer;
    private int potAmount;
    private List<Card> openedCards;
    private Player currentDealerPlayer;
    private RoundState currentState;
    private List<GameEvent> eventList;
    private List<Player> winnerList;

    public Round(List<Player> players, GameSettings settings, int dealerIndex) {
        this.gameSettings = settings;
        this.activePlayers = new ArrayList<Player>(players);
        this.originalPlayersInRound = new ArrayList<Player>(players);
        this.spectatorList = null;
        this.dealer = new Dealer();
        this.currentDealerPlayer = activePlayers.get(dealerIndex);
        this.chipsToCall = gameSettings.getMinBet();
        this.potAmount = 0;
        this.openedCards = new ArrayList<Card>();
        this.currentState = RoundState.PREFLOP;
        this.eventList = new ArrayList<>();
        this.winnerList = new ArrayList<>();

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

        // Send round update notification
        NotificationService.getInstance().sendRoundUpdateNotification(this);
        // Call communication layer to send currentPlayer a message which requests him to play
        NotificationService.getInstance().sendPlayMoveNotification(gameSettings.getName(), currentPlayer, calculateTurnOptions());
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
        //logger.info("{} has left the game {} during a played round.", player.getUser().getUsername(), gameSettings.getName());

        if (activePlayers.size() == 1) {
            endRound();
        }
    }

    private void endRound() {
        if (activePlayers.size() == 1) {
            Player winner = activePlayers.get(0);
            winner.addChips(potAmount);
            this.winnerList.add(winner);
            logger.info("{} is the winner!!!", winner.getUser().getUsername());
        }
        else {
            calculateWinner();
        }

        initPlayersTotalAmountPayedInRound();
        updatePlayersGamesPlayed();
        setRoundActive(false);

        // Send round update notification
        NotificationService.getInstance().sendRoundUpdateNotification(this);
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

    public void playTurnOfPlayer(MoveEvent playerMoveEvent) throws IllegalArgumentException{
        if(currentPlayer != playerMoveEvent.getEventInitiator())
            throw new IllegalArgumentException("This is not your turn");

        if(!calculateTurnOptions().contains(playerMoveEvent.getEventAction()))
            throw new IllegalArgumentException("Your move is Invalid");

        boolean isLastPlayerPlayed = false;
        if (isRoundActive) {
            GameActions chosenAction = playerMoveEvent.getEventAction();

            isLastPlayerPlayed = isLastPlayerPlayed(playerMoveEvent);

            switch (chosenAction) {
                case RAISE:
                    playerRaiseTurn(playerMoveEvent.getAmountToRaise());
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

            eventList.add(playerMoveEvent);

            if (!isRoundActive)
                return;

            if (isLastPlayerPlayed) {
                endFlow();
            } else {
                // Send RoundUpdateNotification
                NotificationService.getInstance().sendRoundUpdateNotification(this);
                // Call communication layer to send currentPlayer a message which requests him to play
                NotificationService.getInstance().sendPlayMoveNotification(gameSettings.getName(), currentPlayer, calculateTurnOptions());
            }
        }
    }

    private void endFlow() {
        initLastPlayer();

        if (isRoundActive) {
            chipsToCall = 0;
            initPlayersLastBetSinceCardOpen();

            switch (currentState) {
                case PREFLOP:
                    endPreFlopFlow();
                    break;
                case FLOP:
                    endFlopFlow();
                    break;
                case TURN:
                    endTurnFlow();
                    break;
                case RIVER:
                    endRiverFlow();
                    break;
            }
        }
    }

    private void endRiverFlow() {
        chipsToCall = 0;
        initPlayersLastBetSinceCardOpen();
        endRound();

        logger.info("Round finished.");
    }

    private void endTurnFlow() {
        endFlopOrTurnFlow();
        currentState = RoundState.RIVER;
        startNewFlow();
    }

    private void endFlopFlow() {
        endFlopOrTurnFlow();
        currentState = RoundState.TURN;
        startNewFlow();
    }

    private void endPreFlopFlow() {
        openedCards.addAll(dealer.open(3));
        logger.info("Cards opened are: ");

        for (Card c : openedCards) {
            logger.info("{} , ", c);
        }

        currentState = RoundState.FLOP;
        startNewFlow();
    }

    private void startNewFlow() {
        int dealerIndex = activePlayers.indexOf(currentDealerPlayer);
        int newCurrentPlayerIndex = (dealerIndex + 1) % (activePlayers.size());
        currentPlayer = activePlayers.get(newCurrentPlayerIndex);

        // Send RoundUpdateNotification
        NotificationService.getInstance().sendRoundUpdateNotification(this);
        // Call communication layer to send currentPlayer a message which requests him to play
        NotificationService.getInstance().sendPlayMoveNotification(gameSettings.getName(), currentPlayer, calculateTurnOptions());
    }

    private void endFlopOrTurnFlow() {
        openedCards.addAll(dealer.open(1));
        logger.info("Cards opened are: ");

        for (Card c : openedCards) {
            logger.info("{} , ", c);
        }
    }

    private boolean isLastPlayerPlayed(MoveEvent playerMoveEvent) {
        return (playerMoveEvent.getEventInitiator() == lastPlayer && playerMoveEvent.getEventAction() != GameActions.RAISE);
    }

    private int getMaxAmountToRaise() {
        if (gameSettings.getGameType() == GamePolicy.LIMIT) {
            return gameSettings.getGameTypeLimit();
        }
        else if (gameSettings.getGameType() == GamePolicy.POTLIMIT) {
            return potAmount;
        }
        else {
            return -1; // Represents infinity amount
        }
    }

    private void playerRaiseTurn(int amountToRaise) throws IllegalArgumentException {
        if(gameSettings.getGameType() == GamePolicy.LIMIT && amountToRaise - chipsToCall > gameSettings.getGameTypeLimit())
            throw new IllegalArgumentException("OOPS you raised more than the limit was decided, please try again");
        if(gameSettings.getGameType() == GamePolicy.POTLIMIT && amountToRaise - chipsToCall > potAmount)
            throw new IllegalArgumentException("OOPS you raised more than the current pot, please try again");
        int currentPlayerIndex = activePlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % (activePlayers.size());
        int lastBet = currentPlayer.getLastBetSinceCardOpen();

        // We assume amount to raise includes the last bet, so
        // we need to add to the pot the difference between them
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
            winnerList.add(p);
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

    private void updatePlayersGamesPlayed() {
        for(int i = 0; i < originalPlayersInRound.size(); i++)
            originalPlayersInRound.get(i).updateGamesPlayed();
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

    public RoundState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(RoundState currentState) {
        this.currentState = currentState;
    }

    public void setChipsToCall(int chipsToCall) {
        this.chipsToCall = chipsToCall;
    }

    public List<GameEvent> getEvents(){
        return eventList;
    }

    public int getBalanceOfPlayer(String userName){
        return getActivePlayers().stream()
                .filter(player -> player.getUser().getUsername().equals(userName))
                .collect(Collectors.toList()).get(0).getChipsAmount();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public List<Player> getWinnerList() {
        return winnerList;
    }

    public void setWinnerList(List<Player> winnerList) {
        this.winnerList = winnerList;
    }

    public List<Spectator> getSpectatorList() {
        return spectatorList;
    }

    public void setSpectatorList(List<Spectator> spectatorList) {
        this.spectatorList = spectatorList;
    }
}