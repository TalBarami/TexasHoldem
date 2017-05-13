package Client.View.ClientAccess;


import Client.View.ClientAccess.Login;
import Client.View.ClientAccess.Register;

import javax.swing.*;
import java.awt.event.*;

public class Welcome extends JFrame {
    private Login login;
    private Register register;

    private JPanel contentPane;
    private JButton buttonLogin;
    private JButton buttonRegister;

    public Welcome() {
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(buttonLogin);

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
        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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
        setContentPane(contentPane);
        revalidate();
    }

    public void onExit(){
        dispose();
    }
}
