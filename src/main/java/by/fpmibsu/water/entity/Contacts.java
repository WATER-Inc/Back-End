package by.fpmibsu.water.entity;

import java.util.List;

public class Contacts {
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    private Integer userId;
    private List<User> users;
    public void setUsers(List<User> users){
        this.users = List.copyOf(users);
    }
}
