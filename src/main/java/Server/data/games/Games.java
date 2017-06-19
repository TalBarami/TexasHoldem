package Server.data.games;

import Exceptions.InvalidArgumentException;
import Server.data.Hybernate.HibernateUtil;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.game.Game;
import Enumerations.GamePolicy;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by RotemWald on 05/04/2017.
 */
public class Games implements IGames {
    private HashMap<Integer, Game> activeGames;
    // private List<Game> archivedGames;
    int newGameId;

    public Games() {
        activeGames = new HashMap<Integer, Game>();
        //archivedGames=new ArrayList<>();
        newGameId = 0;
    }

    public boolean addGame(Game game) throws InvalidArgumentException {
        if(getAllGameNames().contains(game.getName()))
            throw new InvalidArgumentException("Game name already exists, please choose another one.");

        int gameId = getNewGameId();

        game.setGameId(gameId);
        activeGames.put(gameId, game);

        return true;
    }

//    public boolean addGame(Game game) throws InvalidArgumentException {
//        if(getAllGameNames().contains(game.getName()))
//            throw new InvalidArgumentException("Game name already exists, please choose another one.");
//     //   int gameId = getNewGameId(game);
//     //   game.setGameId(gameId);
//     //   activeGames.put(gameId, game);
//        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
//        try{
//            session.beginTransaction();
//            session.save(game);
//            session.getTransaction().commit();
//        }catch (HibernateException e) {
//            if (session.getTransaction()!=null) session.getTransaction().rollback();
//            throw new InvalidArgumentException("Invalid Arguments");
//        }finally {
//            session.close();
//        }
//
//        return true;
//    }

//    public void archiveGame(Game game){
//        archivedGames.add(game);
//       activeGames.remove(game.getId());
//    }

    public void archiveGame(Game game){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            game.setGame_over(true);
            session.save(game);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }

        activeGames.remove(game.getId());
    }

    public LinkedList<Game> getActiveGamesByPlayerName(String playerName) {
        return searchActiveGame(g -> g.getPlayers().stream().anyMatch(p -> p.getUser().getUsername().equals(playerName)));
    }

    //   public List<String> getAllGameNames(){
    //     return activeGames.values().stream().map(Game::getName).collect(Collectors.toList());
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

        gameNames.addAll(activeGames.values().stream().map(Game::getName).collect(Collectors.toList()));
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

    private LinkedList<Game> searchActiveGame(Predicate<Game> p) {
        LinkedList<Game> result = new LinkedList<Game>();

        for (Game g : activeGames.values()) {
            if (/*g.isActive() &&*/ p.test(g)) {
                result.add(g);
            }

        }
        return result;
    }

//    private LinkedList<Game> searchActiveGame(Predicate<Game> p)
//    {
//        LinkedList<Game> result = new LinkedList<Game>();
//
//        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
//        List<Game> games = null;
//        try{
//            session.beginTransaction();
//            String sql = "FROM game";
//            games = session.createCriteria(Game.class).list();;
//            session.getTransaction().commit();
//        }catch (HibernateException e) {
//            if (session.getTransaction()!=null) session.getTransaction().rollback();
//        }finally {
//            session.close();
//        }
//
//        for (Game g : games) {
//            if (/*g.isActive() &&*/ p.test(g)) {
//                result.add(g);
//            }
//        }
//        return result;
//    }

    private int getNewGameId() {
        return newGameId++;
    }


    public List<Game> getArchivedGames(){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<Game> archivedGames = null;
        try{
            session.beginTransaction();
            archivedGames = session.createCriteria(Game.class).list();;
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return archivedGames;
    }

//    public boolean isArchived(Game g){
//        return archivedGames.contains(g);
//    }

    public boolean isArchived(Game game){
        return !activeGames.containsKey(game.getId());
    }
}