package action.http.common;

import action.http.AuthorizedUserHttpAction;
import dao.PersistException;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract public class CommonHttpAction extends AuthorizedUserHttpAction {
    protected static Logger logger = LogManager.getLogger(CommonHttpAction.class);
    public CommonHttpAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}