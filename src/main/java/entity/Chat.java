package entity;

import dao.Identified;
import dao.entity.Participants;

public class Chat extends Entity {
    private String id;
    private String name;
    private Participants participants;

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
