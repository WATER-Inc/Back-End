package by.fpmibsu.water.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO <T>{
    List<T> getAll();
    Optional<T> get(String id);
    Boolean save(T t);
    Boolean update(T t);
    Boolean delete(T t);
}
