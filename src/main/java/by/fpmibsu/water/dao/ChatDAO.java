package by.fpmibsu.water.dao;

import by.fpmibsu.water.entity.Chat;

import java.util.List;
import java.util.Optional;

public class ChatDAO implements GenericDAO<Chat> {
    @Override
    public List<Chat> getAll() {
        return null;
    }

    @Override
    public Optional<Chat> get(String id) {
        return Optional.empty();
    }

    @Override
    public Boolean save(Chat chat) {
        return null;
    }

    @Override
    public Boolean update(Chat chat) {
        return null;
    }

    @Override
    public Boolean delete(Identified chat) {
        return null;
    }
}
