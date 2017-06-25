package Client;

import MutualJsonObjects.ClientUserProfile;
import com.google.common.io.Files;
import com.sun.org.apache.xerces.internal.impl.dv.util.*;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Created by User on 13/05/2017.
 */
public class ClientUtils {

    public static void browseFile(JTextField textField){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, GIF, PNG, BMP", "jpg", "gif", "png", "bmp");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            textField.setText(fileChooser.getSelectedFile().getPath());
        }
    }

    public static void frameInit(JFrame frame, Container contentPane){
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
        frame.revalidate();
    }

    public static void clearTextFields(JTextField ... fields){
        for(JTextField field : fields){
            field.setText("");
        }
    }

    public static <T> String prettyList(List<T> list){
        StringBuilder sb = new StringBuilder();
        for(T item : list){
            sb.append(item.toString());
            if(list.lastIndexOf(item) != list.size()-1){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static ImageIcon getProfileImage(String path, int width, int height){
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width,height,0));
    }
}
