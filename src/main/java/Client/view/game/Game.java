package Client.view.game;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.GameException;
import Client.common.exceptions.InvalidArgumentException;
import Client.domain.GameManager;
import Client.domain.MenuManager;
import Client.domain.SessionManager;
import Client.view.ClientUtils;
import Client.view.system.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private JTextField textField1;
    private JTextPane textPane1;
    private JButton sendButton;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private JPanel contentPane;
    private JButton startGameButton;

    public Game(MainMenu ancestor, String gameName) throws EntityDoesNotExistsException, InvalidArgumentException {
        this.ancestor = ancestor;
        gameManager = new GameManager(gameName);

        init();

        assignActionListeners();

    }

    public void init(){
        toFront();
        ClientUtils.frameInit(this, contentPane);
        getRootPane().setDefaultButton(sendButton);
    }

    private void assignActionListeners(){

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
            MenuManager.getInstance().leaveGame(gameManager.getUser().getUsername(), gameManager.getGameDetails().getName());
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
}
