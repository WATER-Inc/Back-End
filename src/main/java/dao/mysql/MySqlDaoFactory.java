package dao.mysql;


import dao.DAOFactory;
import dao.GenericDAO;
import dao.PersistException;
import entity.auxiliary.ChatLink;
import dao.pool.ConnectionPool;
import entity.Message;
import entity.Role;
import entity.Chat;
import entity.User;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DAOFactory<Connection> {

    private String user = "root";//Логин пользователя
    private String password = "20November3;5.-65@1234";//Пароль пользователя//TODO need to add password
    private String url = "jdbc:mysql://localhost:3306/water";//URL адрес
    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера
    private Map<Class, DAOFactory.DaoCreator> creators;
    private ConnectionPool connectionPool;

    public Connection getConnection() throws PersistException {
        return ConnectionPool.getInstance().getConnection();
    }

    @Override
    public GenericDAO getDao(Connection connection, Class dtoClass) throws PersistException {
        DAOFactory.DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public MySqlDaoFactory() {
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
}
