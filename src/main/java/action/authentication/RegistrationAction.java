package action.authentication;

import action.Action;
import action.sender.Sender;
import dao.PersistException;
import entity.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationAction extends Action {
    final private static Logger logger = Logger.getLogger(String.valueOf(RegistrationAction.class));

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.debug("Trying to register with: [Username: " + username + "; Password: " + password + "]");
        if (username != null && password != null) {
            UserService service = factory.getService(User.class);
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(password);
            try {
                user = service.persist(user);
            } catch (PersistException e) {
                user = null;
                request.setAttribute("message", "Пользователь уже существует!");
                logger.info(String.format("user \"%s\" unsuccessfully tried to register in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            }
            try {
                Sender.sendObject(response, user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (user != null)
                logger.info(String.format("user \"%s\" is registered in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            return;
        }
        return;
    }
}
