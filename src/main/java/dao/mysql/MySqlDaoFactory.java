package dao.mysql;


import dao.DAOFactory;
import dao.GenericDAO;
import dao.PersistException;
import dao.entity.ChatLink;
import entity.Message;
import entity.Role;
import entity.Chat;
import entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DAOFactory<Connection> {

    private String user = "root";//Логин пользователя
    private String password = "20November3;5.-65@1234";//Пароль пользователя//TODO need to add password
    private String url = "jdbc:mysql://localhost:3306/water";//URL адрес
    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера
    private Map<Class, DAOFactory.DaoCreator> creators;

    public Connection getConnection() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
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
//        try {
//            Class.forName(driver);//Регистрируем драйвер
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

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
