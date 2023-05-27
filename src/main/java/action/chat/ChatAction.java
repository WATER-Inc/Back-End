package action.chat;

import action.Action;
import entity.Role;

abstract public class ChatAction extends Action {
    public ChatAction() {
        getAllowRoles().add(new Role("User"));
    }
}