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
    private Lock lock = new ReentrantLock();

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
        AsyncContext asyncContext = request.startAsync(request,response);
        asyncContext.setTimeout(0);//TODO
        ChatAction.map.get(chat.getId()).add(asyncContext);
        Date date = chat.getLastMessageDate();
        chat = Cservice.getById(chat.getId());
        if (chat == null) {
            request.setAttribute("message", "Чата не существует");
            logger.info(String.format("unsuccessfully tried to get chat %s %s (%s:%s)", chat, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        } else {
            synchronized (asyncContext) {
                try {
                    //wait(10000);
                    asyncContext.wait(1000);
                    logger.debug("Start wait");
                    List<Message> messageList = null;
                    while (messageList == null || messageList.isEmpty()) {
                        messageList = Mservice.getMessages(chat, date);
                        if (!messageList.isEmpty()) {
                            logger.info(String.format("chat \"%s\" is sent ", chat));
                            logger.info("Sending" + messageList.toString());
                            SenderManager.sendObject(response, messageList);
                            asyncContext.complete();
                            break;
                        } else {
                            asyncContext.wait(1000);//TODO update time
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error while getting chat messages", e);
                }
            }
        }
    }
}

