package action.chats;

import action.sender.SenderManager;
import dao.PersistException;
import entity.Chat;
import entity.Role;
import entity.User;
import entity.auxiliary.Participants;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserCreateChat extends ChatsAction {
    private static Logger logger = LogManager.getLogger(String.valueOf(UserCreateChat.class));

    public UserCreateChat() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        String chatName = request.getParameter("chatName");
        logger.debug("Trying to create chat: [ChatName: " + chatName + "]");
        if (chatName != null) {
            UserService service = factory.getService(User.class);
            Chat chat = new Chat();
            chat.setName(chatName);
            Participants participants = new Participants();
            Role role = new Role();
            role.setTitle("owner");
            participants.addUser(getAuthorizedUser(), role);
            chat.setParticipants(participants);
            chat = (Chat) factory.getService(Chat.class).persist(chat);
            try {
                SenderManager.sendObject(response, chat);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (chat == null) {
                request.setAttribute("message", "Чат уже существует");
                logger.info(String.format("user \"%s\" unsuccessfully tried to create chat (%s) in form %s (%s:%s)", getAuthorizedUser().getUsername(), chatName, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return;
            }
            HttpSession session = request.getSession();
            logger.info(session.getAttribute("authorizedUser"));
            logger.info(String.format("user \"%s\" created chat (%s) in from %s (%s:%s)", getAuthorizedUser().getUsername(), chatName, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            return;
        }
        return;
    }
}
