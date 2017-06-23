package Server.domain.game.participants;

import Server.domain.game.Game;
import Server.domain.game.chat.Message;
import Server.domain.user.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

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

    public void setUser(User user) {
        this.user = user;
    }
}
