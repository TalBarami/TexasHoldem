package Client.View.ClientAccess;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Register {
    private Welcome ancestor;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField text_name;
    private JTextField text_email;
    private JLabel Label_username;
    private JLabel Label_password;
    private JLabel Label_email;
    private JPasswordField text_password;
    private JLabel Label_birthdate;
    private JLabel Label_picture;
    private JTextField text_picture;
    private JButton button_picture;
    private JDatePickerImpl datePicker_birthdate;

    public Register(Welcome ancestor) {
        this.ancestor = ancestor;

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        button_picture.addActionListener(e -> onBrowse());
    }

    public void init(){
        ancestor.setContentPane(contentPane);
        ancestor.getRootPane().setDefaultButton(buttonOK);
        ancestor.revalidate();
    }

    private void onOK() {
        // add your code here
    }

    private void onCancel() {
        ancestor.init();
    }

    private void onBrowse(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            text_picture.setText(fileChooser.getSelectedFile().getPath());
        }
    }

    private void createUIComponents() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker_birthdate = new JDatePickerImpl(datePanel);
    }
}
