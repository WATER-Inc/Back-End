package service;

import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import entity.*;
import entity.auxiliary.ChatLink;
import entity.auxiliary.Participants;
import dao.mysql.MySqlChatDAO;
import dao.mysql.MySqlChatLinkDAO;

import java.util.List;

public class ChatService extends Service {
    public ChatService(MySqlDaoFactory factory) throws PersistException {
        super(Chat.class, factory);
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
        List<Chat> chats = (List<Chat>) super.getAll();
        MySqlChatLinkDAO chatLinkDAO = (MySqlChatLinkDAO) daoFactory.getDao(ChatLink.class);
        for (Chat chat : chats) {
            chat.setParticipants(chatLinkDAO.getParticipants(chat));
        }
        return chats;
    }

    @Override
    public Chat persist(Entity object) throws PersistException {
        Chat chat = (Chat) super.persist(object);
        chat.setParticipants(((Chat) object).getParticipants());
        addUsers(chat);
        return getById(chat.getId());
    }

    public void addUser(Chat chat, User user, Role role) throws PersistException {

        ChatLink chatLink = new ChatLink();
        chatLink.setUserId(user.getId());
        chatLink.setChatId(chat.getId());
        if(role == null){
            ServiceFactory factory = new ServiceFactory(daoFactory);
            RoleService roleService = factory.getService(Role.class);
            role = roleService.getByTitle("user");
        }
        chatLink.setRoleId(role.getId());
        daoFactory.getDao(ChatLink.class).persist(chatLink);
    }

    @Override
    public void delete(Entity object) throws PersistException {
        super.delete(object);
    }

    public void addUsers(Chat chat) throws PersistException {
        MySqlChatLinkDAO mySqlChatLinkDAO = (MySqlChatLinkDAO) daoFactory.getDao(ChatLink.class);
        Participants participants = chat.getParticipants();
        List<User> users = participants.getUsers();
        List<Role> roles = participants.getRoles();
        System.out.println("Test: " + users + " " + roles);
        ChatLink link = new ChatLink();
        for (int i = 0; i < users.size(); ++i) {
            link.setChatId(chat.getId());
            link.setUserId(users.get(i).getId());
            link.setRoleId(roles.get(i).getId());
            mySqlChatLinkDAO.persist(link);
        }
    }

}
