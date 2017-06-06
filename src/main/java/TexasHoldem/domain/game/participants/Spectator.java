package TexasHoldem.domain.game.participants;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.user.User;

import javax.persistence.*;

/**
 * Created by RonenB on 4/11/2017.
 */
@Entity
@Table(name="spectator")
@PrimaryKeyJoinColumn(name="participant_id")
//@AttributeOverrides({
//        @AttributeOverride(name="participant_id", column=@Column(name="participant_id")),
//        @AttributeOverride(name="userName", column=@Column(name="userName"))
//})
public class Spectator extends Participant {

    public Spectator(User user){
        super(user);
    }

    public Spectator(){}

    public void removeFromGame(Game g){
        g.removeParticipant(this);
    }
}
