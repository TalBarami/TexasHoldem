package Client.domain.callbacks;

import Enumerations.Move;

import java.util.List;

/**
 * Created by User on 03/06/2017.
 */
public interface MoveUpdateCallback {
    void execute(List<Move> possibleMoves);
}
