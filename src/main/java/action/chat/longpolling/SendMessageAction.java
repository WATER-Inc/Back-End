package action.chat.longpolling;

import action.chat.ChatAction;
import action.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Message;
import entity.User;
import service.ChatService;
import service.MessageService;
import service.UserService;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendMessageAction extends ChatLongPollingAction {

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
        if(message != null) {
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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                notifyAllContexts(chat);
            } else
                try {
                    SenderManager.sendObject(response, null);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
        }
    }
}
