package TexasHoldem.data.users;

import TexasHoldem.common.Exceptions.EntityDoesNotExistsException;
import TexasHoldem.common.Exceptions.InvalidArgumentException;
import TexasHoldem.common.Exceptions.LoginException;
import TexasHoldem.data.Hybernate.HibernateUtil;
import TexasHoldem.domain.user.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import TexasHoldem.domain.user.Wallet;
import org.hibernate.HibernateException;
import org.hibernate.Session;


public class Users implements IUsers, IUsersForDistributionAlgorithm {
    private HashMap<String, User> _userList;

    public Users() {
        _userList = new HashMap<String, User>();
    }

    public void addUser(User user) throws InvalidArgumentException {
        verifyUserNameAndEmail(user.getUsername(),user.getPassword());
        User userToAdd = setUser(user);
        Session session = null;
        try{
            session = HibernateUtil.getInstance().getSessionFactory().openSession();
            session.beginTransaction();
            session.save(userToAdd);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            throw new InvalidArgumentException("Invalid Arguments");
        }finally {
            session.close();
        }
    }

    private static User setUser(User user)
    {
        User userToAdd = new User();
        userToAdd.setUsername(user.getUsername());
        userToAdd.setPassword(user.getPassword());
        userToAdd.setEmail(user.getEmail());
        userToAdd.setDateOfBirth(user.getDateOfBirth());
        userToAdd.setAmountEarnedInLeague(user.getAmountEarnedInLeague());
        userToAdd.setCurrLeague(user.getCurrLeague());
        userToAdd.setTotalNetoProfit(user.getTotalNetoProfit());
        userToAdd.setTotalGrossProfit(user.getTotalGrossProfit());
        userToAdd.setHighestCashGain(user.getHighestCashGain());
        userToAdd.setNumOfGamesPlayed(user.getNumOfGamesPlayed());
        userToAdd.setWallet(user.getWallet());

        return userToAdd;
    }

    public void editUser(String oldUser, String newUser, String pass, String email, LocalDate date) throws InvalidArgumentException, EntityDoesNotExistsException {
        if(!userNameExists(oldUser))
            throw new EntityDoesNotExistsException(String.format("'%s' doesn't exist in the system.",oldUser));

        User user=getUserByUserName(oldUser);
        String oldEmail=user.getEmail();
        if((!oldUser.equals(newUser)) || (!oldEmail.equals(email)))
            verifyUserNameAndEmail(oldUser,user.getEmail(),newUser,email);

        if(!user.getUsername().equals(newUser))
            user.setUsername(newUser);
        if(!user.getPassword().equals(pass))
            user.setPassword(pass);
        if(!user.getEmail().equals(email))
            user.setEmail(email);
        if(!user.getDateOfBirth().isEqual(date))
            user.setDateOfBirth(date);

        _userList.remove(oldUser);
        _userList.put(user.getUsername(),user);
    }

    public void deleteUser(User user) {
        _userList.remove(user.getUsername());
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

    public List<String> getAllUserNames(){
        return _userList.values().stream().map(User::getUsername).collect(Collectors.toList());
    }

    public User getUserByUserName(String userName) {
        return _userList.get(userName);
    }

    @Override
    public User getHighestBalance() {
        Optional<User> opt = _userList.values().stream().max(Comparator.comparingInt(User::getBalance));
        return opt.orElse(null);
    }

    private void verifyUserNameAndEmail(String oldUser,String oldEmail,String newName,String newEmail) throws InvalidArgumentException {
        boolean nameExists=false;
        boolean emailExists=false;

        if(!oldUser.equals(newName))
            nameExists=userNameExists(newName);
        if(!oldEmail.equals(newEmail))
            emailExists=emailExists(newEmail);

        checkUserNameAndEmailExistence(nameExists,emailExists);
    }

    private void verifyUserNameAndEmail(String username,String email) throws InvalidArgumentException {
        boolean nameExists=userNameExists(username);
        boolean emailExists=emailExists(email);

        checkUserNameAndEmailExistence(nameExists,emailExists);
    }

    private void checkUserNameAndEmailExistence(boolean nameExists, boolean emailExists) throws InvalidArgumentException {
        if(nameExists && emailExists)
            throw new InvalidArgumentException("Selected user name and e-mail already exist.");
        else if(nameExists)
            throw new InvalidArgumentException("Selected user name already exist.");
        else if(emailExists)
            throw new InvalidArgumentException("Selected e-mail already exist.");
    }


    public User verifyCredentials(String userName,String password) throws LoginException, EntityDoesNotExistsException {
        User user=getUserByUserName(userName);
        if(user == null)
            throw new EntityDoesNotExistsException(String.format("'%s' doesn't exist in the system.",userName));
        else if(!password.equals(user.getPassword()))
            throw new LoginException("Wrong password.");
        return user;
    }

    @Override
    public List<User> getAllUsersInList() {
        return new ArrayList(_userList.values());
    }

    public List<User> getUsersByLeague(int leagueNum) {
        List<User> usersInLeague = new ArrayList<>();
        for(User user : _userList.values()){
            if(user.getCurrLeague() == leagueNum)
                usersInLeague.add(user);
        }
        return usersInLeague;
    }
}