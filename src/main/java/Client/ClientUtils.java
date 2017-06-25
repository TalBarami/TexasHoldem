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

    public static Image decodeImage(String base64String,String userName){
        Image decoded = null;
        BufferedImage image;
        byte[] imageByte;
        File outputfile;

        BASE64Decoder decoder = new BASE64Decoder();
        try{
            imageByte = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();

            // write the image to a file
            outputfile = new File(userName+".png");
            ImageIO.write(image, "png", outputfile);

            decoded = ImageIO.read(outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return decoded;
    }

    public static String encodeImage(String path) {
        //Image scaled = resize(path);

        String encodedImage = null;
        BufferedImage img=resizeImage(path);

        try {
            File outputfile = new File(LocalDate.now().toString()+".png");
            ImageIO.write(img, "png", outputfile);

            //File imageFile =  new File(path);
            FileInputStream fileInputStreamReader = new FileInputStream(outputfile);
            byte[] bytes = new byte[(int)outputfile.length()];
            fileInputStreamReader.read(bytes);
            encodedImage = new String(Base64.encode(bytes).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodedImage;
    }

    private static BufferedImage resizeImage(String imagePath){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {}
        BufferedImage resizedImage = new BufferedImage(200, 200, TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(img, 0, 0, 200, 200, null);
        g.dispose();

        return resizedImage;
    }

    public static ImageIcon getProfileImage(String encodedImage,String userName, int width, int height){
        Image decoded = decodeImage(encodedImage,userName);
        return new ImageIcon(decoded.getScaledInstance(width,height,0));
    }
}
