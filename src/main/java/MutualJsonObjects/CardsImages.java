package MutualJsonObjects;

import Server.domain.game.card.Rank;
import Server.domain.game.card.Suit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 17/06/2017.
 */
public class CardsImages {
    private static Logger logger = LoggerFactory.getLogger(ClientCard.class);
    private static Map<String, Image> images;

    static{
        images = new HashMap<>();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()){
                ClientCard card = new ClientCard(rank, suit);
                File imagePath = new File("src/main/java/Client/resources/" + card.getImageName());
                logger.info("Loading card: {} at path: {}", card.toString(), imagePath.getAbsolutePath());
                try {
                    Image image = ImageIO.read(imagePath);
                    images.put(card.toString(), image.getScaledInstance(50, 72, Image.SCALE_SMOOTH));
                } catch (IOException e) {
                    logger.error("Failed to load image for card: {} at path: {}", card.toString(), imagePath.getAbsolutePath());
                }
            }
        }
    }

    public static ImageIcon getImage(ClientCard card){
        return new ImageIcon(images.get(card.toString()));
    }
}
