package dao;

import entity.Identified;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Identified<PK>, PK extends Serializable> {

    /**
     * Создает соответствующий ей объект
     */
    public T create() throws PersistException;

    /**
     * Создает новую запись, соответствующую объекту object
     */
    public T persist(T object) throws PersistException;

    /**
     * Возвращает объект соответствующий записи с первичным ключом key или null
     */
    public T getByPrimaryKey(PK key) throws PersistException;

    /**
     * Сохраняет состояние объекта group в базе данных
     */
    public void update(T object) throws PersistException;

    /**
     * Удаляет запись об объекте из базы данных
     */
    public void delete(T object) throws PersistException;

    /**
     * Возвращает список объектов соответствующих всем записям в базе данных
     */
    public List<T> getAll() throws PersistException;

}
