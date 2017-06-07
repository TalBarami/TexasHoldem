package Server.domain.game.participants;

import Server.domain.game.Game;
import Server.domain.user.User;

import javax.persistence.*;

/**
 * Created by RonenB on 4/11/2017.
 */
@Entity
@Table(name="spectator")
@PrimaryKeyJoinColumn(name="participant_id")
public class Spectator extends Participant {

    public Spectator(User user){
        super(user);
    }

    public Spectator(){}

    public void removeFromGame(Game g){
        g.removeParticipant(this);
    }
}
