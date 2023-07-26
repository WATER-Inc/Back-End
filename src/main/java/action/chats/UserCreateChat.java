package action.chats;

import send_validate.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Role;
import entity.User;
import entity.auxiliary.Participants;
import service.UserService;
import send_validate.validator.IncorrectFormDataException;
import send_validate.validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserCreateChat extends ChatsAction {

    public UserCreateChat() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Chat chat = null;
        try {
            chat = (Chat) ValidatorFactory.createValidator(Chat.class).validate(request);
        } catch (IncorrectFormDataException ignored) {}
        logger.debug("Trying to create chat: [ChatName: " + chat.getName() + "]");
        if (chat != null) {
            UserService service = factory.getService(User.class);
            Participants participants = new Participants();
            Role role = new Role();
            role.setTitle("Owner");//TODO create enum type OWNER
            role.setId("4");
            participants.addUser(getAuthorizedUser(), role);
            chat.setParticipants(participants);
            logger.debug("In Chat: " + chat);
            chat = (Chat) factory.getService(Chat.class).persist(chat);
            try {
                SenderManager.sendObject(response, chat);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (chat == null) {
                request.setAttribute("message", "Чат уже существует");
                logger.info(String.format("user \"%s\" unsuccessfully tried to create chat (%s) in form %s (%s:%s)", getAuthorizedUser().getUsername(), chat.getName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return;
            }
            HttpSession session = request.getSession();
            logger.info(session.getAttribute("authorizedUser"));
            logger.info(String.format("user \"%s\" created chat (%s) in from %s (%s:%s)", getAuthorizedUser().getUsername(), chat.getName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
        }
    }
}
