package TexasHoldem.domain.game.participants;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.users.User;

/**
 * Created by RonenB on 4/11/2017.
 */
public class Spectator extends Participant {
    public Spectator(User user){
        super(user);
    }

    public void removeFromGame(Game g){
        g.removePlayer(this);
    }
}
