package Client.View.ClientMenu;

import TexasHoldem.domain.game.GamePolicy;

import javax.swing.*;

/**
 * Created by User on 13/05/2017.
 */
public class CreateGame {
    public MainMenu ancestor;

    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField nameTextField;
    private JComboBox<GamePolicy> policyComboBox;
    private JCheckBox tournamentCheckBox;
    private JSpinner minSpinner;
    private JSpinner maxSpinner;
    private JSpinner minBetSpinner;
    private JCheckBox allowSpectatingCheckBox;
    private JLabel buyInPolicyLabel;
    private JLabel chipPolicyLabel;
    private JLabel raiseLimitLabel;
    private JSpinner raiseLimitSpinner;
    private JSpinner buyInPolicySpinner;
    private JSpinner chipPolicySpinner;


    /*String creatorUsername, String gameName, GamePolicy policy, int limit, int minBet, int buyInPolicy, int chipPolicy,
    int minPlayerAmount, int maxPlayerAmount, boolean specAccept*/

    public CreateGame(MainMenu ancestor) {
        this.ancestor = ancestor;

        for(GamePolicy policy : GamePolicy.values()){
            policyComboBox.addItem(policy);
        }

        policyComboBox.addActionListener(e -> onPolicyChange());
        tournamentCheckBox.addActionListener(e -> onTournament());

        // 0 < even number < chip policy (if tournament)
        minBetSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        // raiseLimit > min bet
        raiseLimitSpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

        buyInPolicySpinner.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        chipPolicySpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    }

    public void init(){
        ancestor.setContentPane(contentPane);
        ancestor.getRootPane().setDefaultButton(okButton);
        ancestor.revalidate();
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
