package Client.View.SystemView;

import TexasHoldem.domain.game.GamePolicy;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;

public class CreateGame extends JDialog {
    private MainMenu ancestor;

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

    public CreateGame(MainMenu ancestor) {
        this.ancestor = ancestor;

        setContentPane(contentPane);
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

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initComponents(){
        for(GamePolicy policy : GamePolicy.values()){
            policyComboBox.addItem(policy);
        }

        minBetSpinner.setModel(new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1));
        raiseLimitSpinner.setModel(new SpinnerNumberModel(20, 1, Integer.MAX_VALUE, 1));
        buyInPolicySpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        chipPolicySpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

        for(JSpinner spinner : new JSpinner[]{minBetSpinner, raiseLimitSpinner, buyInPolicySpinner, chipPolicySpinner}){
            JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
            ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        }

    }

    private void onOK() {
        // add your code here
        dispose();
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
        if(policyComboBox.getSelectedItem().equals(GamePolicy.LIMIT)){
            raiseLimitLabel.setVisible(true);
            raiseLimitSpinner.setVisible(true);
        } else{
            raiseLimitLabel.setVisible(false);
            raiseLimitSpinner.setVisible(false);
        }
    }
}
