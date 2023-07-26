package send_validate.validator;

import action.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import entity.Chat;
import entity.Message;
import entity.Role;
import entity.User;
import serealization_deserealization.deserialization.Deserializer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class MessageValidator extends Validator<Message> {
    @Override
    public Message validate(HttpServletRequest request) throws IncorrectFormDataException {
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            return null;
            // todo process request with no body
        }
        Deserializer deserializer = new Deserializer();
        Message message = null;
        try {
            message = deserializer.deserialize(jsonNode);
        } catch (IOException e) {
            logger.error("Error parsing JSON: " + e.getMessage());
        }
        return message;
    }
}
