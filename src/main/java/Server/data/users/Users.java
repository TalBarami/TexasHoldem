package Server.data.users;

import Exceptions.EntityDoesNotExistsException;
import Exceptions.InvalidArgumentException;
import Exceptions.LoginException;

import Server.domain.user.User;

import java.time.LocalDate;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;


public class Users implements IUsers, IUsersForDistributionAlgorithm {
    private HashMap<String, User> _userList;

    public Users() {
        _userList = new HashMap<String, User>();
    }

    public void addUser(User user) throws InvalidArgumentException {
        verifyUserNameAndEmail(user.getUsername(),user.getEmail());
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            throw new InvalidArgumentException("Invalid Arguments");
        }finally {
            session.close();
        }
    }

    public void editUser(String oldUser, String newUser, String pass, String email, LocalDate date) throws InvalidArgumentException, EntityDoesNotExistsException {
        if(!userNameExists(oldUser))
            throw new EntityDoesNotExistsException(String.format("'%s' doesn't exist in the system.",oldUser));

        User userToUpdate = getUserByUserName(oldUser);
        String oldEmail = userToUpdate.getEmail();
        if((!oldUser.equals(newUser)) || (!oldEmail.equals(email)))
            verifyUserNameAndEmail(oldUser, userToUpdate.getEmail(), newUser, email);

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();

            if(!userToUpdate.getUsername().equals(newUser))
                userToUpdate.setUsername(newUser);
            if(!userToUpdate.getPassword().equals(pass))
                userToUpdate.setPassword(pass);
            if(!userToUpdate.getEmail().equals(email))
                userToUpdate.setEmail(email);
            if(!userToUpdate.getDateOfBirth().isEqual(date))
                userToUpdate.setDateOfBirth(date);

            session.update(userToUpdate);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
            throw new InvalidArgumentException("Invalid Arguments");
        }finally {
            session.close();
        }

        userToUpdate.notifyObservers();
    }

    public void deleteUser(User userToDel) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.delete(userToDel);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    private boolean emailExists(String email){
        List<String> emailList = getAllEmails();
        return emailList != null && emailList.contains(email);
    }

    private boolean userNameExists(String username){
        return getUserByUserName(username) != null;
    }

    private List<String> getAllEmails(){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<String> emailsList = null;
        try{
            session.beginTransaction();
            String sql = "SELECT email FROM users";
            emailsList = session.createSQLQuery(sql).list();
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return emailsList;
    }

    public List<String> getAllUserNames(){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<String> userNames = null;
        try{
            session.beginTransaction();
            String sql = "SELECT userName FROM users";
            userNames = session.createSQLQuery(sql).list();
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return userNames;
    }

    public User getUserByUserName(String userName) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        User user = null;
        try{
            session.beginTransaction();
            user = (User) session.get(User.class, userName);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return user;
    }

    @Override
    public User getHighestBalance() {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        User user = null;
        try{
            session.beginTransaction();
            user = (User) session.createCriteria(User.class)
                                        .addOrder(Order.desc("balance"))
                                        .setMaxResults(1)
                                        .uniqueResult();
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return user;
    }

    private void verifyUserNameAndEmail(String oldUser,String oldEmail,String newName,String newEmail) throws InvalidArgumentException {
        boolean nameExists=false;
        boolean emailExists=false;

        if(!oldUser.equals(newName))
            nameExists = userNameExists(newName);
        if(!oldEmail.equals(newEmail))
            emailExists = emailExists(newEmail);

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


    public User verifyCredentials(String userName,String password) throws EntityDoesNotExistsException, LoginException {
        User user=getUserByUserName(userName);
        if(user == null)
            throw new EntityDoesNotExistsException(String.format("'%s' doesn't exist in the system.",userName));
        else if(!password.equals(user.getPassword()))
            throw new LoginException("Wrong password.");
        return user;
    }

    @Override
    public List<User> getAllUsersInList() {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<User> allUsers = null;
        try{
            session.beginTransaction();
            allUsers = session.createCriteria(User.class).list();;
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return allUsers;
    }

    public List<User> getUsersByLeague(int leagueNum) {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<User> Users = null;
        try{
            session.beginTransaction();
            String stringQuery = "FROM users WHERE currLeague = " + leagueNum;
            Query query = session.createSQLQuery(stringQuery);
            Users = query.list();
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (session.getTransaction()!=null) session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return Users;
    }
}