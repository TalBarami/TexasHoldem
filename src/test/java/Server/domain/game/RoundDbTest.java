package Server.domain.game;

import Enumerations.GamePolicy;
import Server.data.Hybernate.HibernateUtil;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.game.card.Card;
import Server.domain.game.card.Rank;
import Server.domain.game.card.Suit;
import Server.domain.game.participants.Player;
import Server.domain.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by hod on 07/06/2017.
 */
public class RoundDbTest {

    @Test
    public void BasicRoundDbTest() throws Exception {
        GameSettings gameSettings = new GameSettings("achiad-poker-game", GamePolicy.NOLIMIT,0,10,100,10000,2,9,true);
        GameSettings gameSettingsFromDb = null;
        User user = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991,4,20),null);
        Player p = new Player(user, 1000, 0);
        LinkedList<Player> playersList = new LinkedList<>();
        playersList.add(p);
        Round roundFromDb = null;
        Round round = new Round(playersList, gameSettings, 0);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.save(p);
            session.save(gameSettings);
            session.save(round);
            roundFromDb = (Round) session.get(Round.class, round.getId());
            gameSettingsFromDb = roundFromDb.getGameSettings();

            round.setRoundActive(false);
            round.setChipsToCall(100);
            round.setPotAmount(150);

            gameSettings.setMinBet(20);
            gameSettings.setBuyInPolicy(0);
            gameSettings.setChipPolicy(1000);
            gameSettings.setMinimalPlayers(3);
            gameSettings.setMaximalPlayers(8);
            gameSettings.setAcceptSpectating(false);

            session.delete(round);
            session.delete(gameSettings);
            session.delete(p);
            session.delete(user);
            session.getTransaction().commit();

        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            assertEquals(0,1);
        }finally {
            session.close();
        }

        assertEquals(roundFromDb.getChipsToCall(), 100);
        assertEquals(roundFromDb.isRoundActive(), false);
        assertEquals(roundFromDb.getPotAmount(), 150);
        assertEquals(gameSettingsFromDb.getMinBet(), 20);
        assertEquals(gameSettingsFromDb.getBuyInPolicy(),0);
        assertEquals(gameSettingsFromDb.getChipPolicy(),1000);
        assertEquals(gameSettingsFromDb.getMinAmountPlayers(),3);
        assertEquals(gameSettingsFromDb.getMaxAmountPlayers(),8);
    }

    @Test
    public void AdvanceRoundDbTest() throws Exception {
        GameSettings gameSettings = new GameSettings("achiad-poker-game", GamePolicy.NOLIMIT,0,10,100,10000,2,9,true);
        GameSettings gameSettingsFromDb = null;

        User user1 = new User("achiad", "achiad", "achiad@gmail.com", LocalDate.of(1991,4,20),null);
        User user2 = new User("hod", "hod", "hod@gmail.com", LocalDate.of(1991,4,20),null);

        Player p1 = new Player(user1, 250, 5);
        Player p2 = new Player(user2, 500, 5);

        LinkedList<Player> playersList = new LinkedList<>();
        playersList.add(p1);
        playersList.add(p2);

        Round roundFromDb = null;
        Round round = new Round(playersList, gameSettings, 1);

        Card card = new Card(Rank.FIVE, Suit.CLUB);
        LinkedList<Card> cardList = new LinkedList<>();
        cardList.add(card);

        GameEvent gameEvent = new GameEvent(p1, GameActions.CALL, "hod");
        LinkedList<GameEvent> gameEvents = new LinkedList<>();
        gameEvents.add(gameEvent);

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(card);
            session.save(user1);
            session.save(user2);
            session.save(p1);
            session.save(p2);
            session.save(gameSettings);
            session.save(round);
            roundFromDb = (Round) session.get(Round.class, round.getId());

            round.setCurrentDealerPlayer(playersList.get(0));
            round.setRoundActive(false);
            round.setChipsToCall(100);
            round.setPotAmount(150);
            round.setOpenedCards(cardList);
            round.setEventList(gameEvents);
            p1.setChipsAmount(1000);
            card.setRank(Rank.KING);

            session.delete(round);
            session.delete(gameSettings);
            session.delete(p1);
            session.delete(p2);
            session.delete(user1);
            session.delete(user2);
            session.delete(card);
            session.getTransaction().commit();

        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            assertEquals(0,1);
        }finally {
            session.close();
        }

        assertEquals(roundFromDb.getChipsToCall(), 100);
        assertEquals(roundFromDb.getPotAmount(), 150);
        assertEquals(roundFromDb.isRoundActive(), false);
        assertEquals(roundFromDb.getCurrentDealerPlayer().getChipsAmount(), 1000);
        assertEquals(roundFromDb.getOpenedCards().get(0).getRank(), Rank.KING);
        assertEquals(roundFromDb.getEventList().get(0).getEventAction(), GameActions.CALL);
    }
}
