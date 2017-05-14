package Client.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * Created by User on 13/05/2017.
 */
public class ClientUtils {

    public static void browseFile(JTextField textField){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            textField.setText(fileChooser.getSelectedFile().getPath());
        }
    }

    public static void frameInit(JFrame frame, Container contentPane){
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
    }

    public static void clearTextFields(JTextField ... fields){
        for(JTextField field : fields){
            field.setText("");
        }
    }
}
