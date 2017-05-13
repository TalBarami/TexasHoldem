package Client.View.ClientMenu;

import TexasHoldem.domain.user.User;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private JTable table1;
    private JButton createNewGameButton;
    private JButton joinSelectedGameButton;
    private JTextField searchTextField;
    private JComboBox filterComboBox;
    private JCheckBox onlyAvailableCheckBox;

    public MainMenu(User user) {
        this.user = user;

        setContentPane(contentPane);

        generateUserInformation();

        // call onExit() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        // call onExit() on ESCAPE
        contentPane.registerKeyboardAction(e -> onExit(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        profileButton.addActionListener(e -> onProfile());
        createNewGameButton.addActionListener(e -> onCreateGame());
        joinSelectedGameButton.addActionListener(e -> onJoinGame());
    }

    public void generateUserInformation(){
        ImageIcon icon = new ImageIcon(user.getImg().getScaledInstance(100, 100, 0));
        label_cash.setText(String.valueOf(user.getBalance()));
        label_name.setText(user.getUsername());
        label_picture.setText("");
        label_picture.setIcon(icon);
    }

    public void onProfile(){
        if(profile == null)
            profile = new Profile(this);
        profile.init();
    }

    public void onCreateGame(){
        if(createGame == null)
            createGame = new CreateGame(this);
        createGame.init();
    }

    public void onJoinGame(){

    }

    public void onFilterChange(){

    }

    public void onSearchTestChange(){

    }

    public void onAvailableOnly(){

    }

    public void init(){
        setContentPane(contentPane);
        revalidate();
    }

    public void onExit(){
        dispose();
    }
}
