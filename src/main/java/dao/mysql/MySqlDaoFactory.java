package dao.mysql;


import dao.DAOFactory;
import dao.GenericDAO;
import dao.PersistException;
import dao.pool.PooledConnection;
import entity.auxiliary.ChatLink;
import dao.pool.ConnectionPool;
import entity.Message;
import entity.Role;
import entity.Chat;
import entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DAOFactory<Connection> {

    private Map<Class, DAOFactory.DaoCreator> creators;

    public PooledConnection getConnection() {
        return connection;
    }

    private PooledConnection connection;
    @Override
    public GenericDAO getDao(Class dtoClass) throws PersistException {
        DAOFactory.DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public MySqlDaoFactory() throws PersistException {
        connection = ConnectionPool.getInstance().getConnection();
        creators = new HashMap<Class, DAOFactory.DaoCreator>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySqlUserDAO(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Chat.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySqlChatDAO(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(ChatLink.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySqlChatLinkDAO(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Role.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySqlRoleDAO(MySqlDaoFactory.this, connection);
            }
        });
        creators.put(Message.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySqlMessageDAO(MySqlDaoFactory.this, connection);
            }
        });
    }
    public void close() throws SQLException {
        connection.close();
    }
}
