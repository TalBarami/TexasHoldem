package Client.view.game;

import Client.domain.SessionHandler;
import Enumerations.Move;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.GameException;
import Exceptions.InvalidArgumentException;
import MutualJsonObjects.*;

import Client.domain.GameHandler;
import Client.domain.MenuHandler;
import Client.ClientUtils;
import Client.view.system.MainMenu;
import NotificationMessages.ChatNotification;
import NotificationMessages.RoundUpdateNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 14/05/2017.
 */
public class Game extends JFrame{
    private static Logger logger = LoggerFactory.getLogger(Game.class);

    private MainMenu ancestor;
    private GameHandler gameHandler;

    private JButton foldButton;
    private JButton raiseButton;
    private JButton callButton;
    private JButton checkButton;
    private JTextField chatTextField;
    private JTextPane chatPanel;
    private JButton sendButton;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private JPanel contentPane;
    private JButton startGameButton;
    private JLabel usernameLabel;
    private JLabel cashLabel;
    private JLabel potLabel;
    private JSpinner raiseAmountSpinner;
    private JScrollPane chatScrollPane;
    private JComboBox<String> chatComboBox;
    private JPanel cardsPanel;
    private JPanel eventsPanel;

    private List<JPanel> seats = Arrays.asList(bottomPanel, leftPanel, topPanel, rightPanel);

    public Game(MainMenu ancestor, String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        this.ancestor = ancestor;
        this.gameHandler = new GameHandler(gameName);

        assignActionListeners();
        initializeGame(gameHandler.getGameDetails());
    }

    public void init(){
        toFront();
        ClientUtils.frameInit(this, contentPane);
        setLocationRelativeTo(ancestor);
        setTitle(gameHandler.getGameDetails().getName());
        getRootPane().setDefaultButton(sendButton);
        pack();
    }

    private void initializeGame(ClientGameDetails gameDetails){
        logger.info("Initializing game.");
        generateUserInformation(SessionHandler.getInstance().user());
        initializeSeats(gameDetails);

        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.X_AXIS));
        cardsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        eventsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        reconfigureStartButton(gameHandler.getGameDetails().getPlayerList(), false);
        disableGameActions();
        sendButton.setEnabled(isClientPlaying(gameHandler.getGameDetails().getPlayerList()));
        chatComboBox.setEnabled(isClientPlaying(gameHandler.getGameDetails().getPlayerList()));

        potLabel.setText("0");
    }

    private void initializeSeats(ClientGameDetails gameDetails){
        logger.info("Initializing seats.");
        bottomPanel.setLayout(new GridLayout(1, 0));
        leftPanel.setLayout(new GridLayout(0, 1));
        topPanel.setLayout(new GridLayout(1, 0));
        rightPanel.setLayout(new GridLayout(0, 1));
        updatePlayersInformation(gameDetails.getPlayerList());
    }

    private void assignActionListeners(){
        logger.info("Assigning action listeners.");
        SessionHandler.getInstance().addUpdateCallback(this::generateUserInformation);

        gameHandler.addGameUpdateCallback(gameUpdateNotification -> notifyPlayers());
        gameHandler.addGameUpdateCallback(gameUpdateNotification -> updatePlayersInformation(gameUpdateNotification.getGameDetails().getPlayerList()));
        gameHandler.addGameUpdateCallback(gameUpdateNotification -> reconfigureStartButton(
                gameUpdateNotification.getGameDetails().getPlayerList(),
                gameUpdateNotification.getGameDetails().isRunning()));

        gameHandler.addRoundUpdateCallback(roundUpdateNotification -> notifyPlayers());
        gameHandler.addRoundUpdateCallback(this::updateTable);
        gameHandler.addRoundUpdateCallback(this::reconfigureRaiseButton);
        gameHandler.addRoundUpdateCallback(roundUpdateNotification -> updatePlayersInformation(roundUpdateNotification.getCurrentPlayers(), roundUpdateNotification.getCurrentPlayerName(), roundUpdateNotification.isFinished(), roundUpdateNotification.getWinnerPlayers()));
        gameHandler.addRoundUpdateCallback(roundUpdateNotification -> reconfigureStartButton(
                roundUpdateNotification.getCurrentPlayers(),
                !roundUpdateNotification.isFinished()));

        gameHandler.addMoveUpdateCallback(this::updateGameMoves);

        gameHandler.addChatUpdateCallback(this::updateChatWindow);

        raiseAmountSpinner.setModel(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) raiseAmountSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        chatScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));

        startGameButton.addActionListener(e -> onStart());

        foldButton.addActionListener(e -> onFold());
        callButton.addActionListener(e -> onCall());
        raiseButton.addActionListener(e -> onRaise());
        checkButton.addActionListener(e -> onCheck());

        sendButton.addActionListener(e -> onSend());
        contentPane.registerKeyboardAction(e -> onSend(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void generateUserInformation(ClientUserProfile user){
        logger.info("Updating user information on header: {}.", user);
        usernameLabel.setText("Name: " + user.getUsername());
        cashLabel.setText("Balance: " + String.valueOf(user.getBalance()));
    }

    private void updatePlayersInformation(List<ClientPlayer> players, String currentPlayerName, boolean isFinished, List<ClientPlayer> winners){
        logger.info("Updating players information on table. Current player: {}, players: {}.", currentPlayerName, players);
        seats.forEach(Container::removeAll);
        chatComboBox.removeAllItems();

        for (int i = 0; i < players.size(); i++) {
            ClientPlayer clientPlayer = players.get(i);
            Player player = new Player(clientPlayer);
            if(isClientPlaying(clientPlayer)){
                player.self();
            }
            if(isTurnOf(clientPlayer, currentPlayerName)){
                player.markAsCurrent();
            }
            if(isClientPlaying(clientPlayer) && !isFinished){
                player.showCards();
            }
            if(isFinished){
                player.showCards();
                if(winners.contains(clientPlayer)){
                    player.markAsWinner();
                }
            }
            JPanel playerComponent = player.getContainer();
            seats.get(i % 4).add(playerComponent);
        }

        chatComboBox.addItem("All");
        for(ClientPlayer player : players){
            chatComboBox.addItem(player.getPlayerName());
        }
        pack();
        //revalidate();
    }

    private void updatePlayersInformation(List<ClientPlayer> players) {
        updatePlayersInformation(players, null, false, new ArrayList<>());
    }

    private void notifyPlayers(){
        logger.info("Attempting to notify players of the new event.");
        eventsPanel.removeAll();
        for(String message : gameHandler.getNotificationMessages()){
            eventsPanel.add(new JLabel(message));
        }
        pack();
        revalidate();
    }


    private void updateTable(RoundUpdateNotification roundUpdateNotification){
        logger.info("Updating table: {}", roundUpdateNotification);
        cardsPanel.removeAll();
        potLabel.setText("Pot size: " + String.valueOf(roundUpdateNotification.getCurrentPotSize()));
        List<ClientCard> tableCards = roundUpdateNotification.getCurrentOpenedCards();

        for(ClientCard card : tableCards){
            cardsPanel.add(new JLabel(CardsImages.getImage(card)));
        }
        pack();
        revalidate();
    }

    private void reconfigureRaiseButton(RoundUpdateNotification roundUpdateNotification){
        logger.info("Determining raise button values: {}", roundUpdateNotification);
        int maxBet = roundUpdateNotification.getCurrentPlayers().stream()
                .map(ClientPlayer::getLastBetSinceCardOpen)
                .max(Integer::compareTo)
                .orElse(0);
        int minRaise = maxBet + gameHandler.getGameDetails().getMinimumBet();

        raiseAmountSpinner.setModel(new SpinnerNumberModel(minRaise, minRaise , Integer.MAX_VALUE, 1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) raiseAmountSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }

    private void updateChatWindow(ChatNotification chatNotification){
        chatPanel.setText(chatPanel.getText() + (chatNotification.isPrivate() ? "whisper from " : "" ) +
                chatNotification.getSenderUserName() + ": " + chatNotification.getMessageContent() + "\n");
    }

    private void updateGameMoves(List<Move> possibleMoves){
        foldButton.setEnabled(possibleMoves.contains(Move.FOLD));
        callButton.setEnabled(possibleMoves.contains(Move.CALL));
        raiseButton.setEnabled(possibleMoves.contains(Move.RAISE));
        checkButton.setEnabled(possibleMoves.contains(Move.CHECK));
    }

    private void onStart(){
        try{
            gameHandler.startGame();
        } catch (GameException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSend(){
        try{
            if(chatComboBox.getSelectedItem().equals("All")) {
                gameHandler.sendMessage(chatTextField.getText());
            } else{
                gameHandler.sentPrivateMessage(chatTextField.getText(), (String)chatComboBox.getSelectedItem());
            }
            chatTextField.setText("");
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onFold(){
        try{
            gameHandler.playFold();
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCall(){
        try {
            gameHandler.playCall();
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRaise(){
        try{
            gameHandler.playRaise(raiseAmountSpinner.getValue().toString());
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCheck(){
        try {
            gameHandler.playCheck();
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onExit() {
        try {
            ancestor.removeGame(this);
            MenuHandler.getInstance().leaveGame(SessionHandler.getInstance().user().getUsername(), gameHandler.getGameDetails().getName());
            dispose();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isTurnOf(ClientPlayer player, String currentPlayerName){
        return player.getPlayerName().equals(currentPlayerName);
    }

    private boolean isClientPlaying(ClientPlayer ... players){
        return isClientPlaying(Arrays.asList(players));
    }

    private boolean isClientPlaying(List<ClientPlayer> players){
        return players.stream().anyMatch(player -> SessionHandler.getInstance().user().getUsername().equals(player.getPlayerName()));
    }

    private void disableGameActions(){
        foldButton.setEnabled(false);
        callButton.setEnabled(false);
        raiseButton.setEnabled(false);
        checkButton.setEnabled(false);
    }

    private void reconfigureStartButton(List<ClientPlayer> players, boolean isGameRunning){
        boolean isClientPlaying = isClientPlaying(players);
        logger.info("Configuring start button: client is player: {}, Game running: {}", isClientPlaying, isGameRunning);
        startGameButton.setEnabled(isClientPlaying && !isGameRunning);
    }
}
