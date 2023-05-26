package entity;

import java.util.Objects;

public class Role extends Entity {
    private String id = null;
    private String title;

    public Role() {
    }

    public Role(String title) {
        super();
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(title, role.title);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
