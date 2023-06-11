package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.auxiliary.Participants;

import java.util.Date;
import java.util.Objects;

public class Chat extends Entity {
    private String id;
    private String name;
    private Date lastMessageDate;

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    @JsonIgnore
    private Participants participants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) && Objects.equals(name, chat.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParticipants(Participants participants) {
        this.participants = participants;
    }

    public Participants getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", participants=" + participants +
                '}';
    }
}
