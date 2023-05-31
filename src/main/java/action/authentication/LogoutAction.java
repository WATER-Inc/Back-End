package action.authentication;

import action.AuthorizedUserAction;
import controller.session.SessionManager;
import dao.PersistException;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction extends AuthorizedUserAction {
    private static Logger logger = Logger.getLogger(LogoutAction.class);

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        User user = getAuthorizedUser();
        logger.info(String.format("user \"%s\" is logged out", user.getUsername()));
        SessionManager.getSession().invalidate();
    }
}
