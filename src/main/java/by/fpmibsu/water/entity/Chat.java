package by.fpmibsu.water.entity;

import by.fpmibsu.water.dao.Identified;
import by.fpmibsu.water.dao.entity.Participants;

import java.util.List;

public class Chat implements Identified<String> {
    private String Id;
    private String name;
    private Participants participants;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setParticipants(Participants participants) {
        this.participants = participants;
    }

    public Participants getParticipants() {
        return participants;
    }
}
