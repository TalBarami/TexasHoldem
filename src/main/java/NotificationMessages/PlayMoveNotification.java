package NotificationMessages;

import Enumerations.Move;

import java.util.List;

/**
 * Created by rotemwald on 02/06/17.
 */
public class PlayMoveNotification extends GameNotification {
    private List<Move> moveList;

    public PlayMoveNotification(String gameName, String recipientUserName, List<Move> moveList) {
        super(recipientUserName, gameName);
        this.moveList = moveList;
    }

    public PlayMoveNotification() {
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moveList) {
        this.moveList = moveList;
    }

    @Override
    public String toString() {
        return "PlayMoveNotification{" +
                "moveList=" + moveList +
                '}';
    }
}
