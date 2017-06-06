package TexasHoldem.domain.game.participants;

import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.user.User;

import javax.persistence.*;

/**
 * Created by RonenB on 4/11/2017.
 */

@Entity
@Table(name = "participant")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "participant_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userName")
    protected User user;

    public Participant(User user){
        this.user=user;
    }

    public Participant(){

    }

    public abstract void removeFromGame(Game g);

    public User getUser(){
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
