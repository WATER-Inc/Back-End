package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

public class User implements Identified<Integer> {
    private Integer id;
    private String username;
    private String passwordHash;
    private Contacts contacts;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public Contacts getContacts(){
        return contacts;
    }

}
