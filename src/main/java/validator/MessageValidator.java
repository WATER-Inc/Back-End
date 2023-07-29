package validator;

import action.parser.Parser;
import action.sender.SenderManager;
import com.fasterxml.jackson.databind.JsonNode;
import entity.Chat;
import entity.Message;
import entity.User;
import service.ChatService;
import service.MessageService;
import service.UserService;

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
        JsonNode userIdNode = null;
        JsonNode chatIdNode = null;
        JsonNode messageNode = null;
        JsonNode dateNode = null;
        if (jsonNode == null) {
            return null;
        }
        chatIdNode = jsonNode.get("chatId");
        userIdNode = jsonNode.get("userId");
        messageNode = jsonNode.get("message");
        dateNode = jsonNode.get("date");
        if (chatIdNode == null || userIdNode == null || messageNode == null) {//TODO add date to this request
            return null;
        }
        Message message = new Message();
        User user = new User();
        user.setId(userIdNode.asText());
        message.setSender(user);
        Chat chat = new Chat();
        chat.setId(chatIdNode.asText());
        message.setChat(chat);
        message.setContent(messageNode.asText());
        if (dateNode != null)
            message.setDate(new Date(dateNode.asText()));
        else message.setDate(new Date());
        return message;
    }
}
