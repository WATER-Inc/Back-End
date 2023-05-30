package action.authentication;

import action.Action;
import action.parser.Parser;
import action.sender.Sender;
import com.fasterxml.jackson.databind.JsonNode;
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
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            logger.debug("Request had no content");
            return;
            // todo process request with no body
        }
        JsonNode usernameNode = null;
        JsonNode passwordNode = null;
        if (jsonNode != null) {
            usernameNode = jsonNode.get("username");
            passwordNode = jsonNode.get("userpassword");
        }
        if (usernameNode == null || passwordNode == null) {
            logger.info("Get failed");
            return;
            // todo process request body without username & userpassword
        }
        String username = usernameNode.asText();
        String password = passwordNode.asText();
        logger.debug("Trying to register with: [Username: " + username + "; Password: " + password + "]");
        if (username != null && password != null) {
            UserService service = factory.getService(User.class);
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(password);
            try {
                user = service.persist(user);
            } catch (PersistException e) {

                try {
                    Sender.sendObject(response, null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                request.setAttribute("message", "Пользователь уже существует!");
                logger.info(String.format("user \"%s\" unsuccessfully tried to register in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return;
            }
            try {
                Sender.sendObject(response, user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logger.info(String.format("user \"%s\" is registered in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            return;
        }
        return;
    }
}
