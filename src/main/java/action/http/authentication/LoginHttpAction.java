package action.http.authentication;


import action.http.HttpAction;
import action.sender.SenderManager;
import dao.PersistException;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginHttpAction extends HttpAction {
    private static final Logger logger = LogManager.getLogger(String.valueOf(LoginHttpAction.class));


    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        User user = null;
        try {
            user = ValidatorFactory.createValidator(User.class).validate(request);
        } catch (IncorrectFormDataException ignored) {
        }
        if (user == null) {
            logger.debug("Request had no content");
            return;
        }
        UserService service = getFactory().getService(User.class);
        User copy = user;
        user = service.getByUsernameAndPassword(user.getUsername(), user.getPasswordHash());
        if (user == null) {
            request.setAttribute("message", "Имя пользователя или пароль не опознанны");
            logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", copy.getUsername(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        }
        try {
            SenderManager.sendObject(response, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = request.getSession();
        if (session != null)
            session.setAttribute("authorizedUser", user);
        logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", copy.getUsername(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
    }
}

