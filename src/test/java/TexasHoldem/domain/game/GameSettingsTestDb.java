package TexasHoldem.domain.game;

import TexasHoldem.data.Hybernate.HibernateUtil;
import TexasHoldem.domain.game.card.Card;
import TexasHoldem.domain.game.card.Rank;
import TexasHoldem.domain.game.card.Suit;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by אחיעד on 07/06/2017.
 */
public class GameSettingsTestDb {

//    @Test
//    public void testNewGameSettingsTest() throws Exception {
//        GameSettings gamesettings = new GameSettings("achiad-poker-game",GamePolicy.NOLIMIT,0,10,100,10000,2,9,true);
//        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
//        try{
//            session.beginTransaction();
//            session.save(gamesettings);
//            gamesettings.setName("hod-poker-game");
//            gamesettings.setGameType(GamePolicy.POTLIMIT);
//            gamesettings.setGameTypeLimit(10);
//            gamesettings.setMinBet(20);
//            gamesettings.setBuyInPolicy(0);
//            gamesettings.setChipPolicy(1000);
//            gamesettings.setMinimalPlayers(3);
//            gamesettings.setMaximalPlayers(8);
//            gamesettings.setAcceptSpectating(false);
//
//            GameSettings gameSettingsFromDb = (GameSettings) session.get(GameSettings.class, gamesettings.getName());
//
//            assertEquals(gamesettings.getName(), gameSettingsFromDb.getChipPolicy());
//            assertEquals(gamesettings.getGameType(), gameSettingsFromDb.getGameType());
//            assertEquals(gamesettings.getGameTypeLimit(), gameSettingsFromDb.getGameTypeLimit());
//            assertEquals(gamesettings.getMinBet(), gameSettingsFromDb.getMinBet());
//
//            session.delete(p);
//            session.delete(card);
//            User userFromDb = (User) session.get(User.class, user.getUsername());
//            assertEquals(userFromDb.getUsername(), user.getUsername());
//            session.delete(user);
//            session.getTransaction().commit();
//        }catch (HibernateException e) {
//            if (session.getTransaction()!=null) session.getTransaction().rollback();
//        }finally {
//            session.close();
//        }
//    }
}
