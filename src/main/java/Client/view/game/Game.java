package Client.view.game;

import Client.view.ClientUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by User on 14/05/2017.
 */
public class Game extends JFrame{

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

    public Game(){
        assignActionListeners();

    }

    public void init(){
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
    }

    private void onSend(){

    }

    private void onFold(){

    }

    private void onCall(){

    }

    private void onRaise(){

    }

    private void onCheck(){

    }

    public void onExit(){
        dispose();
    }
}
