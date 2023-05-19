package service;

import dao.PersistException;
import entity.Entity;
import entity.User;

import java.util.List;

public class UserService extends GenericService {
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
}
