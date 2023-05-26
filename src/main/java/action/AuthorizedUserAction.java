package action;

import dao.PersistException;
import entity.Role;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;

public abstract class AuthorizedUserAction extends Action {
    private static Logger logger = Logger.getLogger(String.valueOf(LoginAction.class));
    public AuthorizedUserAction() {
        super();
        try {
            getAllowRoles().addAll((Collection<? extends Role>) Arrays.asList(factory.getService(Role.class).getAll()));
        } catch (PersistException e) {
            logger.error("Failed to get all roles.", e);
        }
    }
}
