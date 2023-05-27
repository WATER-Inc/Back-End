package service;

import dao.PersistException;
import dao.mysql.MySqlChatDAO;
import entity.Chat;
import entity.Entity;
import entity.User;

import java.util.List;

public class ChatService extends Service {
    public ChatService() throws PersistException {
        super(Chat.class);
    }

    public List<Chat> getByUser(User user) throws PersistException {
        return ((MySqlChatDAO) genericDAO).getByUser(user);
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
