package Client.view.access;

import Client.common.exceptions.InvalidArgumentException;
import Client.domain.SessionManager;
import Client.view.ClientUtils;
import Client.view.system.MainMenu;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.KeyEvent;

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
    private JDatePickerImpl datePicker_birthday;

    public Register(Welcome ancestor) {
        this.ancestor = ancestor;

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        button_picture.addActionListener(e -> onBrowse());

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void init(){
        ClientUtils.frameInit(ancestor, contentPane);
        ancestor.getRootPane().setDefaultButton(buttonOK);
        ClientUtils.clearTextFields(text_name, text_password, text_email, datePicker_birthday.getJFormattedTextField(), text_picture);
    }

    private void onOK() {
        try {
            SessionManager.getInstance().register(text_name.getText(), new String(text_password.getPassword()), text_email.getText(), datePicker_birthday.getJFormattedTextField().getText(), text_picture.getText());
            MainMenu menu = new MainMenu();
            ancestor.dispose();
        } catch (InvalidArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        datePicker_birthday = new JDatePickerImpl(datePanel);
    }
}
