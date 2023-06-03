package service;

import controller.DispatcherServlet;
import dao.GenericDAO;
import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import dao.pool.ConnectionPool;
import dao.pool.PooledConnection;
import entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class Service {
    private static final Logger logger = LogManager.getLogger(String.valueOf(Service.class));

    public void setDaoFactory(MySqlDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    protected MySqlDaoFactory daoFactory;
    protected PooledConnection connection = null;
    protected GenericDAO genericDAO;
    protected Class<? extends Entity> entityClass;

    protected Service(Class<? extends Entity> entityClass, MySqlDaoFactory factory) throws PersistException {
        this.daoFactory = factory;
        this.entityClass = entityClass;
        connection = daoFactory.getConnection();
        genericDAO = daoFactory.getDao(entityClass);
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
