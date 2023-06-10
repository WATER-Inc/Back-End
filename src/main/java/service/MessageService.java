package service;

import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlMessageDAO;
import entity.Chat;
import entity.Entity;
import entity.Message;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class MessageService extends Service {
    public MessageService(MySqlDaoFactory factory) throws PersistException {
        super(Message.class, factory);
    }

    @Override
    public Message getById(String id) throws PersistException {
        return (Message) super.getById(id);
    }

    @Override
    public List<Message> getAll() throws PersistException {
        List<Message> messages = (List<Message>) super.getAll();
        ServiceFactory factory = new ServiceFactory(daoFactory);
        UserService userService = factory.getService(User.class);
        ChatService chatService = factory.getService(Chat.class);
        for (Message message : messages) {
            message.setSender(userService.getById(message.getSender().getId()));
            message.setChat(chatService.getById(message.getChat().getId()));
        }
        return messages;
    }

    @Override
    public Message persist(Entity object) throws PersistException {
        return (Message) super.persist(object);
    }

    protected List<Message> getById(List<Message> messagesId) throws PersistException {
        List<Message> messages = new ArrayList<>();
        ServiceFactory factory = new ServiceFactory(daoFactory);
        MessageService service = factory.getService(Message.class);
        for (int i = 0; i < messagesId.size(); ++i)
            messages.add(service.getById(messagesId.get(i).getId()));
        return messages;
    }

    public List<Message> getMessages(Chat chat) throws PersistException {
        return getById(((MySqlMessageDAO) genericDAO).getMessages(chat));
    }

    public List<Message> getMessages(User user) throws PersistException {
        return getById(((MySqlMessageDAO) genericDAO).getMessages(user));
    }

}
