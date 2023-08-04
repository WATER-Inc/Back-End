package action;

import action.authentication.LoginAction;
import dao.PersistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AuthorizedUserAction extends Action {
    private static Logger logger = LogManager.getLogger(String.valueOf(LoginAction.class));

    public AuthorizedUserAction() throws PersistException {
        super();
//        try {
//            getAllowRoles().addAll(((RoleService) factory.getService(Role.class)).getAll());
//        } catch (PersistException e) {
//            logger.error("Failed to get all roles.", e);
//        }TODO maybe later
    }
}
