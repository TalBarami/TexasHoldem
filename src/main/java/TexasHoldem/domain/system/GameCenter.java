package TexasHoldem.domain.system;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.common.Exceptions.LoginException;
import TexasHoldem.data.games.Games;
import TexasHoldem.data.games.IGames;
import TexasHoldem.data.users.IUsers;
import TexasHoldem.data.users.IUsersForDistributionAlgorithm;
import TexasHoldem.data.users.Users;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameActions;
import TexasHoldem.domain.game.GamePolicy;
import TexasHoldem.domain.game.GameSettings;
import TexasHoldem.domain.game.participants.Participant;
import TexasHoldem.domain.game.participants.Player;
import TexasHoldem.domain.user.User;
import TexasHoldem.domain.user.LeagueManager;
import TexasHoldem.domain.user.usersDistributions.DistributionAlgorithm;
import TexasHoldem.domain.user.usersDistributions.Min2InLeagueSameAmount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        User toDelete=getSpecificUserIfExist(username);
        leagueManager.updateMaxLeagueUserDeleted(toDelete, usersDb.getAllUsersInList());
        usersDb.deleteUser(toDelete);

        logger.info("'{}' deleted from the system.",username);
    }

    public User getUser(String username) throws EntityDoesNotExistsException {
        return getSpecificUserIfExist(username);
    }

    public void login(String userName,String pass) throws LoginException, EntityDoesNotExistsException {
        User user=usersDb.verifyCredentials(userName,pass);
        if(loggedInUsers.contains(user)){
            throw new  LoginException(String.format("'%s' is already logged in to the game.",userName));
        }
        loggedInUsers.add(user);
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

    public void depositMoney(String userName,int amount) throws ArgumentNotInBoundsException, EntityDoesNotExistsException {
        usersDb.getUserByUserName(userName).deposit(amount,true);
        logger.info("{} successfully deposited {} to it's wallet.",userName,amount);
    }

    public void startGame(String userName,String gameName) throws GameException {
        User user=getSpecificUserIfExist(userName);
        Game game= getSpecificGameIfExist(gameName);
        Player playerInGame=(Player)user.getGamePlayerMappings().get(game);
        if(playerInGame==null)
            throw new GameException(String.format("User '%s' is not currently playing in game '%s",userName,gameName));

        game.startGame(playerInGame);
    }

    //todo : service layer will catch exception if  game room already chosen or balance below buy in.
    public void createGame(String creatorUserName,GameSettings settings) throws NoBalanceForBuyInException, ArgumentNotInBoundsException, EntityDoesNotExistsException, InvalidArgumentException {
        User creator=getSpecificUserIfExist(creatorUserName);
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
    }

    public void joinGame(String userName, String gameName, boolean asSpectator) throws GameException {
        Game toJoin = getSpecificGameIfExist(gameName);
        User user = getSpecificUserIfExist(userName);

        if(user.getGamePlayerMappings().containsKey(toJoin))
            throw new GameException(String.format("User '%s' is already in game '%s'.", userName, gameName));

        if (asSpectator){
            if(!toJoin.canBeSpectated())
                throw new CantSpeactateThisRoomException("Selected game can't be spectated due to it's settings.");
            toJoin.joinGameAsSpectator(user);
        }
        else
            handleJoinGameAsPlayer(toJoin,user);
    }

    public void leaveGame(String userName,String gameName) throws GameException {
        User user=getSpecificUserIfExist(userName);
        Game game= getSpecificGameIfExist(gameName);

        if(!user.getGamePlayerMappings().containsKey(game))
            throw new GameException(String.format("User '%s' can't leave game '%s', since he is not playing inside.",userName,gameName));

        Participant participant=user.getGamePlayerMappings().get(game);
        participant.removeFromGame(game);
        user.getGamePlayerMappings().remove(game);

        if(game.canBeArchived()){
            gamesDb.archiveGame(game); // todo : notify someway to spectators of the room that room is closed?
            logger.info("Game '{}' is archived, since all players left.",gameName);
            game.addGameEvent(participant, GameActions.CLOSED);
        }
    }

    public List<Game> findAvailableGames(String userName) throws  EntityDoesNotExistsException {
        User user = getSpecificUserIfExist(userName);
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

    public Game findGameByName(String gameName) throws EntityDoesNotExistsException {
       return getSpecificGameIfExist(gameName);
    }

    private void handleJoinGameAsPlayer(Game game,User user) throws GameException {
        int gameLeague=game.getLeague();
        int usersLeague=user.getCurrLeague();
        int userBalance=user.getBalance();
        int buyInPolicy=game.getBuyInPolicy();

        if((usersLeague != LeagueManager.defaultLeagueForNewUsers) && (usersLeague != gameLeague))
            throw new LeaguesDontMatchException(String.format("Can't join game, user's league is %d ,while game's league is %d.",usersLeague,gameLeague));
        else if (game.isFull())
            throw new GameIsFullException("Can't join game as player because it's full.");
        else if(!game.realMoneyGame() && (userBalance < buyInPolicy))
            throw new NoBalanceForBuyInException(String.format("Buy in is %d, but user's balance is %d;",buyInPolicy,userBalance));
        else if(!game.isActive()){
            throw new GameException("Can't join game as player because tournament game is in progress");
        }

        game.joinGameAsPlayer(user);
    }

    public LeagueManager getLeagueManager(){
        return leagueManager;
    }

    public List<User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public Game getGameByName(String gameName) throws EntityDoesNotExistsException {
        return getSpecificGameIfExist(gameName);
    }

    public boolean isArchived(Game g){
        return gamesDb.isArchived(g);
    }

    private Game getSpecificGameIfExist(String gameName) throws EntityDoesNotExistsException {
        List<Game> games = gamesDb.getActiveGamesByName(gameName);
        if (games.isEmpty())
            throw new EntityDoesNotExistsException(String.format("Game '%s' doesn't exist in the system.",gameName));
        return games.get(0);
    }

    private User getSpecificUserIfExist(String userName) throws EntityDoesNotExistsException {
        User user = usersDb.getUserByUserName(userName);
        if (user == null)
            throw new EntityDoesNotExistsException(String.format("User '%s' doesn't exist in the system.", userName));
        return user;
    }


    public List<Game> getArchivedGames(){
        return gamesDb.getArchivedGames();
    }

    public void redistributeUsersInLeagues(DistributionAlgorithm da) {
        leagueManager.redistributeUsersInLeagues(da);
    }
  
    public void redistributeUsersInLeagues() {
        DistributionAlgorithm distributionAlgorithm = new Min2InLeagueSameAmount((IUsersForDistributionAlgorithm)usersDb);
        leagueManager.redistributeUsersInLeagues(distributionAlgorithm);
    }

    public IUsersForDistributionAlgorithm getUserDbWindowForDistributionAlgorithm() {
        return (IUsersForDistributionAlgorithm)usersDb;
    }

    public List<User> getTop20UsersByGrossProfit(){
        List<User> users = usersDb.getAllUsersInList();
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User firstUser, User secondUser) {
                return firstUser.getTotalGrossProfit() - secondUser.getTotalGrossProfit();
            }
        });
        if(users.size() > 20)
            return users.subList(0,20);
        return users;
    }

    public List<User> getTop20UsersByHighestCashGain(){
        List<User> users = usersDb.getAllUsersInList();
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User firstUser, User secondUser) {
                return firstUser.getHighestCashGain() - secondUser.getHighestCashGain();
            }
        });
        if(users.size() > 20)
            return users.subList(0,20);
        return users;
    }

    public List<User> getTop20UsersByNumOfGamesPlayed(){
        List<User> users = usersDb.getAllUsersInList();
        users.sort(Comparator.comparingInt(User::getNumOfGamesPlayed));
        if(users.size() > 20)
            return users.subList(0,20);
        return users;
    }
/*
    public void setDefaultLeague(String admin, int league) throws NoPermissionException {
        if(!usersDb.getHighestBalance().getUsername().equalsIgnoreCase(admin))
            throw new NoPermissionException("User must have the highest balance.");
        leagueManager.setDefaultLeagueForNewUsers(league);
    }
    public void setUserLeague(String admin, String username, int league) throws NoPermissionException {
        User ad = usersDb.getHighestBalance();
        if(!usersDb.getHighestBalance().getUsername().equalsIgnoreCase(admin))
            throw new NoPermissionException("User must have the highest balance.");
        User user = getUser(username);
        leagueManager.moveUserToLeague(user, league);
    }

    public void setLeagueCriteria(String admin, int criteria) throws NoPermissionException {
        if(!usersDb.getHighestBalance().getUsername().equalsIgnoreCase(admin))
            throw new NoPermissionException("User must have the highest balance.");
        leagueManager.setCriteriaToMovingLeague(criteria);
    }
*/
}
