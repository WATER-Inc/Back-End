package action.chat;

import action.parser.Parser;
import action.sender.SenderManager;
import com.fasterxml.jackson.databind.JsonNode;
import dao.PersistException;
import entity.Chat;
import entity.Message;
import io.swagger.models.auth.In;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import scala.Int;
import service.ChatService;
import service.MessageService;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GetChatMessagesAction extends ChatAction {
    private Integer timeOut = 30000;
    private static final Integer timeStep = 100;

    public GetChatMessagesAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Chat chat = null;
        try {
            chat = Objects.requireNonNull(ValidatorFactory.createValidator(Chat.class)).validate(request);
            if(chat == null)
                throw new IncorrectFormDataException("No one chat in request", "Error");
        } catch (IncorrectFormDataException e) {
            throw new PersistException(e);
        }
        MessageService Mservice = factory.getService(Message.class);
        ChatService Cservice = factory.getService(Chat.class);
        AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(0);//TODO
        Date date = chat.getLastMessageDate();
        chat = Cservice.getById(chat.getId());
        if (chat == null) {
            request.setAttribute("message", "Чата не существует");
            logger.info(String.format("unsuccessfully tried to get chat %s %s (%s:%s)", chat, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        } else {
            synchronized (asyncContext) {
                try {
                    List<Message> messageList = null;
                    while (timeOut >= 0 && (chat.getLastMessageDate() == null || date.compareTo(chat.getLastMessageDate()) >= 0)) {
                        chat = Cservice.getById(chat.getId());
                        //logger.debug("Wait User: " + getAuthorizedUser() + "Last Date: " + chat.getLastMessageDate() + asyncContext.getTimeout());
                        timeOut -= timeStep;
                        asyncContext.wait(timeStep);//TODO update time
                    }
                    messageList = Mservice.getMessages(chat, date);
                    logger.info(String.format("chat \"%s\" is sent ", chat));
                    SenderManager.sendObject(response, messageList);
                } catch (Exception e) {
                    logger.error("Error while getting chat messages", e);
                }
                asyncContext.complete();
            }
        }
    }
}

