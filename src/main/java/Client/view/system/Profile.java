package Client.view.system;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.communication.entities.ClientUserProfile;
import Client.domain.SessionManager;
import Client.view.ClientUtils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * Created by User on 13/05/2017.
 */
public class Profile {
    public MainMenu ancestor;

    public JPanel contentPane;
    private JButton buttonApply;
    private JButton buttonCancel;
    private JPasswordField oldPasswordPasswordField;
    private JPasswordField newPasswordPasswordField;
    private JPasswordField repeatPasswordPasswordField;
    private JTextField newEmailTextField;
    private JTextField repeatEmailTextField;
    private JTextField newPictureTextField;
    private JButton buttonBrowse;
    private JLabel label_userPicture;
    private JLabel label_birthday;
    private JLabel label_name;
    private JLabel label_email;
    private JLabel label_cash;
    private JLabel label_league;
    private JLabel label_totalPlayed;
    private JLabel label_earned;
    private JDatePickerImpl newBirthdayDatePicker;

    public Profile(MainMenu ancestor) {
        this.ancestor = ancestor;

        assignActionListeners();

    }

    public void init(){
        ClientUtils.frameInit(ancestor, contentPane);
        generateUserInformation();
        ancestor.getRootPane().setDefaultButton(buttonApply);
    }

    private void assignActionListeners(){
        buttonApply.addActionListener(e -> onApply());

        buttonCancel.addActionListener(e -> onCancel());

        buttonBrowse.addActionListener(e -> onBrowse());

        newPasswordPasswordField.getDocument().addDocumentListener(textChangeListener());
        repeatPasswordPasswordField.getDocument().addDocumentListener(textChangeListener());
        newEmailTextField.getDocument().addDocumentListener(textChangeListener());
        repeatEmailTextField.getDocument().addDocumentListener(textChangeListener());

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void generateUserInformation(){
        ClientUserProfile user = SessionManager.getInstance().user();
        /*ImageIcon icon = new ImageIcon(user.getImg().getScaledInstance(200, 200, 0));*/
        label_userPicture.setText("");
        /*label_userPicture.setIcon(icon);*/
        label_name.setText("Name: " + user.getUsername());
        label_birthday.setText("Birthday: " + user.getDayOfBirth() + "/" + user.getMonthOfBirth() + "/" + user.getYearOfBirth());
        label_email.setText("E-mail: " + user.getEmail());
        label_cash.setText("Total cash: " + user.getBalance());
        label_totalPlayed.setText("Games played: " + user.getNumOfGamesPlayed());
        label_league.setText("League: " + user.getLeague());
        label_earned.setText("Total earned in league: " + user.getAmountEarnedInLeague());
    }

    private void onApply() {
        try {
            SessionManager.getInstance().editProfile(new String(newPasswordPasswordField.getPassword()), newEmailTextField.getText(), newBirthdayDatePicker.getJFormattedTextField().getText(), newPictureTextField.getText());
            ancestor.init();
        } catch (InvalidArgumentException | EntityDoesNotExistsException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        ancestor.init();
    }

    private void onBrowse() {
        ClientUtils.browseFile(newPictureTextField);
    }

    private void onTextChange(){
        if(Arrays.equals(newPasswordPasswordField.getPassword(), repeatPasswordPasswordField.getPassword()) &&
                newEmailTextField.getText().equals(repeatEmailTextField.getText())){
            buttonApply.setEnabled(true);
        } else {
            buttonApply.setEnabled(false);
        }
    }

    private DocumentListener textChangeListener(){
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChange();
            }
        };
    }

    private void createUIComponents() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        newBirthdayDatePicker = new JDatePickerImpl(datePanel);
    }
}