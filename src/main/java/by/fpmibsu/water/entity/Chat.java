package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

import java.util.List;

public class Chat implements Identified<String> {
    private String Id;
    private String name;
    private List<User> users;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
