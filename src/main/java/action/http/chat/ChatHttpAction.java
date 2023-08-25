package action.http.chat;

import action.http.AuthorizedUserHttpAction;
import dao.PersistException;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class ChatHttpAction extends AuthorizedUserHttpAction {
    protected static Logger logger = LogManager.getLogger(ChatHttpAction.class);
    public ChatHttpAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}