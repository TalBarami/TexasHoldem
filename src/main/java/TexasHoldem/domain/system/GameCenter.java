package TexasHoldem.domain.system;

import TexasHoldem.common.Exceptions.InvalidFieldException;
import TexasHoldem.data.users.Users;
import TexasHoldem.domain.game.Game;
import TexasHoldem.domain.users.User;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameCenter {
    private List<Game> activeGames;
    private List<User> loggedInUsers;
    private Users usersDb;
    public GameCenter() {
        activeGames=new ArrayList<>();
        usersDb=new Users();
    }
    public void registerUser(String userName, String pass, String email, LocalDate date) throws InvalidFieldException {
        List<String> users=usersDb.getAllUserNames();
        List<String> emails=usersDb.getAllEmails();
        if(users.contains(userName) && emails.contains(email))
            throw new InvalidFieldException("Selected user name and e-mail already exist.");
        else if(users.contains(userName))
            throw new InvalidFieldException("Selected user name already exist.");
        else if(emails.contains(email))
            throw new InvalidFieldException("Selected e-mail already exist.");
        else
            usersDb.addUser(new User(userName,pass,email,date));
    }

    public void login(String userName,String pass) throws LoginException {
        String password=usersDb.getPassByUserName(userName);
        if(password == null)
            throw new LoginException("User name doesn't exist in the system");
        else if(!password.equals(userName))
            throw new LoginException("Wrong password.");
        loggedInUsers.add(usersDb.getUserByUserName(userName));//todo: or maybe change status in Db that he logged in?
    }

    public void logout(String userName){
        //remove from all playing rooms
        loggedInUsers.forEach(user -> {
            if(user.getUsername().equals(userName))
                user.getGamePlayerMappings().forEach((k,v) -> k.removePlayer(v));
        });

        //remove from logged in users
        loggedInUsers=loggedInUsers.stream().filter(user -> !user.getUsername().equals(userName)).collect(Collectors.toList());
    }

    public void editProfile(String originalUserName,String newUserName, String pass,String email, LocalDate date) throws InvalidFieldException {
        if(usersDb.getAllEmails().contains(email))
           throw new InvalidFieldException("Edit cannot be done, e-mail already registered in the system.");
        if(usersDb.getAllUserNames().contains(newUserName))
            throw new InvalidFieldException("Edit cannot be done, new user name already registered in the system.");

        User user=usersDb.getUserByUserName(originalUserName);
        if(!user.getUsername().equals(newUserName))
           user.setUsername(newUserName);
        if(!user.getPassword().equals(pass))
            user.setPassword(pass);
        if(!user.getEmail().equals(email))
            user.setEmail(email);
        if(!user.getDateofbirth().isEqual(date))
            user.setDateOfBirth(date);
    }

    public void depositMoney(String userName,double amount){
        usersDb.getUserByUserName(userName).getWallet().deposit(amount);
    }
}
