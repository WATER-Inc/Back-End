package action.http.chats;

import action.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Role;
import entity.auxiliary.Participants;
import validator.IncorrectFormDataException;
import validator.ValidatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserCreateChat extends ChatsHttpAction {

    public UserCreateChat() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Chat chat = null;
        try {
            chat = ValidatorFactory.createValidator(Chat.class).validate(request);
        } catch (IncorrectFormDataException ignored) {}
        if (chat != null) {
            logger.debug("Trying to create chat: [ChatName: " + chat.getName() + "]");
            Participants participants = new Participants();
            Role role = new Role();
            role.setTitle("Owner");//TODO create enum type OWNER
            role.setId("4");
            participants.addUser(getAuthorizedUser(), role);
            chat.setParticipants(participants);
            logger.debug("In Chat: " + chat);
            chat = (Chat) getFactory().getService(Chat.class).persist(chat);
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
