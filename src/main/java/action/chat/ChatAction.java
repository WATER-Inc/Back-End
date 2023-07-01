package action.chat;

import action.Action;
import action.AuthorizedUserAction;
import dao.PersistException;
import entity.Chat;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.AsyncContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

abstract public class ChatAction extends AuthorizedUserAction {
    protected static Logger logger = LogManager.getLogger(ChatAction.class);
    public ChatAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}