package Client.view.system;

import MutualJsonObjects.ClientUserProfile;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;

import Client.domain.SessionHandler;
import Client.ClientUtils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by User on 13/05/2017.
 */
public class Profile {
    public MainMenu ancestor;

    public JPanel contentPane;
    private JButton buttonApply;
    private JButton buttonCancel;
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

        generateUserInformation(SessionHandler.getInstance().user());
        assignActionListeners();
    }

    public void init(){
        ClientUtils.frameInit(ancestor, contentPane);
        ancestor.getRootPane().setDefaultButton(buttonApply);
        ClientUtils.clearTextFields(newPasswordPasswordField, repeatPasswordPasswordField, newEmailTextField, repeatEmailTextField, newBirthdayDatePicker.getJFormattedTextField(), newPictureTextField);
    }

    private void assignActionListeners(){
        SessionHandler.getInstance().addUpdateCallback(this::generateUserInformation);

        buttonApply.addActionListener(e -> onApply());

        buttonCancel.addActionListener(e -> onCancel());

        buttonBrowse.addActionListener(e -> onBrowse());

        newPasswordPasswordField.getDocument().addDocumentListener(textChangeListener());
        repeatPasswordPasswordField.getDocument().addDocumentListener(textChangeListener());
        newEmailTextField.getDocument().addDocumentListener(textChangeListener());
        repeatEmailTextField.getDocument().addDocumentListener(textChangeListener());

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void generateUserInformation(ClientUserProfile profile){
        label_userPicture.setText("");
        label_userPicture.setIcon(ClientUtils.getProfileImage(profile.getImage(), 200, 200));
        label_name.setText("Name: " + profile.getUsername());
        label_birthday.setText("Birthday: " + profile.getDayOfBirth() + "/" + profile.getMonthOfBirth() + "/" + profile.getYearOfBirth());
        label_email.setText("E-mail: " + profile.getEmail());
        label_cash.setText("Total cash: " + profile.getBalance());
        label_totalPlayed.setText("Games played: " + profile.getNumOfGamesPlayed());
        label_league.setText("League: " + profile.getCurrLeague());
        label_earned.setText("Total earned in league: " + profile.getAmountEarnedInLeague());
    }

    private void onApply() {
        try {
            SessionHandler.getInstance().editProfile(new String(newPasswordPasswordField.getPassword()), newEmailTextField.getText(), newBirthdayDatePicker.getJFormattedTextField().getText(), newPictureTextField.getText());
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
