package Client.view.access;

import Client.domain.SessionManager;
import Client.view.ClientUtils;

import javax.swing.*;

/**
 * Created by Tal on 06/06/2017.
 */
public class Address extends JFrame {
    private JTextField IPAddressTextField;
    private JPanel contentPane;
    private JButton enterButton;

    public Address(){
        init();

        getRootPane().setDefaultButton(enterButton);

        assignActionListeners();
    }

    private void init(){
        ClientUtils.frameInit(this, contentPane);
        setTitle("Texas Hold'em");
    }

    public void assignActionListeners(){
        enterButton.addActionListener(e -> onEnter());
    }

    public void onEnter(){
        SessionManager.getInstance().setIpAddress(IPAddressTextField.getText());
        Welcome w = new Welcome();
        dispose();
    }
}
