package TexasHoldem.AcceptanceTests;

import org.joda.time.DateTime;

import TexasHoldem.domain.users.User;

/**
 * Created by אחיעד on 05/04/2017.
 */
public class ProxyBridge implements Bridge {

    private Bridge real;

    public ProxyBridge(){
        real = null;
    }

    public void setRealBridge(Bridge impl)
    {
        if(real == null)
        {
            real = impl;
        }
    }


    public boolean registerUser(String username, String password, String email, DateTime dateTime) {
        return true;
    }

    public boolean searchUser(String username) {
        return true;
    }

    public  boolean deleteUser(String username){
        return true;
    }

    public boolean login(String username, String password)
    {
        return true;
    }
    public boolean logout(String username)
    {
        return true;
    }
}
