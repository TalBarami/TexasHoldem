package TexasHoldem.data.users;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.user.User;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class Users implements IUsers {
    private HashMap<String, User> _userList;

    public Users() {
        _userList = new HashMap<String, User>();
    }

    public void addUser(User user) throws InvalidArgumentException {
        verifyUserNameAndEmail(user.getUsername(),user.getEmail());
        _userList.put(user.getUsername(),user);
    }

    public void editUser(String oldUser, String newUser, String pass, String email, LocalDate date) throws InvalidArgumentException {
        verifyUserNameAndEmail(newUser,email);

        User user=getUserByUserName(oldUser);
        if(!user.getUsername().equals(newUser))
            user.setUsername(newUser);
        if(!user.getPassword().equals(pass))
            user.setPassword(pass);
        if(!user.getEmail().equals(email))
            user.setEmail(email);
        if(!user.getDateOfBirth().isEqual(date))
            user.setDateOfBirth(date);
    }

    public void deleteUser(String username) throws EntityDoesNotExistsException {
        if(!_userList.containsKey(username)){
            throw new EntityDoesNotExistsException("This user name is not registered in the system.");
        }
        _userList.remove(username);
    }

    private boolean emailExists(String email){
        return getAllEmails().contains(email);
    }

    private boolean userNameExists(String username){
        return getAllUserNames().contains(username);
    }

    private List<String> getAllEmails(){
        return _userList.values().stream().map(User::getEmail).collect(Collectors.toList());
    }

    private List<String> getAllUserNames(){
        return _userList.values().stream().map(User::getUsername).collect(Collectors.toList());
    }

    public User getUserByUserName(String userName){
        return _userList.get(userName);
    }

    private void verifyUserNameAndEmail(String username,String email) throws InvalidArgumentException {
        boolean nameExists=userNameExists(username);
        boolean emailExists=emailExists(email);

        if(nameExists && emailExists)
            throw new InvalidArgumentException("Selected user name and e-mail already exist.");
        else if(nameExists)
            throw new InvalidArgumentException("Selected user name already exist.");
        else if(emailExists)
            throw new InvalidArgumentException("Selected e-mail already exist.");
    }

    public User verifyCredentials(String userName,String password) throws LoginException {
        User user=getUserByUserName(userName);
        if(user == null)
            throw new LoginException("User name doesn't exist in the system");
        else if(!password.equals(user.getPassword()))
            throw new LoginException("Wrong password.");
        return user;
    }

}