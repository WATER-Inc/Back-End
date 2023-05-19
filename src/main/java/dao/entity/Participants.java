package dao.entity;

import entity.Role;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class Participants{
    private List<User> users;
    private List<Role> roles;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public void addUser(User user, Role role){
        users.add(user);
        roles.add(role);
    }

    public Participants() {
        this.users = new ArrayList<>();
        this.roles = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Participants{" +
                "users=" + users +
                ", roles=" + roles +
                '}';
    }
}
