package action;


import dao.PersistException;
import entity.Role;
import entity.User;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract public class Action {
    private Set<Role> allowRoles = new HashSet<>();
    private User authorizedUser;
    private String name;

    protected static ServiceFactory factory = new ServiceFactory();

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

    abstract public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException;

    public static class Forward {
        private String forward;
        private boolean redirect;
        private Map<String, Object> attributes = new HashMap<>();

        public Forward(String forward, boolean redirect) {
            this.forward = forward;
            this.redirect = redirect;
        }

        public Forward(String forward) {
            this(forward, true);
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public boolean isRedirect() {
            return redirect;
        }

        public void setRedirect(boolean redirect) {
            this.redirect = redirect;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }
    }

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

