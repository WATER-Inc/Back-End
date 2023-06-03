package action;


import dao.PersistException;
import entity.Role;
import entity.User;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

abstract public class Action {
    private Set<Role> allowRoles = new HashSet<>(List.of(new Role[]{new Role("User")}));
    private User authorizedUser;
    private String name;

    protected static ServiceFactory factory;

    public Set<Role> getAllowRoles() {
        return allowRoles;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFactory(ServiceFactory factory) {
        this.factory = factory;
    }

    abstract public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException;

    @Override
    public String toString() {
        return "Action{" +
                "allowRoles=" + allowRoles +
                ", authorizedUser=" + authorizedUser +
                ", name='" + name + '\'' +
                ", factory=" + factory +
                '}';
    }
}

