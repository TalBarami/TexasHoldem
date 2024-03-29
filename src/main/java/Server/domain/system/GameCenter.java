package Server.domain.system;

import Exceptions.*;
import Server.data.games.Games;
import Server.data.games.IGames;
import Server.data.users.IUsers;
import Server.data.users.IUsersForDistributionAlgorithm;
import Server.data.users.Users;
import Server.domain.events.gameFlowEvents.GameEvent;
import Server.domain.events.gameFlowEvents.MoveEvent;
import Server.domain.game.Game;
import Server.domain.game.GameActions;
import Enumerations.GamePolicy;
import Server.domain.game.GameSettings;
import Server.domain.game.participants.Participant;
import Server.domain.game.participants.Player;
import Server.domain.user.User;
import Server.domain.user.LeagueManager;
import Server.domain.user.usersDistributions.DistributionAlgorithm;
import Server.domain.user.usersDistributions.Min2InLeagueSameAmount;
import javafx.util.Pair;
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

    public void registerUser(String userName, String pass, String email, LocalDate date, String img) throws InvalidArgumentException {
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

    public List<String> getAllUserNames(){
        return usersDb.getAllUserNames();
    }

    public void login(String userName,String pass) throws LoginException, EntityDoesNotExistsException {
        User user=usersDb.verifyCredentials(userName,pass);
        for (User u : loggedInUsers)
            if (u.getUsername().equals(userName))
                throw new LoginException(String.format("'%s' is already logged in to the game.", userName));
        loggedInUsers.add(user);
        logger.info("{} has logged in to the system.",userName);
    }

    public void logout(String userName) throws InvalidArgumentException {
        if(loggedInUsers.stream().filter(user -> user.getUsername().equals(userName)).collect(Collectors.toList()).isEmpty())
            throw new InvalidArgumentException(String.format("'%s' isn't logged in, so can't log out.",userName));

        //remove from all playing rooms
        for(User user: loggedInUsers){
            if(user.getUsername().equals(userName)) {
                Map<String, Participant> mappings = user.getGameMapping();
                Iterator<Map.Entry<String, Participant>> it = mappings.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Participant> keyValue = it.next();
                    String gameName = keyValue.getKey();
                    Participant p = mappings.get(gameName);
                    p.removeFromGame(gamesDb.getActiveGamesByName(gameName).get(0));
                    it.remove();
                }
            }
        }
        //remove from logged in user
        loggedInUsers=loggedInUsers.stream().filter(user -> !user.getUsername().equals(userName)).collect(Collectors.toList());
        logger.info("{} has logged out from the system, attempting to notify played games if needed.",userName);
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date, String image) throws InvalidArgumentException, EntityDoesNotExistsException {
        usersDb.editUser(originalUserName,newUserName,pass,email,date,image);
        logger.info("'{}' user edit his profile [u: {}, p: {}, e: {}, d: {}] .",originalUserName,newUserName,pass,email,date);
    }

    public void depositMoney(String userName,int amount) throws ArgumentNotInBoundsException, EntityDoesNotExistsException {
        usersDb.getUserByUserName(userName).deposit(amount,true);
        logger.info("{} successfully deposited {} to it's wallet.",userName,amount);
    }

    public void startGame(String userName,String gameName) throws GameException {
        User user=getSpecificUserIfExist(userName);
        Game game= getSpecificGameIfExist(gameName);
        Player playerInGame=(Player)user.getGameMapping().get(game.getSettings().getName());
        if(playerInGame==null)
            throw new GameException(String.format("User '%s' is not currently playing in game '%s",userName,gameName));
        if(game.isActive()){
            throw new GameException("Can't start a game, because it's already started.");
        }

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

        if(user.getGameMapping().containsKey(toJoin.getSettings().getName()))
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

        if(!user.getGameMapping().containsKey(gameName))
            throw new GameException(String.format("User '%s' can't leave game '%s', since he is not playing inside.",userName,gameName));

        Participant participant=user.getGameMapping().get(gameName);
        participant.removeFromGame(game);
        user.getGameMapping().remove(gameName);

        if(game.canBeArchived()){
            game.addGameEvent(participant, GameActions.CLOSED);
            gamesDb.archiveGame(game); // todo : notify someway to spectators of the room that room is closed?
            logger.info("Game '{}' is archived, since all players left.",gameName);
        }
    }

    public List<Game> findAvailableGames(String userName) throws  EntityDoesNotExistsException {
        User user = getSpecificUserIfExist(userName);
        List<Game> activeGames = gamesDb.getActiveGames();

        return activeGames.stream()
                .filter(game -> game.getLeague() == user.getCurrLeague() &&
                        (game.realMoneyGame() || (!game.realMoneyGame() && game.canBeJoined() && (game.getBuyInPolicy() <= user.getBalance()))) &&
                        game.getPlayers().size() < game.getMaximalAmountOfPlayers() &&
                        !user.getGameMapping().containsKey(game.getSettings().getName()))
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
        else if(!game.canBeJoined()){
            throw new GameException("Can't join game as player because there is a round in progress, please try again in several minutes.");
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


    public List<String> getArchivedGames() throws EntityDoesNotExistsException {
        List<String> gameNames = gamesDb.getArchivedGames();

        if (gameNames.isEmpty())
            throw new EntityDoesNotExistsException("No archived games available");

        return gameNames;
    }

    public List<GameEvent> getAllGameEvents(String gameName) {
        return gamesDb.getAllGameEvents(gameName);
    }

//    public List<MoveEvent> getAllMoveEvents(String gameName) {
//        return gamesDb.getAllMoveEvents(gameName);
//    }

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

    public List<Pair<String, Integer>> getTop20UsersByGrossProfit(){
        List<User> users = usersDb.getAllUsersInList();
        users.sort((firstUser, secondUser) -> -1 * (firstUser.getTotalGrossProfit() - secondUser.getTotalGrossProfit()));
        if(users.size() > 20)
            return convertUserListToPair(users.subList(0,20), "grossProfit");
        return convertUserListToPair(users, "grossProfit");
    }

    public List<Pair<String, Integer>> getTop20UsersByHighestCashGain(){
        List<User> users = usersDb.getAllUsersInList();
        users.sort((firstUser, secondUser) -> -1 * (firstUser.getHighestCashGain() - secondUser.getHighestCashGain()));
        if(users.size() > 20)
            return convertUserListToPair(users.subList(0,20), "highestCashGain");
        return convertUserListToPair(users, "highestCashGain");
    }

    public List<Pair<String, Integer>> getTop20UsersByNumOfGamesPlayed(){
        List<User> users = usersDb.getAllUsersInList();
        users.sort((firstUser, secondUser) -> -1 * (firstUser.getNumOfGamesPlayed() - secondUser.getNumOfGamesPlayed()));
        if(users.size() > 20)
            return convertUserListToPair(users.subList(0,20), "numOfGamesPlayed");
        return convertUserListToPair(users, "numOfGamesPlayed");
    }


    public List<Pair<String, Integer>> convertUserListToPair(List<User> users, String byWhat){
        List<Pair<String, Integer>> userStats = new ArrayList<>(users.size());
        for(User user: users){
            switch (byWhat) {
                case "numOfGamesPlayed":
                    userStats.add(new Pair<>(user.getUsername(), user.getNumOfGamesPlayed()));
                    break;
                case "highestCashGain":
                    userStats.add(new Pair<>(user.getUsername(), user.getHighestCashGain()));
                    break;
                case "grossProfit":
                    userStats.add(new Pair<>(user.getUsername(), user.getTotalGrossProfit()));
                    break;
            }
        }
        return userStats;
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
