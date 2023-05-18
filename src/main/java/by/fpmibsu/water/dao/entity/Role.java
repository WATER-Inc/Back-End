package by.fpmibsu.water.dao.entity;

import by.fpmibsu.water.dao.Identified;

public class Role implements Identified<String> {
    private String id;
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
}
