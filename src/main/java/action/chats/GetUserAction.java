package action.chats;

import action.sender.SenderManager;
import dao.PersistException;
import entity.User;
import service.UserService;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GetUserAction extends ChatsAction {

    public GetUserAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        User user = null;
        try {
            user = ValidatorFactory.createValidator(User.class).validate(request);
        } catch (IncorrectFormDataException ignored) {
        }
        if (user == null) {
            logger.debug("Request had no content");
            return;
        }
        logger.debug("Trying to login with: [Username: " + user.getUsername() + "; Password: " + user.getPasswordHash() + "]");
        UserService service = factory.getService(User.class);
        User copy = user;
        user = service.getByUsername(user.getUsername());
        if (user == null) {
            request.setAttribute("message", "Имя пользователя не опознанно");
            logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", copy.getUsername(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        }
        try {
            SenderManager.sendObject(response, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
