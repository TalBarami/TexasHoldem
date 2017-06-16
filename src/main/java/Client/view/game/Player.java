package Client.view.game;

import Client.ClientUtils;
import Client.domain.SessionManager;
import MutualJsonObjects.ClientPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03/06/2017.
 */
public class Player {
    private ClientPlayer player;

    private JPanel container;
    private JLabel nameLabel;

    public Player(ClientPlayer player){
        this.player = player;
        nameLabel = new JLabel("Name: " + player.getPlayerName());
        JLabel chipAmountLabel = new JLabel("Chips: " + String.valueOf(player.getChipAmount()));
        JLabel lastBetLabel = new JLabel("Last Bet: " + String.valueOf(player.getLastBetSinceCardOpen()));

        container = new JPanel();
        container.add(nameLabel);
        container.add(chipAmountLabel);
        container.add(lastBetLabel);
    }

    public void mark(){
        Font f = nameLabel.getFont();
        nameLabel.setFont(f.deriveFont(Font.BOLD, f.getSize() + 2));
    }

    public void showCards(){
        JLabel cardsLabel = new JLabel(ClientUtils.prettyList(new ArrayList<>(player.getPlayerCards())));
        container.add(cardsLabel);
    }

    public void self(){
        nameLabel.setText(nameLabel.getText() + " (You)");
    }

    public JPanel getContainer(){
        return container;
    }
}
