package service;

import controller.DispatcherServlet;
import dao.GenericDAO;
import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class Service {
    private static final Logger logger = LogManager.getLogger(String.valueOf(Service.class));
    protected static MySqlDaoFactory daoFactory = new MySqlDaoFactory();
    protected Connection connection = null;
    protected GenericDAO genericDAO;
    protected Class<? extends Entity> entityClass;

    protected Service(Class<? extends Entity> entityClass) throws PersistException {
        this.entityClass = entityClass;
        connection = daoFactory.getConnection();
        genericDAO = daoFactory.getDao(connection, entityClass);
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
    @Override
    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error closing connection", e);
                }
            }
        }
    }
}
