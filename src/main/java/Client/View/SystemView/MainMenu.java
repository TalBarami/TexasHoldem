package Client.View.SystemView;

import Client.View.AccessView.Welcome;
import TexasHoldem.domain.user.User;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by User on 12/05/2017.
 */
public class MainMenu extends JFrame {
    public User user;

    private Profile profile;
    private CreateGame createGame;

    private JLabel label_name;
    private JLabel label_cash;
    private JLabel label_picture;
    private JPanel contentPane;
    private JButton profileButton;
    private JLabel label_league;
    private JTable gamesTable;
    private JButton createNewGameButton;
    private JButton joinSelectedGameButton;
    private JTextField searchTextField;
    private JComboBox filterComboBox;
    private JCheckBox onlyAvailableCheckBox;
    private JButton logoutButton;
    private JButton replaySelectedGameButton;
    private JButton spectateSelectedGameButton;

    public MainMenu(User user) {
        this.user = user;
        setContentPane(contentPane);

        generateUserInformation();

        assignActionListeners();
    }

    private void assignActionListeners(){
        profileButton.addActionListener(e -> onProfile());
        logoutButton.addActionListener(e -> onLogout());

        createNewGameButton.addActionListener(e -> onCreateGame());
        joinSelectedGameButton.addActionListener(e -> onJoinGame());
        replaySelectedGameButton.addActionListener(e -> onReplayGame());

        filterComboBox.addActionListener(e -> onFilterChange());
        searchTextField.addActionListener(e -> onSearchTextChange());
        onlyAvailableCheckBox.addActionListener(e -> onAvailableOnly());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void generateUserInformation(){
        ImageIcon icon = new ImageIcon(user.getImg().getScaledInstance(100, 100, 0));
        label_cash.setText(String.valueOf(user.getBalance()));
        label_name.setText(user.getUsername());
        label_picture.setText("");
        label_picture.setIcon(icon);
    }

    private void onProfile(){
        if(profile == null)
            profile = new Profile(this);
        profile.init();
    }

    private void onCreateGame(){
        //if(createGame == null)
        CreateGame createGame = new CreateGame(this);
        createGame.pack();
        createGame.setVisible(true);
    }

    private void onJoinGame(){
        JoinGame joinGame = new JoinGame(this);
        joinGame.pack();
        joinGame.setVisible(true);
    }

    private void onReplayGame(){

    }

    private void onLogout() {
        Welcome welcome = new Welcome();
        welcome.pack();
        welcome.setVisible(true);
    }

    private void onFilterChange(){

    }

    private void onSearchTextChange(){

    }

    private void onAvailableOnly(){

    }

    private void init(){
        setContentPane(contentPane);
        revalidate();
    }

    private void onExit(){
        dispose();
    }
}
