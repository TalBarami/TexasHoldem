package Server.data.users;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;
import Server.domain.user.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by RotemWald on 4/5/2017.
 */
public interface IUsers {
    void addUser(User user) throws InvalidArgumentException;
    void editUser(String oldUser, String newUser, String pass, String email, LocalDate date,String image) throws InvalidArgumentException, EntityDoesNotExistsException;
    User verifyCredentials(String userName,String password) throws LoginException, EntityDoesNotExistsException;
    User getUserByUserName(String userName) throws EntityDoesNotExistsException;
    User getHighestBalance();
    void deleteUser(User user) throws EntityDoesNotExistsException;
    List<User> getAllUsersInList();
    List<User> getUsersByLeague(int leagueNum);
    List<String> getAllUserNames();
}
