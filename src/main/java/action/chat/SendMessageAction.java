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
import java.util.Date;
import java.util.TimeZone;

public class SendMessageAction extends ChatAction {

    public SendMessageAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Message message = null;
        try {
            message = ValidatorFactory.createValidator(Message.class).validate(request);
        } catch (IncorrectFormDataException ignored) {
        }
        MessageService Mservice = factory.getService(Message.class);
        ChatService Cservice = factory.getService(Chat.class);
        UserService Uservice = factory.getService(User.class);
        Chat chat = Cservice.getById(message.getChat().getId());
        User user = Uservice.getById(message.getSender().getId());
        if (chat != null && user != null) {
            message.setSender(user);
            message.setChat(chat);
            message = Mservice.persist(message);
            try {
                SenderManager.sendObject(response, message);
                map.get(chat.getId()).stream().forEach(asyncContext -> {
                    synchronized (asyncContext) {
                        asyncContext.notifyAll();
                    }
                });
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else
            try {
                SenderManager.sendObject(response, null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
    }
}
