package Server.domain.game;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum GameActions {
    CHECK(0), RAISE(1), CALL(2), FOLD(3), ENTER(4), START(5), EXIT(6), NEWROUND(7), CLOSED(8) , SEND(9);

    private int action;

    GameActions(int action)
    {
        this.action = action;
    }

    public int getAction()
    {
        return action;
    }
}
