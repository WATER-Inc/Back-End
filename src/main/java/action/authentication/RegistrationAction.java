package action.authentication;

import action.Action;
import action.parser.Parser;
import action.sender.SenderManager;
import com.fasterxml.jackson.databind.JsonNode;
import dao.PersistException;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationAction extends Action {
    final private static Logger logger = LogManager.getLogger(String.valueOf(RegistrationAction.class));

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
        String username = user.getUsername();
        String password = user.getPasswordHash();
        logger.debug("Trying to register with: [Username: " + username + "; Password: " + password + "]");
        UserService service = factory.getService(User.class);
        user.setUsername(username);
        user.setPasswordHash(password);
        try {
            user = service.persist(user);
        } catch (PersistException e) {

            try {
                SenderManager.sendObject(response, null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            request.setAttribute("message", "Пользователь уже существует!");
            logger.info(String.format("user \"%s\" unsuccessfully tried to register in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            return;
        }
        try {
            SenderManager.sendObject(response, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info(String.format("user \"%s\" is registered in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        return;
    }
}
