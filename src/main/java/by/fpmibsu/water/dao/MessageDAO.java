package by.fpmibsu.water.dao;

import by.fpmibsu.water.entity.Message;

import java.io.Serializable;
import java.util.List;

public class MessageDAO implements GenericDAO<Message> {

    @Override
    public Message create() throws PersistException {
        return null;
    }

    @Override
    public Message persist(Identified object) throws PersistException {
        return null;
    }

    @Override
    public Message getByPrimaryKey(Serializable key) throws PersistException {
        return null;
    }

    @Override
    public void delete(Identified object) throws PersistException {

    }

    @Override
    public void update(Identified object) throws PersistException {

    }

    @Override
    public List<Message> getAll() throws PersistException {
        return null;
    }



}
