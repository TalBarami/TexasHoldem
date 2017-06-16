package Client.view.access;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;

import Client.domain.SessionManager;
import Client.ClientUtils;
import Client.view.system.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutionException;

public class Login {
    public Welcome ancestor;
    public JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField text_username;
    private JPasswordField text_password;
    private JLabel Label_username;
    private JLabel Label_password;

    public Login(Welcome ancestor) {
        this.ancestor = ancestor;

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void init(){
        ClientUtils.frameInit(ancestor, contentPane);
        ancestor.setTitle("Login");
        ancestor.getRootPane().setDefaultButton(buttonOK);
        ClientUtils.clearTextFields(text_username, text_password);
    }

    private void onOK() {
        try {
            SessionManager.getInstance().login(text_username.getText(), new String(text_password.getPassword()));
            MainMenu menu = new MainMenu();
            ancestor.dispose();
        } catch (LoginException | EntityDoesNotExistsException | InvalidArgumentException | ExecutionException | InterruptedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        ancestor.init();
    }
}
