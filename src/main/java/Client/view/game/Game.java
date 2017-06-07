package Client.view.game;

import Client.domain.SessionManager;
import Enumerations.Move;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.GameException;
import Exceptions.InvalidArgumentException;
import MutualJsonObjects.ClientGameDetails;
import MutualJsonObjects.ClientPlayer;
import MutualJsonObjects.ClientUserProfile;

import Client.domain.GameManager;
import Client.domain.MenuManager;
import Client.view.ClientUtils;
import Client.view.system.MainMenu;
import NotificationMessages.ChatNotification;
import NotificationMessages.GameUpdateNotification;
import NotificationMessages.RoundUpdateNotification;
import Server.domain.game.GameActions;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by User on 14/05/2017.
 */
public class Game extends JFrame{
    private MainMenu ancestor;
    private GameManager gameManager;

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
    private JLabel cardsLabel;
    private JSpinner raiseAmountSpinner;
    private JScrollPane chatScrollPane;
    private JComboBox<String> chatComboBox;
    private JLabel eventsLabel;

    private List<JPanel> seats = Arrays.asList(bottomPanel, leftPanel, topPanel, rightPanel);

    public Game(MainMenu ancestor, String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        this.ancestor = ancestor;
        gameManager = new GameManager(gameName);

        disableGameActions();
        initializeSeats();
        assignActionListeners();
        initializeGame(gameManager.getGameDetails());
    }

    public void init(){
        toFront();
        ClientUtils.frameInit(this, contentPane);
        setTitle(gameManager.getGameDetails().getName());
        getRootPane().setDefaultButton(sendButton);
        setSize(800, 600);
    }

    private void initializeGame(ClientGameDetails gameDetails){
        generateUserInformation(SessionManager.getInstance().user());
        updatePlayersInformation(gameDetails.getPlayerList());
        potLabel.setText("0");
        cardsLabel.setText("");
        eventsLabel.setText("");
    }

    private void initializeSeats(){
        bottomPanel.setLayout(new GridLayout(1, 0));
        leftPanel.setLayout(new GridLayout(0, 1));
        topPanel.setLayout(new GridLayout(1, 0));
        rightPanel.setLayout(new GridLayout(0, 1));
    }

    private void assignActionListeners(){
        SessionManager.getInstance().addUpdateCallback(this::generateUserInformation);

        gameManager.addGameUpdateCallback(this::updateTable);
        gameManager.addRoundUpdateCallback(this::updateTable);
        gameManager.addMoveUpdateCallback(this::updateGameMoves);
        gameManager.addChatUpdateCallback(this::updateChatWindow);

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
        usernameLabel.setText("Name: " + user.getUsername());
        cashLabel.setText("Balance: " + String.valueOf(user.getBalance()));
    }

    private void updatePlayersInformation(List<ClientPlayer> players, String currentPlayerName){
        seats.forEach(Container::removeAll);
        chatComboBox.removeAllItems();

        for (int i = 0; i < players.size(); i++) {
            ClientPlayer clientPlayer = players.get(i);
            Player player = new Player(clientPlayer);
            if(clientPlayer.getPlayerName().equals(currentPlayerName)){
                player.mark();
            }
            if(clientPlayer.getPlayerName().equals(SessionManager.getInstance().user().getUsername())){
                player.showCards();
                player.self();
            }
            JPanel playerComponent = player.getContainer();
            seats.get(i % 4).add(playerComponent);
        }

        chatComboBox.addItem("All");
        for(ClientPlayer player : players){
            chatComboBox.addItem(player.getPlayerName());
        }
    }

    private void updatePlayersInformation(List<ClientPlayer> players) {
        updatePlayersInformation(players, null);
    }

    private void updateTable(GameUpdateNotification gameUpdateNotification){
        updatePlayersInformation(gameUpdateNotification.getGameDetails().getPlayerList());
        String action;
        switch(gameUpdateNotification.getAction()){
            case ENTER:
                action = "joined";
                break;
            case EXIT:
                action = "left";
                break;
            case NEWROUND:
                action = "started";
                startGameButton.setEnabled(false);
                break;
            // TODO: Handle case when round is finished.
            default:
                JOptionPane.showMessageDialog(null, "Undefined event occurred: " + gameUpdateNotification.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }
        eventsLabel.setText(String.format("%s has %s the game.", gameUpdateNotification.getGameActionInitiator(), action));
    }

    private void updateTable(RoundUpdateNotification roundUpdateNotification){
        updatePlayersInformation(roundUpdateNotification.getCurrentPlayers(), roundUpdateNotification.getCurrentPlayerName());

        potLabel.setText(String.valueOf(roundUpdateNotification.getCurrentPotSize()));
        cardsLabel.setText(roundUpdateNotification.getCurrentOpenedCards().toString());

        int maxBet = roundUpdateNotification.getCurrentPlayers().stream()
                .map(ClientPlayer::getLastBetSinceCardOpen)
                .max(Integer::compareTo)
                .orElse(0);
        int minRaise = maxBet + gameManager.getGameDetails().getMinimumBet();

        raiseAmountSpinner.setModel(new SpinnerNumberModel(minRaise, minRaise , Integer.MAX_VALUE, 1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) raiseAmountSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        eventsLabel.setText(String.format("It's %s turn.", roundUpdateNotification.getCurrentPlayerName()));
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
            gameManager.startGame();
        } catch (GameException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSend(){
        try{
            if(chatComboBox.getSelectedItem().equals("All")) {
                gameManager.sendMessage(chatTextField.getText());
            } else{
                gameManager.sentPrivateMessage(chatTextField.getText(), (String)chatComboBox.getSelectedItem());
            }
            chatTextField.setText("");
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onFold(){
        try{
            gameManager.playFold();
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCall(){
        try {
            gameManager.playCall();
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRaise(){
        try{
            gameManager.playRaise(raiseAmountSpinner.getValue().toString());
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCheck(){
        try {
            gameManager.playCheck();
            disableGameActions();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onExit() {
        try {
            ancestor.removeGame(this);
            MenuManager.getInstance().leaveGame(SessionManager.getInstance().user().getUsername(), gameManager.getGameDetails().getName());
            dispose();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void disableGameActions(){
        foldButton.setEnabled(false);
        callButton.setEnabled(false);
        raiseButton.setEnabled(false);
        checkButton.setEnabled(false);
    }
}
