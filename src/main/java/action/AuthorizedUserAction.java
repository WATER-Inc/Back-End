package action;

import dao.PersistException;
import entity.Role;
import org.apache.log4j.Logger;
import service.RoleService;
import service.ServiceFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
