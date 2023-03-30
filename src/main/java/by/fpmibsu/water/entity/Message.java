package by.fpmibsu.water.entity;

import java.util.Date;

public abstract class Message {
    private String messageId;
    private User sender;
    private Chat chat;
    private MessageContent content;
    private Date messagePostDate;
}
