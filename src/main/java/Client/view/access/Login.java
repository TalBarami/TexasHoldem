package Client.view.access;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.domain.SessionManager;
import Client.view.ClientUtils;
import Client.view.system.MainMenu;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.event.KeyEvent;

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
        ancestor.getRootPane().setDefaultButton(buttonOK);
    }

    private void onOK() {
        try {
            SessionManager.getInstance().login(text_username.getText(), new String(text_password.getPassword()));
            MainMenu menu = new MainMenu();
            ancestor.dispose();
        } catch (LoginException | EntityDoesNotExistsException | InvalidArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        ancestor.init();
    }
}
