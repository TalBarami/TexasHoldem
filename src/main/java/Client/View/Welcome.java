package Client.View;


import javax.swing.*;
import java.awt.event.*;

public class Welcome extends JFrame {
    private JPanel contentPane;
    private JButton buttonLogin;
    private JButton buttonRegister;

    public Welcome() {
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(buttonLogin);

        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        buttonRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRegister();
            }
        });

        // call onExit() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        // call onExit() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onLogin() {
        // add your code here
        Login login = new Login();
        login.pack();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        login.initialize();
        dispose();
    }

    private void onRegister() {
        // add your code here if necessary
        Register register = new Register();
        register.pack();
        register.setLocationRelativeTo(null);
        register.setVisible(true);
        register.initialize();
        dispose();
    }

    private void onExit(){
        dispose();
    }
}
