package action.chats;

import action.Action;
import dao.PersistException;
import entity.Chat;
import service.ChatService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class UserNeedChatsAction extends ChatsAction {
    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        Forward forward = new Forward("/chats/chats.jsp", false);
        List<Chat> chats;
        ChatService service = factory.getService(Chat.class);
        chats = service.getByUser(getAuthorizedUser());
        // TODO send chats to response in JSON
        //TODO need to sort chats order by last message date;
        return forward;
    }
}