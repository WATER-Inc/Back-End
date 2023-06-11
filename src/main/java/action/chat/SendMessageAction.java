package action.chat;

import action.parser.Parser;
import action.sender.SenderManager;
import com.fasterxml.jackson.databind.JsonNode;
import dao.PersistException;
import entity.Chat;
import entity.Message;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ChatService;
import service.MessageService;
import service.UserService;
import validator.IncorrectFormDataException;
import validator.Validator;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class SendMessageAction extends ChatAction {

    public SendMessageAction() throws PersistException {
        super();
    }

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
        JsonNode userIdNode = null;
        JsonNode chatIdNode = null;
        JsonNode messageNode = null;

        if (jsonNode == null) {
            try {
                SenderManager.sendObject(response, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        chatIdNode = jsonNode.get("chatId");
        userIdNode = jsonNode.get("userId");
        messageNode = jsonNode.get("message");
        if (chatIdNode == null || userIdNode == null || messageNode == null) {
            try {
                SenderManager.sendObject(response, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String messageText = messageNode.asText();
        String chatId = chatIdNode.asText();
        String userId = userIdNode.asText();
        logger.debug(userId + " " + chatId + " " + messageText);
        if (messageText != null && chatId != null && userId != null) {
            MessageService Mservice = factory.getService(Message.class);
            ChatService Cservice = factory.getService(Chat.class);
            UserService Uservice = factory.getService(User.class);
            Chat chat = Cservice.getById(chatId);
            User user = Uservice.getById(userId);
            if(chat != null && user != null){
                Message message = new Message();
                message.setContent(messageNode.asText());
                message.setSender(user);
                message.setChat(chat);
                message.setDate(new Date(2023,11,20));
                message = Mservice.persist(message);
                logger.debug(message);
                try {
                    SenderManager.sendObject(response, message);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                SenderManager.sendObject(response, null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return;
    }
}
