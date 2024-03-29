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

public class ChatValidator extends Validator<Chat> {

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
        JsonNode chatNameNode = null;
        JsonNode lastMessageDateNode = null;
        if (jsonNode == null) {
            return null;
        }
        chatIdNode = jsonNode.get("chatId");
        chatNameNode = jsonNode.get("chatName");
        lastMessageDateNode = jsonNode.get("lastMessageDate");

        String chatId = null;
        if(chatIdNode != null)
            chatId = chatIdNode.asText();
        String chatName = null;
        if(chatNameNode != null)
            chatName = chatNameNode.asText();
        Date date = null;
        if (lastMessageDateNode != null)
            date = new Date(Long.valueOf(lastMessageDateNode.asText()));
        Chat chat = new Chat();
        chat.setId(chatId);
        chat.setName(chatName);
        chat.setLastMessageDate(date);
        return chat;
    }
}
