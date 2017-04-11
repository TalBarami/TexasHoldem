package TexasHoldem.data.users;

import TexasHoldem.domain.users.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by RotemWald on 4/5/2017.
 */
public class Users implements IUsers {
    private HashMap<String, User> _userList;

    public Users() {
        _userList = new HashMap<String, User>();
    }

    public boolean addUser(User user) {
        if (!userExists(user)) {
            _userList.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public boolean editUser(User user) {
        _userList.put(user.getUsername(), user);
        return true;
    }

    public boolean userExists(User user) {
        return _userList.containsKey(user.getUsername());
    }

    public List<String> getAllEmails(){
        return _userList.values().stream().map(User::getEmail).collect(Collectors.toList());
    }

    public List<String> getAllUserNames(){
        return _userList.values().stream().map(User::getUsername).collect(Collectors.toList());
    }

    public String getPassByUserName(String userName){
        User user = _userList.get(userName);
        return user == null ? null : user.getPassword();
    }

    public User getUserByUserName(String userName){
        return _userList.get(userName);
    }
}