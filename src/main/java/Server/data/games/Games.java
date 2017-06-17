package Server.data.games;

import Exceptions.InvalidArgumentException;
import Server.data.Hybernate.HibernateUtil;
import Server.domain.game.Game;
import Enumerations.GamePolicy;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by RotemWald on 05/04/2017.
 */
public class Games implements IGames {
    private HashMap<Integer, Game> _games;
    private List<Game> archivedGames;
    int _newGameId;

    public Games() {
        _games = new HashMap<Integer, Game>();
        archivedGames=new ArrayList<>();
        _newGameId = 0;
    }

    public boolean addGame(Game game) throws InvalidArgumentException {
        if(getAllGameNames().contains(game.getName()))
            throw new InvalidArgumentException("Game name already exists, please choose another one.");
     //   int gameId = getNewGameId(game);
     //   game.setGameId(gameId);
     //   _games.put(gameId, game);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(game);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            throw new InvalidArgumentException("Invalid Arguments");
        }finally {
            session.close();
        }

        return true;
    }

//    public void archiveGame(Game game){
//        archivedGames.add(game);
//       _games.remove(game.getId());
//    }

    public void archiveGame(Game game){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            game.setGame_over(true);
            session.update(game);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    public LinkedList<Game> getActiveGamesByPlayerName(String playerName) {
        return searchActiveGame(g -> g.getPlayers().stream().anyMatch(p -> p.getUser().getUsername().equals(playerName)));
    }

 //   public List<String> getAllGameNames(){
   //     return _games.values().stream().map(Game::getName).collect(Collectors.toList());
   // }

    public List<String> getAllGameNames(){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<String> gameNames = null;
        try{
            session.beginTransaction();
            String sql = "SELECT game_name FROM game";
            gameNames = session.createSQLQuery(sql).list();
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return gameNames;
    }

    public LinkedList<Game> getActiveGamesByName(String name){
        return searchActiveGame(g -> g.getName().equals(name));
    }

    public LinkedList<Game> getActiveGamesByPotSize(int potSize) {
        return searchActiveGame(g -> g.getLastRound().getPotAmount() >= potSize);
    }

    public LinkedList<Game> getActiveGamesByGamePolicy(GamePolicy policy) {
        return searchActiveGame(g -> g.getSettings().getGameType() == policy);
    }

    public LinkedList<Game> getActiveGamesByMinimumBetAmount(int minBetAmount) {
        return searchActiveGame(g -> g.getSettings().getMinBet() == minBetAmount);
    }

    public LinkedList<Game> getActiveGamesByMaximumBuyInAmount(int maxBuyInAmount) {
        return searchActiveGame(g -> g.getBuyInPolicy() <= maxBuyInAmount);
    }

    public LinkedList<Game> getActiveGamesByChipPolicyAmount(int chipPolicyAmount) {
        return searchActiveGame(g -> g.getSettings().getChipPolicy() <= chipPolicyAmount);
    }

    public LinkedList<Game> getActiveGamesByMinimumPlayersAmount(int minimumPlayersAmount) {
        return searchActiveGame(g -> g.getSettings().getPlayerRange().getLeft() == minimumPlayersAmount);
    }

    public LinkedList<Game> getActiveGamesByMaximumPlayersAmount(int maximumPlayersAmount) {
        return searchActiveGame(g -> g.getSettings().getPlayerRange().getRight() == maximumPlayersAmount);
    }

    public LinkedList<Game> getActiveGamesBySpectationAllowed(boolean spectationAllowed) {
        return searchActiveGame(g -> g.canBeSpectated() == spectationAllowed);
    }

    public List<Game> getActiveGames(){
        return searchActiveGame(g -> true);
    }

//    private LinkedList<Game> searchActiveGame(Predicate<Game> p) {
//        LinkedList<Game> result = new LinkedList<Game>();
//
//        for (Game g : _games.values()) {
//            if (/*g.isActive() &&*/ p.test(g)) {
//                result.add(g);
//            }
//
//        }
//        return result;
//    }

    private LinkedList<Game> searchActiveGame(Predicate<Game> p)
    {
        LinkedList<Game> result = new LinkedList<Game>();

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<Game> games = null;
        try{
            session.beginTransaction();
            String sql = "FROM game";
            games = session.createCriteria(Game.class).list();;
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }

        for (Game g : games) {
            if (/*g.isActive() &&*/ p.test(g)) {
                result.add(g);
            }
        }
        return result;
    }

  //  private int getNewGameId() {
    //    return _newGameId++;
   // }


    public List<Game> getArchivedGames(){ return archivedGames; }

//    public boolean isArchived(Game g){
//        return archivedGames.contains(g);
//    }

    public boolean isArchived(Game game){
        return searchActiveGame(g -> g.isGame_over() == true).contains(game);
    }
}