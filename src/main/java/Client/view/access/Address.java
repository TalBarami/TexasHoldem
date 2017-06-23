package Client.view.access;

import Client.communication.GameRequestHandler;
import Client.communication.ReplayRequestHandler;
import Client.communication.SessionRequestHandler;
import Client.communication.UserRequestHandler;
import Client.domain.SessionHandler;
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

        // FIXME: Remove before submission!
        IPAddressTextField.setText("localhost:8080");
    }

    private void init(){
        ClientUtils.frameInit(this, contentPane);
        setLocationRelativeTo(null);
        setTitle("Texas Hold'em");
    }

    public void assignActionListeners(){
        enterButton.addActionListener(e -> onEnter());
    }

    public void onEnter(){
        SessionHandler.getInstance().setIpAddress(IPAddressTextField.getText());

        GameRequestHandler.serviceURI = "http://" + SessionHandler.getInstance().getIpAddress() + "/game";
        SessionRequestHandler.serviceURI = "http://" + SessionHandler.getInstance().getIpAddress() + "/session";
        UserRequestHandler.serviceURI = "http://" + SessionHandler.getInstance().getIpAddress() + "/user";
        ReplayRequestHandler.serviceURI = "http://" + SessionHandler.getInstance().getIpAddress() + "/replay";

        Welcome w = new Welcome();
        dispose();
    }
}
