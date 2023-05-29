package action.chat;

import action.Action;
import action.AuthorizedUserAction;
import entity.Role;

abstract public class ChatAction extends AuthorizedUserAction {
    public ChatAction() {
        getAllowRoles().add(new Role("User"));
    }
}