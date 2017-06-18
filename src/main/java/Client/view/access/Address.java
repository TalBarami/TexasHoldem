package Client.view.access;

import Client.communication.GameRequestHandler;
import Client.communication.ReplayRequestHandler;
import Client.communication.SessionRequestHandler;
import Client.communication.UserRequestHandler;
import Client.domain.SessionManager;
import Client.ClientUtils;

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

        GameRequestHandler.serviceURI = "http://" + SessionManager.getInstance().getIpAddress() + "/game";
        SessionRequestHandler.serviceURI = "http://" + SessionManager.getInstance().getIpAddress() + "/session";
        UserRequestHandler.serviceURI = "http://" + SessionManager.getInstance().getIpAddress() + "/user";
        ReplayRequestHandler.serviceURI = "http://" + SessionManager.getInstance().getIpAddress() + "/replay";

        Welcome w = new Welcome();
        dispose();
    }
}
