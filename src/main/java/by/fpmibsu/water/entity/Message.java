package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;

import java.util.Date;

public abstract class Message implements Identified<String> {
    private String id;
    private User sender;
    private Chat chat;
    private MessageContent content;
    @Override
    public String getId() {
        return id;
    }
    public void setId(String id) {
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
}
