package action.authentication;


import action.Action;
import action.sender.Sender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PersistException;
import entity.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

public class LoginAction extends Action {
    private static Logger logger = Logger.getLogger(String.valueOf(LoginAction.class));

    private JsonNode parseRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(sb.toString());
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {

        JsonNode jsonNode = null;
        try{
            jsonNode = parseRequest(request);
        }catch (IOException e){
            logger.debug("Request had no content");
            return;
            // todo process request with no body
        }

        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode = jsonNode.get("userpassword");
        if(usernameNode == null || passwordNode == null){
            logger.info("Get failed");
           return;
            // todo process request body without username & userpassword
        }
        String username = usernameNode.asText();
        String password = passwordNode.asText();

        if (username!=null && password!=null) {
            logger.debug("Trying to login with: [Username: " + username + "; Password: " + password + "]");
            UserService service = factory.getService(User.class);
            User user = service.getByUsernameAndPassword(username, password);
            if (user == null) {
                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return;
            }
            try {
                Sender.sendObject(response, user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HttpSession session = request.getSession();
            session.setAttribute("authorizedUser", user);
            logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        }
    }
}

