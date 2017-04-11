package TexasHoldem.data.users;

import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.domain.users.User;

import java.time.LocalDate;

/**
 * Created by RotemWald on 4/5/2017.
 */
public interface IUsers {
    void addUser(User user) throws InvalidArgumentException;
    void editUser(String oldUser, String newUser, String pass, String email, LocalDate date) throws InvalidArgumentException;
}
