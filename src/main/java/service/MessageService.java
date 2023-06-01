package service;

import dao.PersistException;
import dao.mysql.MySqlMessageDAO;
import entity.Chat;
import entity.Entity;
import entity.Message;
import entity.User;

import java.util.List;

public class MessageService extends Service {
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

    public List<Message> getMessages(Chat chat) throws PersistException {
        return ((MySqlMessageDAO)genericDAO).getMessages(chat);
    }
    public List<Message> getMessages(User user) throws PersistException {
        return ((MySqlMessageDAO)genericDAO).getMessages(user);
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
