package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

import java.util.List;

public class User implements Identified<String> {
    private String id;
    private String username;
    private String passwordHash;
    private List<User> contacts;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<User> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
