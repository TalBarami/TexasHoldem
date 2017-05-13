package TexasHoldem.domain.game.participants;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.chat.Message;
import TexasHoldem.domain.user.User;

/**
 * Created by RonenB on 4/11/2017.
 */
public abstract class Participant {
    protected User user;

    Participant(User user){
        this.user=user;
    }

    Participant(){

    }

    public abstract void removeFromGame(Game g);

    public User getUser(){
        return user;
    }

}
