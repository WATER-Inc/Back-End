package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class User extends Entity {
    private String id = null;
    private String username;
    @JsonIgnore
    private String passwordHash;
    private final Role role = new Role("User");

    public static enum Default {
        USER("User", "User"),
        ADMIN("Admin", "Admin");
        User user;

        Default(String username, String passwordHash) {
            user = new User();
            user.setUsername(username);
            user.setPasswordHash(passwordHash);
        }

        public User getUser() {
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(passwordHash, user.passwordHash);
    }


    public Role getRole() {
        return role;
    }

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


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
