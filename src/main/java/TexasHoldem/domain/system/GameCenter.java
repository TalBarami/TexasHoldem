package TexasHoldem.domain.system;

import TexasHoldem.common.Exceptions.*;
import TexasHoldem.data.games.Games;
import TexasHoldem.data.games.IGames;
import TexasHoldem.data.users.IUsers;
import TexasHoldem.data.users.Users;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.GameSettings;
import TexasHoldem.domain.user.User;
import TexasHoldem.domain.user.LeagueManager;

import javax.security.auth.login.LoginException;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameCenter {
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
        leagueManager.addNewUserToLegue(newUser);
    }

    public void deleteUser(String username) throws EntityDoesNotExistsException {
        usersDb.deleteUser(username);
    }

    public User getUser(String username){
        return usersDb.getUserByUserName(username);
    }

    public void login(String userName,String pass) throws LoginException {
        User user=usersDb.verifyCredentials(userName,pass);
        loggedInUsers.add(user);//todo: or maybe change status in Db that he logged in?
    }

    public void logout(String userName){
        //remove from all playing rooms
        loggedInUsers.forEach(user -> {
            if(user.getUsername().equals(userName))
                user.getGamePlayerMappings().forEach((game,participant) -> {
                    participant.removeFromGame(game);
                    user.removeGameParticipant(game);
            });
        });

        //remove from logged in user
            loggedInUsers=loggedInUsers.stream().filter(user -> !user.getUsername().equals(userName)).collect(Collectors.toList());
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidArgumentException {
        usersDb.editUser(originalUserName,newUserName,pass,email,date);
    }

    public void depositMoney(String userName,int amount) throws ArgumentNotInBoundsException {
        usersDb.getUserByUserName(userName).deposit(amount,false);
    }


    //todo : service layer will catch exception if  game room already chosen or balance below buy in.
    public void createGame(String creatorUserName,GameSettings settings) throws InvalidArgumentException, NoBalanceForBuyInException {
        User creator=usersDb.getUserByUserName(creatorUserName);

        if(settings.tournamentMode() && creator.getBalance()<settings.getBuyInPolicy())
            throw new NoBalanceForBuyInException("User's balance below the selected game buy in.");

        settings.setLeagueCriteria(creator.getCurrLeague());
        Game game=new Game(settings,creator,leagueManager);
        gamesDb.addGame(game);
        //todo : what about the 'activeGames' field?  delete\leave ( and add the game to the list)?
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

    public List<Game> findAvailableGames(String username){
        User user = usersDb.getUserByUserName(username);
        List<Game> activeGames = gamesDb.getActiveGames();
        return activeGames.stream()
                .filter(game -> game.getLeague() == user.getCurrLeague() &&
                        game.getBuyInPolicy() <= user.getBalance() &&
                        (game.realMoneyGame() || (!game.realMoneyGame() && !game.isActive())) &&
                        game.getPlayers().size() <= game.getMaximalAmountOfPlayers())
                .collect(Collectors.toList());
    }

    public List<Game> findSpectateableGames(){
        List<Game> activeGames = gamesDb.getActiveGames();
        return activeGames.stream()
                .filter(Game::canBeSpectated)
                .collect(Collectors.toList());
    }

    private void handleJoinGameAsPlayer(Game game,User user) throws LeaguesDontMatchException, GameIsFullException, NoBalanceForBuyInException {
        int gameLeague=game.getLeague();
        int usersLeague=user.getCurrLeague();
        double userBalance=user.getBalance();
        double buyInPolicy=game.getBuyInPolicy();

        if(gameLeague != usersLeague)
            throw new LeaguesDontMatchException(String.format("Can't join game, user's league is %d ,while game's league is %d.",usersLeague,gameLeague));
        else if (game.isFull())
            throw new GameIsFullException("Can't join game as player because it's full.");
        else if(!game.realMoneyGame() && (userBalance < buyInPolicy))
            throw new NoBalanceForBuyInException(String.format("Buy in is %d, but user's balance is %d;",buyInPolicy,userBalance));

        game.joinGameAsPlayer(user);
    }
}
