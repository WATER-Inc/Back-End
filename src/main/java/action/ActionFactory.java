package action;

import action.http.ErrorHttpAction;
import action.http.authentication.LoginHttpAction;
import action.http.authentication.LogoutHttpAction;
import action.http.authentication.RegistrationHttpAction;
import action.http.chat.AddUserToChatHttpAction;
import action.http.chat.longpolling.GetChatMessagesHttpAction;
import action.http.chat.longpolling.SendMessageHttpAction;
import action.http.chats.UserCreateChat;
import action.http.chats.UserNeedChatsHttpAction;
import action.http.common.FindUserByLoginHttpAction;
import action.websocket.MainWsAction;
import action.websocket.chat.message.remove.RemoveMessageWsAction;
import action.websocket.chat.message.send.SendMessageWsAction;
import action.websocket.chat.message.update.UpdateMessageWsAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFactory {
    private static final Map<String, Class<? extends AbstractAction>> actions = new ConcurrentHashMap<>();
    static {
        actions.put("/water/", LoginHttpAction.class);
        actions.put("/water/chat", GetChatMessagesHttpAction.class);
        actions.put("/water/chat/send", SendMessageWsAction.class);
        actions.put("/water/chat/remove", RemoveMessageWsAction.class);
        actions.put("/water/chat/update", UpdateMessageWsAction.class);
        actions.put("/water/chat/add/user", AddUserToChatHttpAction.class);
        actions.put("/water/chats", UserNeedChatsHttpAction.class);
        actions.put("/water/chats/create", UserCreateChat.class);
        actions.put("/water/common/find/user", FindUserByLoginHttpAction.class);
        actions.put("/water/login", LoginHttpAction.class);
        actions.put("/water/logout", LogoutHttpAction.class);
        actions.put("/water/message", SendMessageHttpAction.class);
        actions.put("/water/register", RegistrationHttpAction.class);
        actions.put("errorAction", ErrorHttpAction.class);
        actions.put("websocket", MainWsAction.class);
    }
    public static Class<? extends AbstractAction> getAction(String uri){
                return actions.get(uri);
    }
}
