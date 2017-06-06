package TexasHoldem.domain.game.participants;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by RonenB on 4/11/2017.
 */
@Entity
@Table(name="spectator")
@PrimaryKeyJoinColumn(name="id")
public class Spectator extends Participant {

    public Spectator(User user){
        super(user);
    }

    public Spectator(){}

    public void removeFromGame(Game g){
        g.removeParticipant(this);
    }
}
