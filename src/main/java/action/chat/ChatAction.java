package action.chat;

import action.Action;
import action.AuthorizedUserAction;
import dao.PersistException;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class ChatAction extends AuthorizedUserAction {
    protected static Logger logger = LogManager.getLogger(ChatAction.class);
    public ChatAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}