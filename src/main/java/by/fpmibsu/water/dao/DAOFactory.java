package by.fpmibsu.water.dao;

import by.fpmibsu.water.dao.GenericDAO;

import java.sql.Connection;

public interface DAOFactory<Context> {

    public interface DaoCreator<Context> {
        public GenericDAO create(Context context);
    }

    /** Возвращает подключение к базе данных */
    public Context getContext() throws PersistException;

    /** Возвращает объект для управления персистентным состоянием объекта */
    public GenericDAO getDao(Context context, Class dtoClass) throws PersistException;
}