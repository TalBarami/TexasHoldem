package Client.view.game;

import MutualJsonObjects.CardsImages;
import MutualJsonObjects.ClientCard;
import MutualJsonObjects.ClientPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(10, 10, 10, 10));
        container.setBackground(Color.lightGray);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.X_AXIS));
        labelsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        labelsPanel.setBackground(Color.lightGray);

        labelsPanel.add(nameLabel);
        labelsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        labelsPanel.add(chipAmountLabel);
        labelsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        labelsPanel.add(lastBetLabel);

        container.add(labelsPanel);
    }

    public void mark(){
        Font f = nameLabel.getFont();
        nameLabel.setFont(f.deriveFont(Font.BOLD, f.getSize() + 2));
    }

    public void showCards(){
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.X_AXIS));
        cardsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cardsPanel.setBackground(Color.lightGray);

        List<ClientCard> cards = new ArrayList<>(player.getPlayerCards());
        for(ClientCard card : cards){
            JLabel cardLabel = new JLabel(CardsImages.getImage(card));
            cardsPanel.add(cardLabel);
        }
        container.add(cardsPanel);
    }

    public void self(){
        nameLabel.setText(nameLabel.getText() + " (You)");
    }

    public JPanel getContainer(){
        return container;
    }
}
