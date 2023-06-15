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
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GetChatMessagesAction extends ChatAction {
    private static Logger logger = LogManager.getLogger(SendMessageAction.class);

    public GetChatMessagesAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Chat chat = null;
        try {
            chat = Objects.requireNonNull(ValidatorFactory.createValidator(Chat.class)).validate(request);
        } catch (IncorrectFormDataException ignored) {
        }

        MessageService Mservice = factory.getService(Message.class);
        ChatService Cservice = factory.getService(Chat.class);
        List<Message> messageList = null;
        if (chat == null) {
            request.setAttribute("message", "Чата не существует");
            logger.info(String.format("unsuccessfully tried to get chat %s %s (%s:%s)", chat, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        } else {
            Date date = chat.getLastMessageDate();
            chat = Cservice.getById(chat.getId());
            messageList = Mservice.getMessages(chat, date);
        }
        try {
            SenderManager.sendObject(response, messageList);
            logger.info(String.format("chat \"%s\" is sent ", chat));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

