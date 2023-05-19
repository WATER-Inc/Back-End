package dao;

public interface DAOFactory<Connection> {

    public interface DaoCreator<Connection> {
        public GenericDAO create(Connection context);
    }
    /** Возвращает подключение к базе данных */
    public Connection getConnection() throws PersistException;

    /** Возвращает объект для управления персистентным состоянием объекта */
    public GenericDAO getDao(Connection connection, Class dtoClass) throws PersistException;
}