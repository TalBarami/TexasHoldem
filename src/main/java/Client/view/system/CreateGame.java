package Client.view.system;

import Exceptions.ArgumentNotInBoundsException;
import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.NoBalanceForBuyInException;
import Enumerations.GamePolicy;
import Client.domain.MenuManager;
import Client.view.game.Game;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;

public class CreateGame extends JDialog {
    private MainMenu ancestor;
    private MainMenu.AddGameCallback addGameCallback;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JComboBox<GamePolicy> policyComboBox;
    private JSpinner minBetSpinner;
    private JLabel raiseLimitLabel;
    private JLabel buyInPolicyLabel;
    private JLabel chipPolicyLabel;
    private JSpinner raiseLimitSpinner;
    private JSpinner buyInPolicySpinner;
    private JSpinner chipPolicySpinner;
    private JSpinner minSpinner;
    private JSpinner maxSpinner;
    private JCheckBox tournamentCheckBox;
    private JCheckBox allowSpectatingCheckBox;

    public CreateGame(MainMenu ancestor, MainMenu.AddGameCallback addGameCallback) {
        this.ancestor = ancestor;
        this.addGameCallback = addGameCallback;

        setContentPane(contentPane);
        setTitle("Create game");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initComponents();

        assignActionListeners();
    }

    private void assignActionListeners(){
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        policyComboBox.addActionListener(e -> onPolicyChange());
        tournamentCheckBox.addActionListener(e -> onTournament());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void initComponents(){
        for(GamePolicy policy : GamePolicy.values()){
            policyComboBox.addItem(policy);
        }

        minBetSpinner.setModel(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
        raiseLimitSpinner.setModel(new SpinnerNumberModel(20, 1, Integer.MAX_VALUE, 1));
        buyInPolicySpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        chipPolicySpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        minSpinner.setModel(new SpinnerNumberModel(2, 2, 9, 1));
        maxSpinner.setModel(new SpinnerNumberModel(9, 2, 9, 1));

        for(JSpinner spinner : new JSpinner[]{minBetSpinner, raiseLimitSpinner, buyInPolicySpinner, chipPolicySpinner, minSpinner, maxSpinner}){
            JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
            ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        }

    }

    private void onOK() {
        // add your code here
        MenuManager menuManager = MenuManager.getInstance();
        try {
            menuManager.createGame(nameTextField.getText(), (GamePolicy)policyComboBox.getSelectedItem(), (int)raiseLimitSpinner.getValue(), (int)minBetSpinner.getValue(),
                    (int)buyInPolicySpinner.getValue(), (int)chipPolicySpinner.getValue(), (int)minSpinner.getValue(), (int)maxSpinner.getValue(), allowSpectatingCheckBox.isSelected());
            Game game = new Game(ancestor, nameTextField.getText());
            addGameCallback.add(game);
            dispose();
        } catch (InvalidArgumentException | EntityDoesNotExistsException | ArgumentNotInBoundsException | NoBalanceForBuyInException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onTournament(){
        buyInPolicyLabel.setVisible(!buyInPolicyLabel.isVisible());
        buyInPolicySpinner.setVisible(!buyInPolicySpinner.isVisible());
        chipPolicyLabel.setVisible(!chipPolicyLabel.isVisible());
        chipPolicySpinner.setVisible(!chipPolicySpinner.isVisible());
    }

    private void onPolicyChange(){
        if(policyComboBox.getSelectedItem().equals("Limit")){
            raiseLimitLabel.setVisible(true);
            raiseLimitSpinner.setVisible(true);
        } else{
            raiseLimitLabel.setVisible(false);
            raiseLimitSpinner.setVisible(false);
        }
    }
}
