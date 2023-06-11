package service;

import dao.PersistException;
import entity.Chat;
import entity.Entity;

import java.util.List;

public class ChatService extends GenericService {
    public ChatService() throws PersistException {
        super(Chat.class);
    }

    @Override
    public Chat getById(String id) throws PersistException {
<<<<<<< Updated upstream
        return (Chat) super.getById(id);
=======
        Chat chat = (Chat) super.getById(id);
        chat.setLastMessageDate(getLastMessageDate(chat));
        return chat;
    }

    private Date getLastMessageDate(Chat chat) throws PersistException {
        ServiceFactory factory = new ServiceFactory(daoFactory);
        MessageService service = factory.getService(Message.class);
        List<Message> messages = service.getMessages(chat);
        System.out.println("Messages: " + messages.toString());
        return messages.stream().max(Comparator.comparing(Message::getDate)).orElse(null).getDate();
>>>>>>> Stashed changes
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
