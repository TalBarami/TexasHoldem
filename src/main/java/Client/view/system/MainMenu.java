package Client.view.system;

import Client.common.exceptions.EntityDoesNotExistsException;
import Client.common.exceptions.InvalidArgumentException;
import Client.communication.entities.ClientGameDetails;
import Client.communication.entities.ClientUserProfile;
import Client.domain.SearchManager;
import Client.domain.SessionManager;
import Client.view.ClientUtils;
import Client.view.access.Welcome;
import Client.view.game.Game;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static Client.domain.SearchManager.*;

/**
 * Created by User on 12/05/2017.
 */
public class MainMenu extends JFrame {
    private List<Game> games;

    private Profile profile;

    private List<String> textFields = Arrays.asList(GAME_NAME, USERNAME);
    private List<String> numericFields = Arrays.asList(POT_SIZE, MAX_BUYIN, CHIP_POLICY, MIN_BET, MIN_PLAYERS, MAX_PLAYERS);
    private List<String> selectionFields = Collections.singletonList(GAME_POLICY);
    private List<String> noFields = Arrays.asList(AVAILABLE, REPLAYABLE, SPECTATEABLE);

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
    private JComboBox<String> searchComboBox;

    public MainMenu() {
        init();

        assignActionListeners();
        initSearchProperties();
        initTable();
    }

    private void assignActionListeners(){
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
        Set<String> searchPolicies = SearchManager.getInstance().getPolicies();

        for(String type : searchPolicies){
            searchTypeComboBox.addItem(type);
        }

        List<String> gamePolicies = Arrays.asList("Limit", "No limit", "Pot limit");
        for(String policy : gamePolicies){
            searchComboBox.addItem(policy);
        }

        searchSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JFormattedTextField txt = ((JSpinner.NumberEditor) searchSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }


    private void generateUserInformation(){
        ClientUserProfile user = SessionManager.getInstance().user();
        //ImageIcon icon = new ImageIcon(user.getImg().getScaledInstance(100, 100, 0));
        label_name.setText(user.getUsername());
        label_cash.setText(String.valueOf(user.getBalance()));
        label_league.setText(String.valueOf(user.getLeague()));
        label_picture.setText("");
        //label_picture.setIcon(icon);
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
        try {
            SessionManager.getInstance().logout(SessionManager.getInstance().user().getUsername());
            Welcome welcome = new Welcome();
            welcome.pack();
            welcome.setLocationRelativeTo(null);
            welcome.setVisible(true);
            dispose();
        } catch (InvalidArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCreateGame(){
        CreateGame createGame = new CreateGame(this);
        createGame.pack();
        createGame.setLocationRelativeTo(null);
        createGame.setVisible(true);
    }

    private void onJoinGame(){

    }

    private void onSpectateGame(){

    }

    private void onReplayGame(){

    }

    private void onFind(){
        gamesTable.removeAll();
        String searchType = String.valueOf(searchTypeComboBox.getSelectedItem());
        String searchValue = searchTextField.isVisible() ? searchTextField.getText()
                : searchSpinner.isVisible() ? String.valueOf(searchSpinner.getValue())
                : searchComboBox.isVisible() ? String.valueOf(searchComboBox.getSelectedItem())
                : "";
        try {
            SearchManager searchManager = SearchManager.getInstance();
            List<ClientGameDetails> found = searchManager.getPolicy(searchType).find(searchValue);
            refreshTable(found);
        } catch (EntityDoesNotExistsException | InvalidArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tableSelectionChange(){
        joinSelectedGameButton.setEnabled(!gamesTable.getSelectionModel().isSelectionEmpty());
        spectateSelectedGameButton.setEnabled(String.valueOf(searchTypeComboBox.getSelectedItem()).equals(SPECTATEABLE) && !gamesTable.getSelectionModel().isSelectionEmpty());
        replaySelectedGameButton.setEnabled(String.valueOf(searchTypeComboBox.getSelectedItem()).equals(REPLAYABLE) && !gamesTable.getSelectionModel().isSelectionEmpty());

        handleButtonsAvailability();
    }

    private void onSearchTypeChange(){
        String selectedItem = String.valueOf(searchTypeComboBox.getSelectedItem());

        searchTextField.setVisible(textFields.contains(selectedItem));
        searchSpinner.setVisible(numericFields.contains(selectedItem));
        searchComboBox.setVisible(selectionFields.contains(selectedItem));

        handleButtonsAvailability();
    }

    private void handleButtonsAvailability(){
        joinSelectedGameButton.setEnabled(!gamesTable.getSelectionModel().isSelectionEmpty());
        spectateSelectedGameButton.setEnabled(String.valueOf(searchTypeComboBox.getSelectedItem()).equals(SPECTATEABLE) && !gamesTable.getSelectionModel().isSelectionEmpty());
        replaySelectedGameButton.setEnabled(String.valueOf(searchTypeComboBox.getSelectedItem()).equals(REPLAYABLE) && !gamesTable.getSelectionModel().isSelectionEmpty());
    }

    private void initTable(){
        List<String> tableHeader = Arrays.asList("Name", "Policy", "Policy limit", "Buy in", "Chip policy", "Minimum players", "Maximum players", "Allows spectate");
        DefaultTableModel model = (DefaultTableModel) gamesTable.getModel();
        for(String header : tableHeader){
            model.addColumn(header);
        }
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
        generateUserInformation();
        ClientUtils.clearTextFields(searchTextField);
    }

    private void onExit(){
        dispose();
    }
}
