package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

public class Role implements Identified<String> {
    private String id = null;
    private String title;

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
