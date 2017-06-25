package Client.view.system;

import Client.view.game.Replay;
import MutualJsonObjects.ClientGameDetails;
import MutualJsonObjects.ClientGameEvent;
import MutualJsonObjects.ClientUserProfile;
import Enumerations.GamePolicy;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.GameException;
import Exceptions.InvalidArgumentException;

import Client.domain.MenuHandler;
import Client.domain.SearchHandler;
import Client.domain.SessionHandler;
import Client.ClientUtils;
import Client.view.access.Welcome;
import Client.view.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static Client.domain.SearchHandler.*;

/**
 * Created by User on 12/05/2017.
 */
public class MainMenu extends JFrame {
    private static Logger logger = LoggerFactory.getLogger(MainMenu.class);

    private Profile profile;

    private List<String> textFields = Arrays.asList(GAME_NAME, USERNAME);
    private List<String> numericFields = Arrays.asList(POT_SIZE, MAX_BUYIN, CHIP_POLICY, MIN_BET, MIN_PLAYERS, MAX_PLAYERS);
    private List<String> selectionFields = Collections.singletonList(GAME_POLICY);
    private List<String> noFields = Arrays.asList(AVAILABLE, REPLAYABLE, SPECTATEABLE);

    private List<Game> activeGames;

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
    private JComboBox<String> searchTypeComboBox;
    private JButton logoutButton;
    private JButton replaySelectedGameButton;
    private JButton spectateSelectedGameButton;
    private JButton depositButton;
    private JButton findButton;
    private JSpinner searchSpinner;
    private JComboBox<GamePolicy> searchComboBox;
    private JLabel searchValueLabel;

    public MainMenu() {
        init();
        setLocationRelativeTo(null);

        activeGames = new ArrayList<>();

        generateUserInformation(SessionHandler.getInstance().user());
        assignActionListeners();
        initSearchProperties();
        initTable();
    }

    private void assignActionListeners(){
        SessionHandler.getInstance().addUpdateCallback(this::generateUserInformation);

        profileButton.addActionListener(e -> onProfile());
        logoutButton.addActionListener(e -> onLogout());
        depositButton.addActionListener(e -> onDeposit());

        createNewGameButton.addActionListener(e -> onCreateGame());
        joinSelectedGameButton.addActionListener(e -> onJoinGame());
        spectateSelectedGameButton.addActionListener(e -> onSpectateGame());
        replaySelectedGameButton.addActionListener(e -> onReplayGame());
        findButton.addActionListener(e -> onFind());
        searchTypeComboBox.addActionListener(e -> onSearchTypeChange());

        gamesTable.getSelectionModel().addListSelectionListener(e -> tableSelectionChange());

        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
    }


    private void initSearchProperties(){
        Set<String> searchPolicies = SearchHandler.getInstance().getPoliciesNames();

        for(String type : searchPolicies){
            searchTypeComboBox.addItem(type);
        }

        for(GamePolicy policy : GamePolicy.values()){
            searchComboBox.addItem(policy);
        }

        searchSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) searchSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }


    private void generateUserInformation(ClientUserProfile profile){
        ClientUserProfile user = SessionHandler.getInstance().user();
        label_name.setText("Name: " + user.getUsername());
        label_cash.setText("Cash: " + String.valueOf(user.getBalance()));
        label_league.setText("League: " + String.valueOf(user.getCurrLeague()));
        label_picture.setText("");
        label_picture.setIcon(ClientUtils.getProfileImage(profile.getImage(), 100, 100));
    }

    private void onDeposit(){
        Deposit deposit = new Deposit(this);
        deposit.pack();
        deposit.setLocationRelativeTo(null);
        deposit.setVisible(true);
    }

    private void onProfile(){
        if(profile == null)
            profile = new Profile(this);
        profile.init();
    }

    private void onLogout() {
        Welcome welcome = new Welcome();
        welcome.pack();
        welcome.setLocationRelativeTo(null);
        welcome.setVisible(true);
        onExit();
    }

    private void onCreateGame(){
        CreateGame createGame = new CreateGame(this);
        createGame.pack();
        createGame.setLocationRelativeTo(null);
        createGame.setVisible(true);
    }

    private void onJoinGame(){
        String gameName = String.valueOf(gamesTable.getValueAt(gamesTable.getSelectedRow(), 0));
        String username = SessionHandler.getInstance().user().getUsername();
        try {
            MenuHandler.getInstance().joinGame(username, gameName);
            Game game = new Game(this, gameName);
            addGame(game);
            game.init();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSpectateGame(){
        String gameName = String.valueOf(gamesTable.getValueAt(gamesTable.getSelectedRow(), 0));
        String username = SessionHandler.getInstance().user().getUsername();
        try {
            MenuHandler.getInstance().spectateGame(username, gameName);
            Game game = new Game(this, gameName);
            addGame(game);
            game.init();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onReplayGame(){
        String gameName = String.valueOf(gamesTable.getValueAt(gamesTable.getSelectedRow(), 0));
        try {
            List<ClientGameEvent> events = MenuHandler.getInstance().replayGame(gameName);
            Replay replay = new Replay(this, gameName, events);
            replay.init();
        } catch (GameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onFind(){
        DefaultTableModel dtm = (DefaultTableModel) gamesTable.getModel();
        dtm.setRowCount(0);

        String searchType = String.valueOf(searchTypeComboBox.getSelectedItem());
        String searchValue = searchTextField.isVisible() ? searchTextField.getText()
                : searchSpinner.isVisible() ? String.valueOf(searchSpinner.getValue())
                : searchComboBox.isVisible() ? String.valueOf(searchComboBox.getSelectedItem())
                : "";
        try {
            SearchHandler searchHandler = SearchHandler.getInstance();
            List<ClientGameDetails> found = searchHandler.getPolicy(searchType).find(searchValue);
            refreshTable(found);
        } catch (EntityDoesNotExistsException | InvalidArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tableSelectionChange(){
        if(gamesTable.getSelectionModel().isSelectionEmpty()){
            for(JButton button : Arrays.asList(joinSelectedGameButton, spectateSelectedGameButton, replaySelectedGameButton)){
                button.setEnabled(false);
            }
        } else{
            List<ClientGameDetails> games = SearchHandler.getInstance().getLastSearchedGames();
            logger.info("Games in table: {}", games.toString());
            int selectedIndex = gamesTable.getSelectedRow();
            logger.info("Selected row: {}, Game: {}", selectedIndex, games.get(selectedIndex));
            joinSelectedGameButton.setEnabled(!games.get(selectedIndex).isArchived());
            spectateSelectedGameButton.setEnabled(games.get(selectedIndex).isSpectateValid());
            replaySelectedGameButton.setEnabled(games.get(selectedIndex).isArchived());
        }
    }

    private void onSearchTypeChange(){
        String selectedItem = String.valueOf(searchTypeComboBox.getSelectedItem());

        searchTextField.setVisible(textFields.contains(selectedItem));
        searchSpinner.setVisible(numericFields.contains(selectedItem));
        searchComboBox.setVisible(selectionFields.contains(selectedItem));
        searchValueLabel.setVisible(!noFields.contains(selectedItem));
        revalidate();
    }

    private void initTable(){
        List<String> tableHeader = Arrays.asList("Name", "Policy", "Policy limit", "Buy in", "Chip policy", "Minimum players", "Maximum players", "Allows spectate");
        DefaultTableModel model = (DefaultTableModel) gamesTable.getModel();
        for(String header : tableHeader){
            model.addColumn(header);
        }
        gamesTable.setRowSelectionAllowed(true);
        gamesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void refreshTable(List<ClientGameDetails> games){
        DefaultTableModel model = (DefaultTableModel) gamesTable.getModel();
        for(ClientGameDetails game : games){
            model.addRow(new Object[]{
                    game.getName(), game.getPolicyType(), game.getPolicyLimitAmount(),
                    game.getBuyInAmount(), game.getChipPolicyAmount(), game.getMinimumPlayersAmount(),
                    game.getMaximumPlayersAmount(), game.isSpectateValid()});
        }
    }

    public void init(){
        ClientUtils.frameInit(this, contentPane);
        setTitle("Main menu");
        ClientUtils.clearTextFields(searchTextField);
    }

    private void onExit(){
        try {
            for(int i=activeGames.size()-1; i>=0; i--){
                activeGames.get(i).onExit();
            }
            SessionHandler.getInstance().logout(SessionHandler.getInstance().user().getUsername());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        dispose();
    }

    public void addGame(Game game){
        activeGames.add(game);
    }

    public void removeGame(Game game){
        activeGames.remove(game);
    }
}
