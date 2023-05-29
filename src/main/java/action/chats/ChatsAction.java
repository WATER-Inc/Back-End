package action.chats;


import action.Action;
import action.AuthorizedUserAction;
import entity.Role;

abstract public class ChatsAction extends AuthorizedUserAction {
    public ChatsAction() {
        getAllowRoles().add(new Role("User"));
    }
}