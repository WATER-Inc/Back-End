package validator;

import action.parser.Parser;
import action.sender.SenderManager;
import com.fasterxml.jackson.databind.JsonNode;
import entity.Chat;
import entity.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ChatService;
import service.MessageService;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ChatValidator implements Validator<Chat> {
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
        JsonNode chatIdNode = null;
        JsonNode lastMessageDateNode = null;
        if (jsonNode == null) {
            return null;
        }
        chatIdNode = jsonNode.get("chatId");
        lastMessageDateNode = jsonNode.get("lastMessageDate");
        if (chatIdNode == null) {
            return null;
        }
        String chatId = chatIdNode.asText();
        Date date = null;
        if (lastMessageDateNode != null)
            date = new Date(Long.valueOf(lastMessageDateNode.asText()));
        logger.info(chatId + " " + date);
        Chat chat = new Chat();
        chat.setId(chatId);
        chat.setLastMessageDate(date);
        return chat;
    }
}
