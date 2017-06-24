package Client.view.game;

import MutualJsonObjects.CardsImages;
import MutualJsonObjects.ClientCard;
import MutualJsonObjects.ClientPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
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
    private JLabel chipAmountLabel;
    private JLabel lastBetLabel;

    public Player(ClientPlayer player){
        this.player = player;
        nameLabel = new JLabel("Name: " + player.getPlayerName());
        chipAmountLabel = new JLabel("Chips: " + String.valueOf(player.getChipAmount()));
        lastBetLabel = new JLabel("Last Bet: " + String.valueOf(player.getLastBetSinceCardOpen()));

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
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

    public void markAsCurrent(){
        mark();
        container.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.lightGray, Color.red));
    }
    public void markAsWinner(){
        mark();
        container.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.lightGray, Color.yellow));
    }

    public void mark(){
        Font f = nameLabel.getFont();
        nameLabel.setFont(f.deriveFont(Font.BOLD, f.getSize() + 2));
        chipAmountLabel.setFont(f.deriveFont(Font.BOLD, f.getSize() + 2));
        lastBetLabel.setFont(f.deriveFont(Font.BOLD, f.getSize() + 2));
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
        container.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.darkGray, Color.blue));
    }

    public JPanel getContainer(){
        return container;
    }
}
