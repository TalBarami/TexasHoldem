package Client.view.game;

import Client.ClientUtils;
import Client.view.system.MainMenu;
import MutualJsonObjects.ClientGameEvent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 18/06/2017.
 */
public class Replay extends JFrame{
    private MainMenu ancestor;

    private JPanel contentPane;
    private JPanel replayPanel;
    private JButton closeButton;

    private String gameName;
    private List<ClientGameEvent> events;

    public Replay(MainMenu ancestor, String gameName, List<ClientGameEvent> events){
        this.ancestor = ancestor;

        this.gameName = gameName;
        this.events = events;

        assignActionListeners(gameName);
        addGameEvents(events);
    }

    public void init(){
        toFront();
        ClientUtils.frameInit(this, contentPane);
        setLocationRelativeTo(ancestor);
        setTitle("Replay: " + gameName);
        getRootPane().setDefaultButton(closeButton);
        pack();
    }

    private void assignActionListeners(String title){
        closeButton.addActionListener(e -> onClose());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void addGameEvents(List<ClientGameEvent> events){
        List<String> gameLog = events.stream().map(event -> {
            switch (event.getAction()){
                case CHECK:
                case CALL:
                case FOLD:
                case RAISE:
                    return String.format("%s played %s", event.getEventInitiatorUserName(), event.getAction().toString().toLowerCase());
                case ENTER:
                    return String.format("%s joined", event.getEventInitiatorUserName());
                case EXIT:
                    return String.format("%s left", event.getEventInitiatorUserName());
                case START:
                case NEWROUND:
                    return String.format("%s started the game", event.getEventInitiatorUserName());
                case CLOSED:
                    return "Game over.";

            }
            return "";
        }).collect(Collectors.toList());

        for(String s : gameLog){
            replayPanel.add(new JLabel(s));
        }
    }

    private void onClose(){
        dispose();
    }
}
