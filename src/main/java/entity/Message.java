package entity;

import dao.Identified;

<<<<<<< Updated upstream
import java.sql.Date;
=======
import java.util.Date;
import java.util.Objects;
>>>>>>> Stashed changes

public class Message extends Entity {
    private String id;
    private User sender;
    private Chat chat;
    private String content;
    private Date date;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", sender=" + sender +
                ", chat=" + chat +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
