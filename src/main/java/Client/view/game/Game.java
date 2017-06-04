package Client.view.game;

import Client.domain.SessionManager;
import Client.domain.callbacks.ChatUpdateCallback;
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
import NotificationMessages.MessageNotification;
import NotificationMessages.RoundUpdateNotification;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
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

    private List<JPanel> seats = Arrays.asList(bottomPanel, leftPanel, topPanel, rightPanel);

    public Game(MainMenu ancestor, String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        this.ancestor = ancestor;
        gameManager = new GameManager(gameName);

        initializeSeats();
        assignActionListeners();
        initializeGame(gameManager.getGameDetails());
    }

    public void initializeGame(ClientGameDetails gameDetails){
        generateUserInformation(SessionManager.getInstance().user());
        updatePlayersInformation(gameDetails.getPlayerList());
        potLabel.setText("0");
        cardsLabel.setText("");
    }

    public void init(){
        toFront();
        ClientUtils.frameInit(this, contentPane);
        setTitle(gameManager.getGameDetails().getName());
        getRootPane().setDefaultButton(sendButton);
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
        gameManager.addMoveUpdateCallback(this::updateGameMoves);
        gameManager.addChatUpdateCallback(this::updateChatWindow);

        raiseAmountSpinner.setModel(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) raiseAmountSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        chatScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));

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
        usernameLabel.setText(user.getUsername());
        cashLabel.setText(String.valueOf(user.getBalance()));
    }

    private void updatePlayersInformation(List<ClientPlayer> players) throws NullPointerException {
        seats.forEach(Container::removeAll);
        chatComboBox.removeAllItems();

        for (int i = 0; i < players.size(); i++) {
            ClientPlayer player = players.get(i);
            JPanel playerComponent = new Player(player).getContainer();
            seats.get(i % 4).add(playerComponent);
            // FIXME: Mark "current player" (and maybe "client player"?)
        }

        chatComboBox.addItem("All");
        for(ClientPlayer player : players){
            chatComboBox.addItem(player.getPlayerName());
        }
    }

    private void updateTable(RoundUpdateNotification roundUpdateNotification){
        updatePlayersInformation(roundUpdateNotification.getCurrentPlayers());

        // FIXME: Add pot & cards.
    }

    private void updateChatWindow(MessageNotification messageNotification){
        chatPanel.setText(chatPanel.getText() + messageNotification.getSenderUserName() + ": " + messageNotification.getMessageContent() + "\n");
    }

    private void updateGameMoves(List<Move> possibleMoves){
        foldButton.setEnabled(possibleMoves.contains(Move.FOLD));
        callButton.setEnabled(possibleMoves.contains(Move.CALL));
        raiseButton.setEnabled(possibleMoves.contains(Move.RAISE));
        checkButton.setEnabled(possibleMoves.contains(Move.CHECK));
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
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    public void disableGameActions(){
        foldButton.setEnabled(false);
        callButton.setEnabled(false);
        raiseButton.setEnabled(false);
        checkButton.setEnabled(false);
    }
}
