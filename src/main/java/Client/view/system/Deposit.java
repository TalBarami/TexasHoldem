package Client.view.system;

import Exceptions.ArgumentNotInBoundsException;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Client.domain.MenuManager;
import Client.domain.SessionManager;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;

public class Deposit extends JDialog {
    private MainMenu ancestor;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner amountSpinner;

    public Deposit(MainMenu ancestor) {
        this.ancestor = ancestor;

        setContentPane(contentPane);
        setTitle("Deposit");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        amountSpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) amountSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }

    private void onOK() {
        try {
            String username = SessionManager.getInstance().user().getUsername();
            MenuManager gameManager = MenuManager.getInstance();
            ancestor.init();
            dispose();
            gameManager.deposit(username, (int)amountSpinner.getValue());
        } catch (InvalidArgumentException | ArgumentNotInBoundsException | EntityDoesNotExistsException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
