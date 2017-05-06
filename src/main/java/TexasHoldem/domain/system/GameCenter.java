package TexasHoldem.domain.system;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.data.games.Games;
import TexasHoldem.data.games.IGames;
import TexasHoldem.data.users.IUsers;
import TexasHoldem.data.users.Users;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GamePolicy;
import TexasHoldem.domain.game.GameSettings;
import TexasHoldem.domain.game.participants.Participant;
import TexasHoldem.domain.user.User;
import TexasHoldem.domain.user.LeagueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GameCenter {
    private static Logger logger = LoggerFactory.getLogger(GameCenter.class);

    private List<User> loggedInUsers;
    private IUsers usersDb;
    private IGames gamesDb;
    private LeagueManager leagueManager;

    public GameCenter() {
        loggedInUsers=new ArrayList<>();
        usersDb=new Users();
        gamesDb=new Games();
        leagueManager = new LeagueManager();
    }

    public void registerUser(String userName, String pass, String email, LocalDate date, BufferedImage img) throws InvalidArgumentException {
        User newUser = new User(userName,pass,email,date,img);
        usersDb.addUser(newUser);
        leagueManager.addNewUserToLeague(newUser);
        logger.info("{} registered to the system as a new user.",userName);
    }

    public void deleteUser(String username) throws EntityDoesNotExistsException {
        usersDb.deleteUser(username);
        logger.info("{} deleted from the system.",username);
    }

    public User getUser(String username){
        return usersDb.getUserByUserName(username);
    }

    public void login(String userName,String pass) throws LoginException, EntityDoesNotExistsException {
        User user=usersDb.verifyCredentials(userName,pass);
        loggedInUsers.add(user);//todo: or maybe change status in Db that he logged in?
        logger.info("{} has logged in to the system.",userName);
    }

    public void logout(String userName) throws InvalidArgumentException {
        if(loggedInUsers.stream().filter(user -> user.getUsername().equals(userName)).collect(Collectors.toList()).isEmpty())
            throw new InvalidArgumentException(String.format("'%s' isn't logged in, so can't log out.",userName));

        //remove from all playing rooms
        loggedInUsers.forEach(user -> {
            if(user.getUsername().equals(userName)) {
                Map<Game, Participant> mappings = user.getGamePlayerMappings();
                Iterator<Map.Entry<Game, Participant>> it = mappings.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Game, Participant> keyValue = it.next();
                    Game g = keyValue.getKey();
                    Participant p = mappings.get(g);
                    p.removeFromGame(g);
                    it.remove();
                }
            }
        });
        //remove from logged in user
        loggedInUsers=loggedInUsers.stream().filter(user -> !user.getUsername().equals(userName)).collect(Collectors.toList());
        logger.info("{} has logged out from the system, attempting to notify played games if needed.",userName);
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidArgumentException, EntityDoesNotExistsException {
        usersDb.editUser(originalUserName,newUserName,pass,email,date);
        logger.info("'{}' user edit his profile [u: {}, p: {}, e: {}, d: {}] .",originalUserName,newUserName,pass,email,date);
    }

    public void depositMoney(String userName,int amount) throws ArgumentNotInBoundsException {
        usersDb.getUserByUserName(userName).deposit(amount,true);
        logger.info("{} successfully deposited {} to it's wallet.",userName,amount);
    }

    //todo : service layer will catch exception if  game room already chosen or balance below buy in.
    public void createGame(String creatorUserName,GameSettings settings) throws InvalidArgumentException, NoBalanceForBuyInException, ArgumentNotInBoundsException {
        User creator=usersDb.getUserByUserName(creatorUserName);
        int minPlayers=settings.getPlayerRange().getLeft();
        int maxPlayers=settings.getPlayerRange().getRight();

        if(maxPlayers<minPlayers)
            throw new InvalidArgumentException("Maximal amount of players is greater than minimal.");
        else if(minPlayers<2 || maxPlayers>9)
            throw new ArgumentNotInBoundsException(String.format("Players amount should be between 2 and 9, but actually they are between %d and %d",minPlayers,maxPlayers));

        if(settings.tournamentMode() && creator.getBalance()<settings.getBuyInPolicy())
            throw new NoBalanceForBuyInException("User's balance below the selected game buy in.");

        settings.setLeagueCriteria(creator.getCurrLeague());
        Game game=new Game(settings,creator,leagueManager);
        gamesDb.addGame(game);
        //game.startGame(); todo : causing loop for now, waiting for threads.
        //gamesDb.archiveGame(game); todo: archive game (returned when the gamec an be archived)
    }

    public void joinGame(String userName, String gameName, boolean asSpectator) throws InvalidArgumentException, CantSpeactateThisRoomException,
            NoBalanceForBuyInException, GameIsFullException, LeaguesDontMatchException {
        List<Game> games = gamesDb.getActiveGamesByName(gameName);
        if (games.isEmpty())
            throw new InvalidArgumentException("There is no game in the system with selected name.");
        Game toJoin = games.get(0);
        User user = usersDb.getUserByUserName(userName);
        if (asSpectator){
            if(!toJoin.canBeSpectated())
                throw new CantSpeactateThisRoomException("Selected game can't be spectated due to it's settings.");
            toJoin.joinGameAsSpectator(user);
        }
        else
            handleJoinGameAsPlayer(toJoin,user);
    }

    public void leaveGame(String userName,String gameName) throws GameException {
        User user=usersDb.getUserByUserName(userName);
        if(user==null)
            throw new InvalidArgumentException(String.format("User '%s' doesn't exist in the system.",userName));

        if(gamesDb.getActiveGamesByName(gameName).isEmpty())
            throw new InvalidArgumentException(String.format("Game '%s' doesn't exist in the system.",gameName));

        Game game=gamesDb.getActiveGamesByName(gameName).get(0);
        if(!user.getGamePlayerMappings().containsKey(game))
            throw new GameException(String.format("User '%s' can't leave game '%s', since he is not playing inside.",userName,gameName));


        user.getGamePlayerMappings().get(game).removeFromGame(game);
        user.getGamePlayerMappings().remove(game);
        if(game.canBeArchived()){
            gamesDb.archiveGame(game); // todo : notify someway to spectators of the room that room is closed?
            logger.info("Game '{}' is archived, since all players left.",gameName);
        }

    }

    public List<Game> findAvailableGames(String username){
        User user = usersDb.getUserByUserName(username);
        List<Game> activeGames = gamesDb.getActiveGames();
        return activeGames.stream()
                .filter(game -> game.getLeague() == user.getCurrLeague() &&
                        (game.realMoneyGame() || (!game.realMoneyGame() && game.isActive() && (game.getBuyInPolicy() <= user.getBalance()))) &&
                        game.getPlayers().size() < game.getMaximalAmountOfPlayers() &&
                        !user.getGamePlayerMappings().containsKey(game))
                .collect(Collectors.toList());
    }

    public List<Game> findSpectateableGames(){
        return gamesDb.getActiveGamesBySpectationAllowed(true);
    }

    public List<Game> findGamesByUsername(String username) {
        return gamesDb.getActiveGamesByPlayerName(username);
    }

    public List<Game> findGamesByPotSize(int potSize) {
        return gamesDb.getActiveGamesByPotSize(potSize);
    }

    public List<Game> findGamesByGamePolicy(GamePolicy policy) {
        return gamesDb.getActiveGamesByGamePolicy(policy);
    }

    public List<Game> findGamesByMaximumBuyIn(int maximumBuyIn) {
        return gamesDb.getActiveGamesByMaximumBuyInAmount(maximumBuyIn);
    }

    public List<Game> findGamesByChipPolicy(int chipPolicy) {
        return gamesDb.getActiveGamesByChipPolicyAmount(chipPolicy);
    }

    public List<Game> findGamesByMinimumBet(int minimumBet) {
        return gamesDb.getActiveGamesByMinimumBetAmount(minimumBet);
    }

    public List<Game> findGamesByMinimumPlayers(int minimumPlayers) {
        return gamesDb.getActiveGamesByMinimumPlayersAmount(minimumPlayers);
    }

    public List<Game> findGamesByMaximumPlayers(int maximumPlayers) {
        return gamesDb.getActiveGamesByMaximumPlayersAmount(maximumPlayers);
    }

    private void handleJoinGameAsPlayer(Game game,User user) throws LeaguesDontMatchException, GameIsFullException, NoBalanceForBuyInException {
        int gameLeague=game.getLeague();
        int usersLeague=user.getCurrLeague();
        int userBalance=user.getBalance();
        int buyInPolicy=game.getBuyInPolicy();

        if(gameLeague != usersLeague)
            throw new LeaguesDontMatchException(String.format("Can't join game, user's league is %d ,while game's league is %d.",usersLeague,gameLeague));
        else if (game.isFull())
            throw new GameIsFullException("Can't join game as player because it's full.");
        else if(!game.realMoneyGame() && (userBalance < buyInPolicy))
            throw new NoBalanceForBuyInException(String.format("Buy in is %d, but user's balance is %d;",buyInPolicy,userBalance));

        game.joinGameAsPlayer(user);
    }

    public LeagueManager getLeagueManager(){
        return leagueManager;
    }

    public List<User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public Game getGameByName(String gameName){
        List<Game> games = gamesDb.getActiveGamesByName(gameName);
        return games.isEmpty() ? null : games.get(0);
    }

    public boolean isArchived(Game g){
        return gamesDb.isArchived(g);
    }

    public List<User> getAllUsersInList() {
        return usersDb.getAllUsersInList();
    }

    public List<User> getUsersByLeague(int leagueNum) {
        return  usersDb.getUsersByLeague(leagueNum);
    }

//    public void setDefaultLeague(String admin, int league) throws NoPermissionException {
//        if(!usersDb.getHighestBalance().getUsername().equalsIgnoreCase(admin))
//            throw new NoPermissionException("User must have the highest balance.");
//        leagueManager.setDefaultLeagueForNewUsers(league);
//    }

//    public void setUserLeague(String admin, String username, int league) throws NoPermissionException {
//        User ad = usersDb.getHighestBalance();
//        if(!usersDb.getHighestBalance().getUsername().equalsIgnoreCase(admin))
//            throw new NoPermissionException("User must have the highest balance.");
//        User user = getUser(username);
//        leagueManager.moveUserToLeague(user, league);
//    }
//
//    public void setLeagueCriteria(String admin, int criteria) throws NoPermissionException {
//        if(!usersDb.getHighestBalance().getUsername().equalsIgnoreCase(admin))
//            throw new NoPermissionException("User must have the highest balance.");
//        leagueManager.setCriteriaToMovingLeague(criteria);
//    }
}
