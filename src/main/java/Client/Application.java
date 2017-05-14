package Client;

import Client.View.AccessView.Welcome;
import Client.View.SystemView.MainMenu;
import TexasHoldem.common.JsonSerializer;
import TexasHoldem.communication.entities.ClientUserDetails;
import TexasHoldem.communication.entities.ClientUserProfile;
import TexasHoldem.communication.entities.ResponseMessage;
import TexasHoldem.domain.user.User;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.xml.ws.Response;
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
        User user = new User("Tal", "1234", "talbaramii@gmail.com", LocalDate.now(), null);

        MainMenu mainMenu = new MainMenu(user);

        //Welcome w = new Welcome();

    }
}
