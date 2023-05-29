package action.chats;

import action.Action;
import action.sender.Sender;
import dao.PersistException;
import entity.Chat;
import service.ChatService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class UserNeedChatsAction extends ChatsAction {
    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Forward forward = new Forward("/chats/chats.jsp", false);
        List<Chat> chats;
        ChatService service = factory.getService(Chat.class);
        chats = service.getByUser(getAuthorizedUser());
        try {
            Sender.sendObject(response, chats);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return forward;
    }
}