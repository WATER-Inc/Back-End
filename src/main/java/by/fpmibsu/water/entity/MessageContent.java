package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

public class MessageContent implements Identified<String> {
    private String id;
    private String content;

    @Override
    public String getId() {
        return id;
    }
}
