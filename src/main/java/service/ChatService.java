package service;

import dao.PersistException;
import entity.Chat;
import entity.Entity;

import java.util.List;

public class ChatService extends Service {
    public ChatService() throws PersistException {
        super(Chat.class);
    }

    @Override
    public Chat getById(String id) throws PersistException {
        return (Chat) super.getById(id);
    }

    @Override
    public List<Chat> getAll() throws PersistException {
        return (List<Chat>) super.getAll();
    }

    @Override
    public Chat persist(Entity object) throws PersistException {
        return (Chat) super.persist(object);
    }
}
