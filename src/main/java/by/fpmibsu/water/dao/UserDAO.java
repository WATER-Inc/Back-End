package by.fpmibsu.water.dao;

import by.fpmibsu.water.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDAO implements GenericDAO<User>{
    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.empty();
    }

    @Override
    public Boolean save(User user) {
        return null;
    }

    @Override
    public Boolean update(User user) {
        return null;
    }

    @Override
    public Boolean delete(User user) {
        return null;
    }
}
