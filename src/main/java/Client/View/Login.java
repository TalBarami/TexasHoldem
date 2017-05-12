package Client.View;

import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame {
    public JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField Text_username;
    private JPasswordField Text_password;
    private JLabel Label_username;
    private JLabel Label_password;

    public Login() {
        setContentPane(contentPane);
        //setModal(true);
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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void initialize() {
        pack();
        setVisible(true);
    }
}
