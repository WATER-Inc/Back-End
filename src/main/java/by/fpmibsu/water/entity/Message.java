package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

import java.util.Date;

public abstract class Message implements Identified<Integer> {
    private String messageId;
    private User sender;
    private Chat chat;
    private MessageContent content;
    private Date messagePostDate;

    @Override
    public Integer getId() {
        return null;
    }
}
