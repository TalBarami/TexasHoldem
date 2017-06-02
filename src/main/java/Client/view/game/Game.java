package Client.view.game;

import Client.domain.SessionManager;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    private List<JPanel> seats = Arrays.asList(bottomPanel, leftPanel, topPanel, rightPanel);

    public Game(MainMenu ancestor, String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        this.ancestor = ancestor;
        gameManager = new GameManager(gameName);

        init();

        assignActionListeners();
        generateUserInformation(SessionManager.getInstance().user());
        updateTable(gameManager.getGameDetails());

    }

    public void init(){
        toFront();
        ClientUtils.frameInit(this, contentPane);
        setTitle(gameManager.getGameDetails().getName());
        getRootPane().setDefaultButton(sendButton);
    }


    private void updatePlayersInformation(List<ClientPlayer> players) throws NullPointerException {
        seats.forEach(Container::removeAll);

        for(int i=0; i<players.size(); i++){
            ClientPlayer player = players.get(i);
            JPanel container = new JPanel();
            JLabel name = new JLabel(player.getPlayerName());
            JLabel chipAmount = new JLabel(String.valueOf(player.getChipAmount()));
            container.add(name);
            container.add(chipAmount);
            if(player.getPlayerName().equals(SessionManager.getInstance().user().getUsername())){
                JLabel cards = new JLabel(player.getPlayerCards().toString());
                container.add(cards);
            }
            seats.get(i%4).add(container);
            // FIXME: Mark "current player" (and maybe "client player"?)
        }
    }

    private void updateTable(ClientGameDetails gameDetails){
        updatePlayersInformation(gameDetails.getPlayerList());

        // FIXME: Add pot & cards.
    }

    private void updateChatWindow(){
        // FIXME: Upon receiving chat message, add it to the TextArea
    }

    private void generateUserInformation(ClientUserProfile user){
        usernameLabel.setText(user.getUsername());
        cashLabel.setText(String.valueOf(user.getBalance()));
    }

    private void assignActionListeners(){
        SessionManager.getInstance().addUpdateCallback(this::generateUserInformation);

        gameManager.addGameUpdateCallback(this::updateTable);


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

    private void onSend(){
        try{
            gameManager.sendMessage(chatTextField.getText());
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onFold(){
        try{
            gameManager.playFold();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCall(){
        try {
            gameManager.playCall();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRaise(){
        try{
            gameManager.playRaise("FIXME!");
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCheck(){
        try {
            gameManager.playCheck();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onExit() {
        try {
            MenuManager.getInstance().leaveGame(SessionManager.getInstance().user().getUsername(), gameManager.getGameDetails().getName());
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
}
