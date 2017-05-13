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
    private User user;

    private JLabel label_name;
    private JLabel label_cash;
    private JLabel label_picture;
    private JPanel contentPane;
    private JButton button_profile;
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


        button_profile.addActionListener(e -> {
            Profile p = new Profile();
            setContentPane(p.contentPane);
        });
    }

    public void generateUserInformation(){
        ImageIcon im = new ImageIcon(user.getImg().getScaledInstance(100, 100, 0));
        label_cash.setText(String.valueOf(user.getBalance()));
        label_name.setText(user.getUsername());
        label_picture.setText("");
        label_picture.setIcon(im);
    }

    public void onProfile(){

    }

    public void onCreateGame(){

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
