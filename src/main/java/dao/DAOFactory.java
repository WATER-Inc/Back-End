package dao;

public interface DAOFactory<Connection> {

    public interface DaoCreator<Connection> {
        public GenericDAO create(Connection context);
    }

    /** Возвращает объект для управления персистентным состоянием объекта */
    public GenericDAO getDao(Class dtoClass) throws PersistException;
}