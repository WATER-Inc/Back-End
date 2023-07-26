package send_validate.validator;

import action.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import entity.Chat;
import entity.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serealization_deserealization.deserialization.Deserializer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class ChatValidator extends Validator<Chat> {
    private static Logger logger = LogManager.getLogger(ChatValidator.class);
    @Override
    public Chat validate(HttpServletRequest request) throws IncorrectFormDataException {
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            return null;
            // todo process request with no body
        }
        Deserializer deserializer = new Deserializer();
        Chat chat = null;
        try {
            chat = deserializer.deserialize(jsonNode);
        } catch (IOException e) {
            logger.error("Error parsing JSON: " + e.getMessage());
        }
        return chat;
    }
}
