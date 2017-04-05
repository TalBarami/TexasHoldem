package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;

import TexasHoldem.domain.users.User;

/**
 * Created by אחיעד on 05/04/2017.
 */
public interface Bridge {


    boolean registerUser(String username, String password, String email, DateTime dateTime);

    boolean searchUser(String username);

    boolean deleteUser(String username);

    boolean login(String username, String password);

    boolean logout(String username);
}
