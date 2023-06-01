package service;

import dao.GenericDAO;
import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import entity.Entity;

import java.util.List;

public abstract class Service {
    protected static MySqlDaoFactory daoFactory = new MySqlDaoFactory();
    protected GenericDAO genericDAO;
    protected Class<? extends Entity> entityClass;

    protected Service(Class<? extends Entity> entityClass) throws PersistException {
        this.entityClass = entityClass;
        genericDAO = daoFactory.getDao(daoFactory.getConnection(), entityClass);
    }

    public Entity getById(String id) throws PersistException {
        return (Entity) genericDAO.getByPrimaryKey(id);
    }

    public List<? extends Entity> getAll() throws PersistException {
        return genericDAO.getAll();
    }

    public Entity persist(Entity object) throws PersistException {
        return (Entity) genericDAO.persist(object);
    }

    public void delete(Entity object) throws PersistException {
        genericDAO.delete(object);
    }

    public void update(Entity object) throws PersistException {
        genericDAO.update(object);
    }


}
