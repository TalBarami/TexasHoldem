package Server.domain.game;

import Enumerations.GamePolicy;
import Server.data.Hybernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by אחיעד on 07/06/2017.
 */
public class GameDbTest {

    @Test
    public void testNewGame() throws Exception {
        GameSettings gamesettings = new GameSettings("achiad-poker-game", GamePolicy.NOLIMIT,0,10,100,10000,2,9,true);
        GameSettings gameSettingsFromDb = null;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(gamesettings);

            gameSettingsFromDb = (GameSettings) session.get(GameSettings.class, gamesettings.getName());

            gamesettings.setGameType(GamePolicy.POTLIMIT);
            gamesettings.setGameTypeLimit(10);
            gamesettings.setMinBet(20);
            gamesettings.setBuyInPolicy(0);
            gamesettings.setChipPolicy(1000);
            gamesettings.setMinimalPlayers(3);
            gamesettings.setMaximalPlayers(8);
            gamesettings.setAcceptSpectating(false);

            session.delete(gamesettings);
            session.getTransaction().commit();

        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }

        assertEquals(gamesettings.getName(), "achiad-poker-game");
        assertEquals(gamesettings.getGameType(), gameSettingsFromDb.getGameType());
        assertEquals(gamesettings.getGameTypeLimit(), gameSettingsFromDb.getGameTypeLimit());
        assertEquals(gameSettingsFromDb.getMinBet(), 20);
        assertEquals(gameSettingsFromDb.getBuyInPolicy(),0);
        assertEquals(gameSettingsFromDb.getChipPolicy(),1000);
        assertEquals(gameSettingsFromDb.getMinAmountPlayers(),3);
        assertEquals(gameSettingsFromDb.getMaxAmountPlayers(),8);
    }
}
