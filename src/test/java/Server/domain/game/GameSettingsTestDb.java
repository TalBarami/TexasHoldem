package Server.domain.game;

import Enumerations.GamePolicy;
import Server.data.Hybernate.HibernateUtil;
import Server.domain.game.card.Card;
import Server.domain.game.card.Rank;
import Server.domain.game.card.Suit;
import Server.domain.game.participants.Player;
import Server.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by אחיעד on 07/06/2017.
 */
public class GameSettingsTestDb {

    @Test
    public void testNewGameSettingsTest() throws Exception {
        GameSettings gamesettings = new GameSettings("achiad-poker-game", GamePolicy.NOLIMIT,0,10,100,10000,2,9,true);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
       try{
            session.beginTransaction();
            session.save(gamesettings);
            gamesettings.setName("hod-poker-game");
            gamesettings.setGameType(GamePolicy.POTLIMIT);
            gamesettings.setGameTypeLimit(10);
            gamesettings.setMinBet(20);
            gamesettings.setBuyInPolicy(0);
            gamesettings.setChipPolicy(1000);
            gamesettings.setMinimalPlayers(3);
            gamesettings.setMaximalPlayers(8);
            gamesettings.setAcceptSpectating(false);
            GameSettings gameSettingsFromDb = (GameSettings) session.get(GameSettings.class, gamesettings.getName());

            assertEquals(gamesettings.getName(), gameSettingsFromDb.getChipPolicy());
            assertEquals(gamesettings.getGameType(), gameSettingsFromDb.getGameType());
            assertEquals(gamesettings.getGameTypeLimit(), gameSettingsFromDb.getGameTypeLimit());
            assertEquals(gamesettings.getMinBet(), gameSettingsFromDb.getMinBet());
            assertEquals(gamesettings.getBuyInPolicy(),gameSettingsFromDb.getBuyInPolicy());
            assertEquals(gamesettings.getChipPolicy(),gameSettingsFromDb.getChipPolicy());
            assertEquals(gamesettings.getMinAmountPlayers(),gameSettingsFromDb.getMinAmountPlayers());
            assertEquals(gamesettings.getMaxAmountPlayers(),gameSettingsFromDb.getMaxAmountPlayers());

      //      session.delete(gamesettings);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
   }
}
