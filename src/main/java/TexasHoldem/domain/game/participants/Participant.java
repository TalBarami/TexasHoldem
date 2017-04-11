package TexasHoldem.domain.game.participants;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.users.User;

/**
 * Created by RonenB on 4/11/2017.
 */
public abstract class Participant {
    protected User user;

    public Participant(User user){
        this.user=user;
    }

    public abstract void removeFromGame(Game g);

}
