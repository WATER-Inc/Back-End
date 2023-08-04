package action.chat;

import action.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Role;
import entity.User;
import entity.auxiliary.PreChatLink;
import service.ChatService;
import service.RoleService;
import service.UserService;
import validator.IncorrectFormDataException;
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
        PreChatLink preChatLink = null;
        try {
            preChatLink = ValidatorFactory.createValidator(PreChatLink.class).validate(request);
        } catch (IncorrectFormDataException ignored) {
        }
        if (preChatLink != null) {
            ChatService Cservice = factory.getService(Chat.class);
            UserService Uservice = factory.getService(User.class);
            RoleService Rservice = factory.getService(Role.class);

            Chat chat = Cservice.getById(preChatLink.getChatId());
            User user = Uservice.getByUsername(preChatLink.getUsername());
            //User invitor = Uservice.getByUsername(preChatLink.getInviterName());//TODO add check if invitor cat add user t chat
            Role role = Rservice.getByTitle(preChatLink.getRoleName());
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
}
