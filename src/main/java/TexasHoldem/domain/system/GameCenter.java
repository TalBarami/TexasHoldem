package TexasHoldem.domain.system;

import TexasHoldem.common.Exceptions.ArgumentNotInBoundsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.data.users.Users;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.game.leagues.LeagueManager;
import TexasHoldem.domain.users.User;

import javax.security.auth.login.LoginException;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameCenter {
    private List<Game> activeGames;
    private List<User> loggedInUsers;
    private Users usersDb;
    private LeagueManager leagueManager;

    public GameCenter() {
        activeGames=new ArrayList<>();
        usersDb=new Users();
        leagueManager = new LeagueManager();
    }

    public void registerUser(String userName, String pass, String email, LocalDate date, BufferedImage img) throws InvalidArgumentException {
        User newUser = new User(userName,pass,email,date,img);
        usersDb.addUser(newUser);
        leagueManager.addNewUserToLegue(newUser);
    }

    public void login(String userName,String pass) throws LoginException {
        String password=usersDb.getPassByUserName(userName);
        if(password == null)
            throw new LoginException("User name doesn't exist in the system");
        else if(!password.equals(pass))
            throw new LoginException("Wrong password.");
        loggedInUsers.add(usersDb.getUserByUserName(userName));//todo: or maybe change status in Db that he logged in?
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

        //remove from logged in users
            loggedInUsers=loggedInUsers.stream().filter(user -> !user.getUsername().equals(userName)).collect(Collectors.toList());
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidArgumentException {
        usersDb.editUser(originalUserName,newUserName,pass,email,date);
    }

    public void depositMoney(String userName,int amount) throws ArgumentNotInBoundsException {
        usersDb.getUserByUserName(userName).deposit(amount,false);
    }

    public List<Game> findAvailableGames(User user){
        return activeGames.stream().filter(game -> game.getLeague() == user.getCurrLeague() &&
                game.getBuyInPolicy() <= user.getBalance() &&
                (game.realMoneyGame() || (!game.realMoneyGame() && !game.isActive())) &&
                game.getPlayers().size() <= game.getMaximalAmountOfPlayers())
                .collect(Collectors.toList());
    }
}
