package action.chat;

import action.parser.Parser;
import action.sender.SenderManager;
import com.fasterxml.jackson.databind.JsonNode;
import dao.PersistException;
import entity.Chat;
import entity.Message;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import service.ChatService;
import service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetChatMessagesAction extends ChatAction{
    private static Logger logger = LogManager.getLogger(SendMessageAction.class);

    public GetChatMessagesAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        JsonNode jsonNode =null;
        try {
            jsonNode = Parser.parseRequest(request);
        } catch (IOException e) {
            logger.debug("Request had no content");
            return;
            // todo process request with no body
        }
        JsonNode chatIdNode = null;
        if (jsonNode == null) {
            try {
                SenderManager.sendObject(response, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        chatIdNode = jsonNode.get("chatId");
        if(chatIdNode == null){
            try {
                SenderManager.sendObject(response, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String chatId = chatIdNode.asText();
        if(chatId != null){
            MessageService Mservice = factory.getService(Message.class);
            ChatService Cservice = factory.getService(Chat.class);
            Chat chat = Cservice.getById(chatId);
            List<Message> messageList = null;
            if(chat == null){
                request.setAttribute("message", "Чата не существует");
                logger.info(String.format("unsuccessfully tried to get chat %s %s (%s:%s)", chatId, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            }else messageList = Mservice.getMessages(chat);
            try {
                SenderManager.sendObject(response, messageList);
                logger.info(String.format("chat \"%s\" is sent ", chatId));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

