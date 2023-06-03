package action.chat;

import action.Action;
import action.AuthorizedUserAction;
import dao.PersistException;
import entity.Role;

abstract public class ChatAction extends AuthorizedUserAction {
    public ChatAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}