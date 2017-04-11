package TexasHoldem.data.users;

import TexasHoldem.domain.users.User;

/**
 * Created by RotemWald on 4/5/2017.
 */
public interface IUsers {
    boolean addUser(User user);
    boolean editUser(User user);
    boolean userExists(User user);
}
