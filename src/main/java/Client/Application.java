package Client;

import Client.View.ClientMenu.MainMenu;
import TexasHoldem.domain.user.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by User on 12/05/2017.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        BufferedImage i = ImageIO.read(new File("C:\\Users\\User\\Desktop\\1.jpg"));
        User user = new User("Tal", "1234", "talbaramii@gmail.com", LocalDate.now(), i);
        MainMenu mainMenu = new MainMenu(user);
        mainMenu.pack();
        mainMenu.setSize(1000, 1000);
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setVisible(true);

        /*Welcome w = new Welcome();
        w.pack();
        w.setSize(400, 400);
        w.setLocationRelativeTo(null);
        w.setVisible(true);*/
    }
}
