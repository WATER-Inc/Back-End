package action.chat;

import action.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.User;
import org.testng.internal.collections.Pair;
import service.UserService;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserToChat extends ChatAction {
    public AddUserToChat() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Pair<Chat, User> input = null;
        try {
            input = (Pair<Chat, User>) ValidatorFactory.createValidator(AddUserToChat.class).validate(request);
        } catch (IncorrectFormDataException ignored) {
        }
        if (user == null) {
            logger.debug("Request had no content");
            return;
        }
        User copy = user;
        logger.debug("Trying to add user: [Username: " + user.getUsername() + "] To chat: [ChatName: " + );
        UserService userService = factory.getService(User.class);
        user = userService.getByUsername(user.getUsername());
        if (user == null)
            logger.debug("User: " + copy.getUsername() + "does not exist.");
        try {
            SenderManager.sendObject(response, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
