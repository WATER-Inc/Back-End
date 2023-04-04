package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

import java.util.Date;

public abstract class Message implements Identified<Integer> {
    private Integer id;
    private User sender;
    private Chat chat;
    private MessageContent content;
    private Date messagePostDate;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    public Date getMessagePostDate() {
        return messagePostDate;
    }

    public void setMessagePostDate(Date messagePostDate) {
        this.messagePostDate = messagePostDate;
    }
}
