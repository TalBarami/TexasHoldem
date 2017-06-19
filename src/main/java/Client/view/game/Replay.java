package Client.view.game;

import Client.view.system.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by User on 18/06/2017.
 */
public class Replay extends JDialog {
    private MainMenu ancestor;

    private JPanel contentPane;
    private JPanel replayPanel;
    private JButton closeButton;

    public Replay(MainMenu ancestor){
        this.ancestor = ancestor;

        setContentPane(contentPane);
        setTitle("Deposit");
        getRootPane().setDefaultButton(closeButton);

        assignActionListeners();
        displayGame();
    }

    private void assignActionListeners(){
        closeButton.addActionListener(e -> onClose());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void displayGame(){

    }

    private void onClose(){
        dispose();
    }
}
