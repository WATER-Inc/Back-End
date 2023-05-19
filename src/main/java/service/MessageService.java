package service;

import dao.PersistException;
import entity.Entity;
import entity.Message;

import java.util.List;

public class MessageService extends GenericService {
    public MessageService() throws PersistException {
        super(Message.class);
    }

    @Override
    public Message getById(String id) throws PersistException {
        return (Message) super.getById(id);
    }

    @Override
    public List<Message> getAll() throws PersistException {
        return (List<Message>) super.getAll();
    }

    @Override
    public Message persist(Entity object) throws PersistException {
        return (Message) super.persist(object);
    }


}
