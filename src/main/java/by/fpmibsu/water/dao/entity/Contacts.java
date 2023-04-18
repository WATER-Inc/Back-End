package by.fpmibsu.water.dao.entity;

import by.fpmibsu.water.dao.Identified;
import by.fpmibsu.water.entity.User;

import java.util.List;

public class Contacts implements Identified<String>{
    private String id;
    private String userId;
    private List<User> users;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsers(List<User> users) {
        this.users = List.copyOf(users);
    }

    @Override
    public String getId() {
        return null;
    }
}
