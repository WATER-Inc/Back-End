package action.chats;


import action.Action;
import entity.Role;

abstract public class ChatsAction extends Action {
    public ChatsAction() {
        getAllowRoles().add(new Role("User"));
    }
}