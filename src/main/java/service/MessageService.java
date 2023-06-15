package service;

import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlMessageDAO;
import entity.Chat;
import entity.Entity;
import entity.Message;
import entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService extends Service {
    public MessageService(MySqlDaoFactory factory) throws PersistException {
        super(Message.class, factory);
    }

    @Override
    public Message getById(String id) throws PersistException {
        Message message = (Message) super.getById(id);
        ServiceFactory factory = new ServiceFactory(daoFactory);
//        message.setChat((Chat) factory.getService(Chat.class).getById(message.getChat().getId()));//TODO recursive send
        message.setSender((User) factory.getService(User.class).getById(message.getSender().getId()));
        return message;
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

    protected List<Message> getListById(List<Message> messagesId) throws PersistException {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < messagesId.size(); ++i)
            messages.add(getById(messagesId.get(i).getId()));
        return messages;
    }

    public List<Message> getMessages(Chat chat) throws PersistException {
        return getListById(((MySqlMessageDAO) genericDAO).getMessages(chat));
    }

    public List<Message> getMessages(Chat chat, Date date) throws PersistException {
        //TODO create SQL function
        if (date == null)
            return getMessages(chat);
        else
            return getMessages(chat).stream().filter(message -> message.getDate().after(date)).collect(Collectors.toList());
    }

    public List<Message> getMessages(User user) throws PersistException {
        return getListById(((MySqlMessageDAO) genericDAO).getMessages(user));
    }

}
