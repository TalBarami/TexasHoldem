package Client.view.access;


import Client.domain.user.SessionManager;
import Client.view.ClientUtils;

import javax.swing.*;
import java.awt.event.*;

public class Welcome extends JFrame {
    private Login login;
    private Register register;
    private SessionManager sessionManager;

    private JPanel contentPane;
    private JButton buttonLogin;
    private JButton buttonRegister;

    public Welcome() {
        sessionManager = new SessionManager();

        init();

        getRootPane().setDefaultButton(buttonLogin);

        assignActionListeners();
    }

    private void assignActionListeners(){
        buttonLogin.addActionListener(e -> onLogin());

        buttonRegister.addActionListener(e -> onRegister());

        // call onExit() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        // call onExit() on ESCAPE
        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void onLogin() {
        // add your code here
        if(login == null)
            login = new Login(this);
        login.init();
    }

    private void onRegister() {
        // add your code here if necessary
        if(register == null)
            register = new Register(this);
        register.init();
    }

    public void init(){
        ClientUtils.frameInit(this, contentPane);
    }

    private void onExit(){
        dispose();
    }

    public SessionManager sessionManager(){
        return sessionManager;
    }
}
