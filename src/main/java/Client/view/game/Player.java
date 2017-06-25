package Client.view.game;

import Client.ClientUtils;
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
    private JLabel pictureLabel;
    private JLabel nameLabel;
    private JLabel chipAmountLabel;
    private JLabel lastBetLabel;

    public Player(ClientPlayer player){
        this.player = player;
        pictureLabel = new JLabel(ClientUtils.getProfileImage(player.getImage() ,player.getPlayerName(), 100, 100));
        nameLabel = new JLabel("Name: " + player.getPlayerName());
        chipAmountLabel = new JLabel("Chips: " + String.valueOf(player.getChipAmount()));
        lastBetLabel = new JLabel("Last Bet: " + String.valueOf(player.getLastBetSinceCardOpen()));

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        container.setBackground(Color.lightGray);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        labelsPanel.setBackground(Color.lightGray);

        JPanel identityPanel = new JPanel();
        identityPanel.setLayout(new BoxLayout(identityPanel, BoxLayout.X_AXIS));
        identityPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        identityPanel.setBackground(Color.lightGray);

        identityPanel.add(pictureLabel);
        identityPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        identityPanel.add(nameLabel);

        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.X_AXIS));
        informationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        informationPanel.setBackground(Color.lightGray);

        informationPanel.add(chipAmountLabel);
        informationPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        informationPanel.add(lastBetLabel);

        labelsPanel.add(identityPanel);
        labelsPanel.add(informationPanel);

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
