package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;
import jdk.internal.net.http.common.Pair;

import java.util.List;

public class Chat implements Identified<Integer> {
    private Integer Id;
    private String name;
    private List<Pair<User, String>> users; //TODO second element in the pair is the role of the user of this pair

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public List<Pair<User, String>> getUsers() {
        return users;
    }

    public void setUsers(List<Pair<User, String>> users) {
        this.users = users;
    }
}
