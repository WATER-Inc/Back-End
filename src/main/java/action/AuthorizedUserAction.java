package action;

import action.authentication.LoginAction;
import dao.PersistException;
import entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RoleService;
import service.ServiceFactory;

public abstract class AuthorizedUserAction extends Action {
    private static Logger logger = LogManager.getLogger(String.valueOf(LoginAction.class));

    public AuthorizedUserAction() throws PersistException {
        super();
        try {
            getAllowRoles().addAll(((RoleService) factory.getService(Role.class)).getAll());
        } catch (PersistException e) {
            logger.error("Failed to get all roles.", e);
        }
    }
}
