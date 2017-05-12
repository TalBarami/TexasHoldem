package Client;

import Client.View.Welcome;

/**
 * Created by User on 12/05/2017.
 */
public class Application {
    public static void main(String[] args){
        Welcome w = new Welcome();
        w.pack();
        w.setSize(200, 200);
        w.setLocationRelativeTo(null);
        w.setVisible(true);
    }
}
