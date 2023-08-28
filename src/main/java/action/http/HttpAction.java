package action.http;


import action.AbstractAction;
import dao.PersistException;
import entity.Role;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

abstract public class HttpAction extends AbstractAction<HttpServletRequest, HttpServletResponse> {
    private final Set<Role> allowRoles = new HashSet<>(List.of(new Role[]{new Role("User")}));
    private User authorizedUser;


    public Set<Role> getAllowRoles() {
        return allowRoles;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser) {
        this.authorizedUser = authorizedUser;
    }



    @Override
    abstract public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException;

    @Override
    public String toString() {
        return "Action{" +
                "allowRoles=" + allowRoles +
                ", authorizedUser=" + authorizedUser +
                ", name='" + getName() + '\'' +
                ", factory=" + getFactory() +
                '}';
    }
}

