package service;

import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlUserDAO;
import entity.Entity;
import entity.User;

import java.util.List;

public class UserService extends Service {
    public UserService(MySqlDaoFactory factory) throws PersistException {
        super(User.class, factory);
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
            if (user!= null && user.getPasswordHash().equals(password))
                return user;
            return null;
        } catch (PersistException exception) {
            return null;
        }
    }

    public User getByUsername(String username) throws PersistException {
        User user = null;
        try {
            user = ((MySqlUserDAO) genericDAO).getByUsername(username);
            return user;
        } catch (PersistException ex) {
            return null;
        }
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
