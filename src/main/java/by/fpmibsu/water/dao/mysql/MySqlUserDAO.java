package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlUserDAO extends AbstractJDBCDao<User, String> {
    private final static String selectQ = "SELECT * FROM water.user";
    private final static String selectALlQ = "SELECT * FROM water.user";
    private final static String insertQ = "INSERT INTO water.user (username, password_hash) \n" + "VALUES (?, ?);";
    private final static String updateQ = "UPDATE water.user SET username=?, password_hash=? WHERE id= ?;";
    private final static String deleteQ = "DELETE FROM water.user WHERE id= ?;";
    private class PersistUser extends User {
        public void setId(String id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return selectQ;
    }

    @Override
    public String getSelectAllQuery() {
        return selectALlQ;
    }

    @Override
    public String getCreateQuery() {
        return insertQ;
    }

    @Override
    public String getUpdateQuery() {
        return updateQ;
    }

    @Override
    public String getDeleteQuery() {
        return deleteQ;
    }

    @Override
    public User create() throws PersistException {
        User user = new User();
        return persist(user);
    }

    public MySqlUserDAO(DAOFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<User> result = new LinkedList<User>();
        try {
            while (rs.next()) {
                MySqlUserDAO.PersistUser user = new MySqlUserDAO.PersistUser();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                result.add(user);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPasswordHash());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPasswordHash());
            statement.setString(3, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
