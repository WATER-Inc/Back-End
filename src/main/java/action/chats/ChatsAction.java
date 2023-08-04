package action.chats;


import action.AuthorizedUserAction;
import dao.PersistException;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class ChatsAction extends AuthorizedUserAction {
    protected static Logger logger = LogManager.getLogger(ChatsAction.class);
    public ChatsAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }

}