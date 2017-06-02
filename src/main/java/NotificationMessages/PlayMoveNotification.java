package NotificationMessages;

import Enumerations.Move;

import java.util.List;

/**
 * Created by rotemwald on 02/06/17.
 */
public class PlayMoveNotification extends Notification {
    private List<Move> moveList;

    public PlayMoveNotification(String recipientUserName, List<Move> moveList) {
        super(recipientUserName);
        this.moveList = moveList;
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moveList) {
        this.moveList = moveList;
    }
}
