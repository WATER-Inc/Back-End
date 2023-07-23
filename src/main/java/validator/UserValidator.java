package validator;

import action.parser.Parser;
import action.sender.SenderManager;
import com.fasterxml.jackson.databind.JsonNode;
import entity.User;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserValidator implements Validator<User> {
    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            return null;
            // todo process request with no body
        }
        JsonNode usernameNode = null;
        JsonNode passwordNode = null;
        if (jsonNode != null) {
            usernameNode = jsonNode.get("username");
            passwordNode = jsonNode.get("userpassword");
        }
        if (usernameNode == null) {
            return null;
            // todo process request body without username & userpassword
        }
        String username = usernameNode.asText();

        String password = null;
        if(passwordNode != null)
           password = passwordNode.asText();
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(password);
        return user;
    }
}
