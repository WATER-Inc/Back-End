package by.fpmibsu.water.entity;

import java.util.List;

public abstract class Contacts {
    private List<User> users;
    public void setUsers(List<User> users){
        this.users = List.copyOf(users);
    }
}
