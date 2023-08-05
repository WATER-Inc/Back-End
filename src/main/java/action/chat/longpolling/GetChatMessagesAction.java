package action.chat.longpolling;

import action.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Message;
import service.ChatService;
import service.MessageService;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GetChatMessagesAction extends ChatLongPollingAction {
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
        asyncContext.setTimeout(timeOut);//TODO
        Date date = chat.getLastMessageDate();
        chat = Cservice.getById(chat.getId());
        addContext(chat, asyncContext);
        if (chat == null) {
            request.setAttribute("message", "Чата не существует");
            logger.info(String.format("unsuccessfully tried to get chat %s %s (%s:%s)", chat, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        } else {
            synchronized (asyncContext) {
                try {
                    if(chat.getLastMessageDate() == null || date.compareTo(chat.getLastMessageDate()) >= 0)
                        asyncContext.wait(timeOut);
                    logger.debug("Context Waked up!!!");
                    List<Message> messageList = null;
                    messageList = Mservice.getMessages(chat, date);
                    logger.info(String.format("chat \"%s\" is sent ", chat));
                    SenderManager.sendObject(response, messageList);
                } catch (Exception e) {
                    logger.error("Error while getting chat messages", e);
                }
                asyncContext.complete();
            }
        }
        removeContext(chat, asyncContext);
    }
}

