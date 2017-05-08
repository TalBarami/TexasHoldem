package TexasHoldem.data.users;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.user.User;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by RotemWald on 4/5/2017.
 */
public interface IUsers {
    void addUser(User user) throws InvalidArgumentException;
    void editUser(String oldUser, String newUser, String pass, String email, LocalDate date) throws InvalidArgumentException, EntityDoesNotExistsException;
    User verifyCredentials(String userName,String password) throws LoginException, EntityDoesNotExistsException;
    User getUserByUserName(String userName);
    User getHighestBalance();
    void deleteUser(String username) throws EntityDoesNotExistsException;
    List<User> getAllUsersInList();
    List<User> getUsersByLeague(int leagueNum);
}
