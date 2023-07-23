package action.common;

import action.AuthorizedUserAction;
import action.chat.ChatAction;
import dao.PersistException;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class CommonAction extends AuthorizedUserAction {
    protected static Logger logger = LogManager.getLogger(CommonAction.class);
    public CommonAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}