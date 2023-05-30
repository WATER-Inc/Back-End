package service;

import dao.PersistException;
import dao.mysql.MySqlChatDAO;
import dao.mysql.MySqlUserDAO;
import entity.Chat;
import entity.Entity;
import entity.User;
import validator.IncorrectFormDataException;

import java.util.List;
import java.util.stream.Collectors;

public class UserService extends Service {
    public UserService() throws PersistException {
        super(User.class);
    }

    @Override
    public User getById(String id) throws PersistException {
        return (User) super.getById(id);
    }

    @Override
    public List<User> getAll() throws PersistException {
        return (List<User>) super.getAll();
    }

    @Override
    public User persist(Entity object) throws PersistException {
        return (User) super.persist(object);
    }

    public User getByUsernameAndPassword(String username, String password) throws PersistException {
        User user = null;
        try {
            user = ((MySqlUserDAO) genericDAO).getByUsername(username);
        } catch (PersistException exception) {
            throw new PersistException("Incorrect Username");
        }
        if (user != null && user.getPasswordHash().equals(password))
            return user;
        else
            throw new PersistException("Incorrect Password");
    }

    public User getByUsername(String username) throws PersistException {
        User user = null;
        try {
            user = ((MySqlUserDAO) genericDAO).getByUsername(username);
        } catch (PersistException ex) {
            throw new PersistException("Incorrect Username");
        }
        return user;
    }
}
