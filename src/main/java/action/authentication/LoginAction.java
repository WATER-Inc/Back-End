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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginAction extends Action {
    private static Logger logger = LogManager.getLogger(String.valueOf(LoginAction.class));


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
            try {
                SenderManager.sendObject(response, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logger.info("Get failed");
            return;
            // todo process request body without username & userpassword
        }
        String username = usernameNode.asText();
        String password = passwordNode.asText();

        if (username != null && password != null) {
            logger.debug("Trying to login with: [Username: " + username + "; Password: " + password + "]");
            UserService service = factory.getService(User.class);
            User user = service.getByUsernameAndPassword(username, password);
            if (user == null) {
                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            }
            try {
                SenderManager.sendObject(response, user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HttpSession session = request.getSession();
            logger.debug("|" + session + "|");
            if (session != null)
                session.setAttribute("authorizedUser", user);
            logger.debug(request.getSession());
            logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        }
    }
}

