package action.chat;

import send_validate.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Role;
import entity.User;
import org.testng.internal.collections.Pair;
import service.ChatService;
import service.RoleService;
import service.UserService;
import send_validate.validator.IncorrectFormDataException;
import send_validate.validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserToChatAction extends ChatAction {

    public AddUserToChatAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        logger.debug(getAuthorizedUser() + " try to add user to chat");
        Chat chat = null;
        User user = null;
        Role role = null;
        try {
            Pair<Chat, Pair<User, Role>> res = new Pair<>(new Chat(), new Pair<>(new User(), new Role()));
            res = (Pair<Chat, Pair<User, Role>>) ValidatorFactory.createValidator(res.getClass()).validate(request);
            chat = res.first();
            user = res.second().first();
            role = res.second().second();
        } catch (IncorrectFormDataException ignored) {
        }
        logger.debug("Add action: " + chat.toString() + user.toString() + role.toString());
        ChatService Cservice = factory.getService(Chat.class);
        UserService Uservice = factory.getService(User.class);
        RoleService Rservice = factory.getService(Role.class);
        chat = Cservice.getById(chat.getId());
        user = Uservice.getByUsername(user.getUsername());
        role = Rservice.getByTitle(role.getTitle());
        logger.debug("Add action: " + chat.toString() + user.toString() + role.toString());
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
