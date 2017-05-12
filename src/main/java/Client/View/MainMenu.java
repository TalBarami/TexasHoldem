package Client.View;

import TexasHoldem.domain.user.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by User on 12/05/2017.
 */
public class MainMenu extends JFrame {
    private User user;
    private JLabel label_name;
    private JLabel label_cash;
    private JLabel label_picture;
    private JPanel contentPane;

    public MainMenu() {
        try {
            setContentPane(contentPane);
            BufferedImage i = ImageIO.read(new File("C:\\Users\\User\\Desktop\\1.jpg"));
            user = new User("Tal", "1234", "talbaramii@gmail.com", LocalDate.now(), i);

            label_cash.setText(String.valueOf(user.getBalance()));
            label_name.setText(user.getUsername());
            label_picture.setText("");
            ImageIcon im = new ImageIcon(i.getScaledInstance(200, 200, 0));
            label_picture.setIcon(im);
            label_picture.setSize(200, 200);
        } catch(Exception ignore){

        }
    }
}
