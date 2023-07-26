package send_validate.validator;

import action.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import entity.User;
import serealization_deserealization.deserialization.Deserializer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserValidator extends Validator<User> {
    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            return null;
            // todo process request with no body
        }
        Deserializer deserializer = new Deserializer();
        User user = null;
        try {
            user = deserializer.deserialize(jsonNode);
        } catch (IOException e) {
            logger.error("Error parsing JSON: " + e.getMessage());
        }
        return user;
    }
}
