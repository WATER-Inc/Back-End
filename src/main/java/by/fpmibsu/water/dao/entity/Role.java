package by.fpmibsu.water.dao.entity;

import by.fpmibsu.water.dao.Identified;

public class Role implements Identified<String> {
    private String id;
    private Integer userId;
    private String title;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
