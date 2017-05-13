package Client.View.SystemView;

import javax.swing.*;
import java.awt.event.*;

public class JoinGame extends JDialog {
    private MainMenu ancestor;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public JoinGame(MainMenu ancestor) {
        this.ancestor = ancestor;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        assignActionListeners();
    }

    private void assignActionListeners(){
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
