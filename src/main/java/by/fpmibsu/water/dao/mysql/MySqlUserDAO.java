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

    private class PersistUser extends User {
        public void setId(String id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return "SELECT user_id, username, password_hash FROM water.user";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO water.user (username, password_hash) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE water.user SET username=?, password_hash=? WHERE user_id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM water.user WHERE user_id= ?;";
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
                user.setId(rs.getString("user_id"));
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
