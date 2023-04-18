package by.fpmibsu.water.dao.entity;

import by.fpmibsu.water.dao.Identified;
import by.fpmibsu.water.entity.User;

import java.util.List;

public class Participants implements Identified<String>{
    private String id;
    private String chatId;
    private List<String> usersId;
    private List<String> rolesId;

    @Override
    public String getId() {
        return null;
    }

}
