package action.chats;


import action.Action;
import action.AuthorizedUserAction;
import dao.PersistException;
import entity.Role;

abstract public class ChatsAction extends AuthorizedUserAction {
    public ChatsAction() throws PersistException {
        super();
        getAllowRoles().add(new Role("User"));
    }
}