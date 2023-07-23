package action.chat;

import action.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Message;
import entity.Role;
import entity.User;
import service.ChatService;
import service.MessageService;
import service.RoleService;
import service.UserService;
import validator.IncorrectFormDataException;
import validator.Validator;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserToChatAction extends ChatAction {

    public AddUserToChatAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Chat chat = null;
        User user = null;
        Role role = null;
        try {
            chat = ValidatorFactory.createValidator(Chat.class).validate(request);
            user = ValidatorFactory.createValidator(User.class).validate(request);
            role = ValidatorFactory.createValidator(Role.class).validate(request);
        } catch (IncorrectFormDataException ignored) {
        }
        ChatService Cservice = factory.getService(Chat.class);
        UserService Uservice = factory.getService(User.class);
        RoleService Rservice = factory.getService(Role.class);
        chat = Cservice.getById(chat.getId());
        user = Uservice.getByUsername(user.getUsername());
        if (chat != null && user != null) {
            Cservice.addUser(chat, user, role);
            try {
                SenderManager.sendObject(response, role);
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
