package Client.View.AccessView;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;

public class Register {
    private Welcome ancestor;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField text_name;
    private JTextField text_email;
    private JLabel Label_username;
    private JLabel Label_password;
    private JLabel Label_email;
    private JPasswordField text_password;
    private JLabel Label_birthdate;
    private JLabel Label_picture;
    private JTextField text_picture;
    private JButton button_picture;
    private JDatePickerImpl datePicker_birthdate;

    public Register(Welcome ancestor) {
        this.ancestor = ancestor;

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        button_picture.addActionListener(e -> onBrowse());
    }

    public void init(){
        ClientUtils.frameInit(ancestor, contentPane);
        ancestor.getRootPane().setDefaultButton(buttonOK);
    }

    private void onOK() {
        // add your code here
    }

    private void onCancel() {
        ancestor.init();
    }

    private void onBrowse(){
        ClientUtils.browseFile(text_picture);
    }

    private void createUIComponents() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker_birthdate = new JDatePickerImpl(datePanel);
    }
}
