package by.fpmibsu.water.dao;

import by.fpmibsu.water.entity.User;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class UserDAO implements GenericDAO<User>{

    @Override
    public User create() throws PersistException {
        return null;
    }

    @Override
    public User getByPK(Serializable key) throws PersistException {
        return null;
    }

    @Override
    public List<User> getAll() throws PersistException {
        return null;
    }

    @Override
    public void delete(Identified object) throws PersistException {

    }

    @Override
    public void update(Identified object) throws PersistException {

    }

    @Override
    public User persist(Identified object) throws PersistException {
        return null;
    }
}
