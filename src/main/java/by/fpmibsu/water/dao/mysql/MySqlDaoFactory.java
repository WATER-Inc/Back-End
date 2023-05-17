package by.fpmibsu.water.dao.mysql;


import by.fpmibsu.water.dao.*;
import by.fpmibsu.water.dao.entity.Role;
import by.fpmibsu.water.entity.Chat;
import by.fpmibsu.water.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DAOFactory<Connection> {

    private String user = "root";//Логин пользователя
    private String password = "waterwater";//Пароль пользователя//TODO need to add password
    private String url = "jdbc:mysql://localhost:3306/water";//URL адрес
    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера
    private Map<Class, DAOFactory.DaoCreator> creators;

    public Connection getContext() throws PersistException {
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
        creators.put(Role.class, new DaoCreator<Connection>() {
            @Override
            public GenericDAO create(Connection connection) {
                return new MySqlRoleDAO(MySqlDaoFactory.this, connection);
            }
        });
    }
}
