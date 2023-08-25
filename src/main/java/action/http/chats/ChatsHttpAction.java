package action.http.chats;


import action.http.AuthorizedUserHttpAction;
import dao.PersistException;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class ChatsHttpAction extends AuthorizedUserHttpAction {
    protected static Logger logger = LogManager.getLogger(ChatsHttpAction.class);
    public ChatsHttpAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }

}