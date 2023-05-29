package action;

import action.authentication.LoginAction;
import dao.PersistException;
import entity.Role;
import org.apache.log4j.Logger;
import service.RoleService;
import service.ServiceFactory;

public abstract class AuthorizedUserAction extends Action {
    private static Logger logger = Logger.getLogger(String.valueOf(LoginAction.class));

    public AuthorizedUserAction() {
        super();
        factory = new ServiceFactory();//TODO
        try {
            getAllowRoles().addAll(((RoleService) factory.getService(Role.class)).getAll());
        } catch (PersistException e) {
            logger.error("Failed to get all roles.", e);
        }
    }
}
