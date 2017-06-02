package Server.domain.game.participants;

import Server.domain.game.Game;
import Server.domain.user.User;

/**
 * Created by RonenB on 4/11/2017.
 */
public class Spectator extends Participant {
    public Spectator(User user){
        super(user);
    }

    public void removeFromGame(Game g){
        g.removeParticipant(this);
    }
}
