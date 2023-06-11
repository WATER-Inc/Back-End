package service;

import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import entity.*;
import entity.auxiliary.ChatLink;
import entity.auxiliary.Participants;
import dao.mysql.MySqlChatDAO;
import dao.mysql.MySqlChatLinkDAO;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChatService extends Service {
    public ChatService(MySqlDaoFactory factory) throws PersistException {
        super(Chat.class, factory);
    }

    public List<Chat> getByUser(User user) throws PersistException {
        List<Chat> chats = ((MySqlChatDAO) genericDAO).getByUser(user);
        for (Chat chat : chats)
            chat.setLastMessageDate(getLastMessageDate(chat));
        return chats;
    }

    @Override
    public Chat getById(String id) throws PersistException {
        Chat chat = (Chat) super.getById(id);
        chat.setLastMessageDate(getLastMessageDate(chat));
        return chat;
    }

    private Date getLastMessageDate(Chat chat) throws PersistException {
        ServiceFactory factory = new ServiceFactory(daoFactory);
        MessageService service = factory.getService(Message.class);
        List<Message> messages = service.getMessages(chat);
        return messages.stream().max(Comparator.comparing(Message::getDate)).orElse(null).getDate();
    }

    @Override
    public List<Chat> getAll() throws PersistException {
        List<Chat> chats = (List<Chat>) super.getAll();
        MySqlChatLinkDAO chatLinkDAO = (MySqlChatLinkDAO) daoFactory.getDao(ChatLink.class);
        for (Chat chat : chats) {
            chat.setParticipants(chatLinkDAO.getParticipants(chat));
            chat.setLastMessageDate(getLastMessageDate(chat));
        }
        return chats;
    }

    @Override
    public Chat persist(Entity object) throws PersistException {
        Chat chat = (Chat) super.persist(object);
        chat.setParticipants(((Chat) object).getParticipants());
        addUsers((Chat) chat);
        chat.setLastMessageDate(getLastMessageDate(chat));
        return getById(chat.getId());
    }

    private void addUser(Chat chat, User user, Role role) throws PersistException {
        ChatLink chatLink = new ChatLink();
        chatLink.setUserId(user.getId());
        chatLink.setRoleId(role.getId());
        chatLink.setChatId(chat.getId());
        daoFactory.getDao(ChatLink.class).persist(chatLink);
    }

    @Override
    public void delete(Entity object) throws PersistException {
        super.delete(object);
    }

    private void addUsers(Chat chat) throws PersistException {
        MySqlChatLinkDAO mySqlChatLinkDAO = (MySqlChatLinkDAO) daoFactory.getDao(ChatLink.class);
        Participants participants = chat.getParticipants();
        List<User> users = participants.getUsers();
        List<Role> roles = participants.getRoles();
        ChatLink link = new ChatLink();
        for (int i = 0; i < users.size(); ++i) {
            link.setChatId(chat.getId());
            link.setUserId(users.get(i).getId());
            link.setRoleId(roles.get(i).getId());
            mySqlChatLinkDAO.persist(link);
        }
    }

}
