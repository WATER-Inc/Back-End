package action.authentication;

import action.AuthorizedUserAction;
import send_validate.sender.SenderManager;
import dao.PersistException;
import entity.User;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutAction extends AuthorizedUserAction {
    private static Logger logger = LogManager.getLogger(LogoutAction.class);

    public LogoutAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        User user = getAuthorizedUser();
        try {
            SenderManager.sendObject(response, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info(String.format("user \"%s\" is logged out", user.getUsername()));
        request.getSession().invalidate();
    }
}
