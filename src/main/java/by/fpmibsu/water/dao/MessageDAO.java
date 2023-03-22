package by.fpmibsu.water.dao;

import by.fpmibsu.water.entity.Message;

import java.util.List;
import java.util.Optional;

public class MessageDAO implements GenericDAO<Message> {
    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public Optional<Message> get(String id) {
        return Optional.empty();
    }

    @Override
    public Boolean save(Message message) {
        return null;
    }

    @Override
    public Boolean update(Message message) {
        return null;
    }

    @Override
    public Boolean delete(Message message) {
        return null;
    }
}
