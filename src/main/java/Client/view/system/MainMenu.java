package Client.view.system;

import Client.communication.entities.ClientUserProfile;
import Client.domain.SearchPolicy;
import Client.domain.SessionManager;
import Client.view.ClientUtils;
import Client.view.access.Welcome;
import Client.view.game.Game;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by User on 12/05/2017.
 */
public class MainMenu extends JFrame {
    private List<Game> games;

    private Profile profile;
    private List<SearchPolicy> searchPolicies;

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
    private JComboBox<String> filterComboBox;
    private JCheckBox onlyAvailableCheckBox;
    private JButton logoutButton;
    private JButton replaySelectedGameButton;
    private JButton spectateSelectedGameButton;
    private JCheckBox inactiveGamesCheckBox;
    private JButton depositButton;
    private JButton findButton;

    public MainMenu() {
        init();

        assignActionListeners();
        initializeSearchFilters();
    }

    private void assignActionListeners(){
        profileButton.addActionListener(e -> onProfile());
        logoutButton.addActionListener(e -> onLogout());

        createNewGameButton.addActionListener(e -> onCreateGame());
        joinSelectedGameButton.addActionListener(e -> onJoinGame());
        spectateSelectedGameButton.addActionListener(e -> onSpectateGame());
        replaySelectedGameButton.addActionListener(e -> onReplayGame());
        findButton.addActionListener(e -> onFind());

        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
    }

    private void initializeSearchFilters(){
        filterComboBox.addItem("Game name");
        filterComboBox.addItem("User name");
        filterComboBox.addItem("Pot size");
        filterComboBox.addItem("Game policy");
        filterComboBox.addItem("Maximum buy-in");
        filterComboBox.addItem("Chip policy");
        filterComboBox.addItem("Minimum bet");
        filterComboBox.addItem("Minimum players");
        filterComboBox.addItem("Maximum players");
    }

    private void generateUserInformation(){
        ClientUserProfile user = SessionManager.getInstance().user();
        /*ImageIcon icon = new ImageIcon(user.getImg().getScaledInstance(100, 100, 0));
        label_cash.setText(String.valueOf(user.getBalance()));*/
        label_name.setText(user.getUsername());
        label_picture.setText("");
        /*label_picture.setIcon(icon);*/
    }

    private void onProfile(){
        if(profile == null)
            profile = new Profile(this);
        profile.init();
    }

    private void onCreateGame(){
        CreateGame createGame = new CreateGame(this);
        createGame.pack();
        createGame.setLocationRelativeTo(null);
        createGame.setVisible(true);
    }

    private void onJoinGame(){
        Game game = new Game();
        game.init();
    }

    private void onSpectateGame(){

    }

    private void onReplayGame(){

    }

    private void onFind(){

    }

    private void onLogout() {
        Welcome welcome = new Welcome();
        welcome.pack();
        welcome.setLocationRelativeTo(null);
        welcome.setVisible(true);
        dispose();
    }

    public void init(){
        ClientUtils.frameInit(this, contentPane);
        generateUserInformation();
        ClientUtils.clearTextFields(searchTextField);
    }

    private void onExit(){
        dispose();
    }

}
