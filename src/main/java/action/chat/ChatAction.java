package action.chat;

import action.Action;
import action.AuthorizedUserAction;
import dao.PersistException;
import entity.Chat;
import entity.Role;

import javax.servlet.AsyncContext;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

abstract public class ChatAction extends AuthorizedUserAction {
    public static Map<String, List<AsyncContext>> map;

    static {
        List<Chat> chats = null;
        try {
            chats = (List<Chat>) factory.getService(Chat.class).getAll();
        } catch (PersistException e) {
            throw new RuntimeException(e);
        }
        for (Chat chat : chats)
            map.put(chat.getId(), new CopyOnWriteArrayList<>());
    }

    public ChatAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}