package action.http;

import action.http.authentication.LoginHttpAction;
import dao.PersistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AuthorizedUserHttpAction extends HttpAction {
    private static Logger logger = LogManager.getLogger(String.valueOf(LoginHttpAction.class));

    public AuthorizedUserHttpAction() throws PersistException {
        super();
//        try {
//            getAllowRoles().addAll(((RoleService) factory.getService(Role.class)).getAll());
//        } catch (PersistException e) {
//            logger.error("Failed to get all roles.", e);
//        }TODO maybe later
    }
}
