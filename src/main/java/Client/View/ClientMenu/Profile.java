package Client.View.ClientMenu;

import Client.View.ClientAccess.ClientUtils;
import TexasHoldem.domain.user.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JTextField newEMailTextField;
    private JTextField repeatEMailTextField;
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

    public Profile(MainMenu ancestor) {
        this.ancestor = ancestor;

        buttonApply.addActionListener(e -> onApply());

        buttonCancel.addActionListener(e -> onCancel());

        buttonBrowse.addActionListener(e -> onBrowse());

        newPasswordPasswordField.getDocument().addDocumentListener(textChangeListener());
        repeatPasswordPasswordField.getDocument().addDocumentListener(textChangeListener());
        newEMailTextField.getDocument().addDocumentListener(textChangeListener());
        repeatEMailTextField.getDocument().addDocumentListener(textChangeListener());

        generateUserInformation();
    }

    public void init(){
        ancestor.setContentPane(contentPane);
        ancestor.getRootPane().setDefaultButton(buttonApply);
        ancestor.revalidate();
    }

    public void generateUserInformation(){
        User user = ancestor.user;
        ImageIcon icon = new ImageIcon(user.getImg().getScaledInstance(200, 200, 0));
        label_userPicture.setText("");
        label_userPicture.setIcon(icon);
        label_name.setText(label_name.getText() + user.getUsername());
        label_birthday.setText(label_birthday.getText() + user.getDateOfBirth());
        label_email.setText(label_email.getText() + user.getEmail());
        label_cash.setText(label_cash.getText() + user.getBalance());
        label_totalPlayed.setText(label_totalPlayed.getText() + user.getNumOfGamesPlayed());
        label_league.setText(label_league.getText() + user.getCurrLeague());
        label_earned.setText(label_earned.getText() + user.getAmountEarnedInLeague());
    }

    private void onApply() {
        // add your code here
    }

    private void onCancel() {
        ancestor.init();
    }

    private void onBrowse() {
        ClientUtils.browseFile(newPictureTextField);
    }

    private void onTextChange(){
        if(Arrays.equals(newPasswordPasswordField.getPassword(), repeatPasswordPasswordField.getPassword()) &&
                newEMailTextField.getText().equals(repeatEMailTextField.getText())){
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
}
