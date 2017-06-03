package Client.view.game;

import Client.domain.SessionManager;
import MutualJsonObjects.ClientPlayer;

import javax.swing.*;
import java.util.List;

/**
 * Created by User on 03/06/2017.
 */
public class Player {
    private JLabel nameLabel;
    private JLabel chipAmountLabel;
    private JLabel lastBetLabel;
    private JLabel cardsLabel;

    private JPanel container;

    public Player(ClientPlayer player){
        nameLabel = new JLabel(player.getPlayerName());
        chipAmountLabel = new JLabel(String.valueOf(player.getChipAmount()));
        lastBetLabel = new JLabel();

        container = new JPanel();
        container.add(nameLabel);
        container.add(chipAmountLabel);
        container.add(lastBetLabel);

        if(SessionManager.getInstance().user().getUsername().equals(player.getPlayerName())) {
            cardsLabel = new JLabel(player.getPlayerCards().toString());
            container.add(cardsLabel);
        }
    }

    public JPanel getContainer(){
        return container;
    }
}
