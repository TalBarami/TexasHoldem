package Client.View.AccessView;

import javax.swing.*;

public class Login {
    public Welcome ancestor;
    public JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField Text_username;
    private JPasswordField Text_password;
    private JLabel Label_username;
    private JLabel Label_password;

    public Login(Welcome ancestor) {
        this.ancestor = ancestor;

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());
    }

    public void init(){
        ClientUtils.frameInit(ancestor, contentPane);
        ancestor.getRootPane().setDefaultButton(buttonOK);
    }

    private void onOK() {

    }

    private void onCancel() {
        ancestor.init();
    }
}