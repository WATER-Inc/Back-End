package dao.mysql;

import dao.PersistException;
import dao.AbstractJDBCDao;
import dao.DAOFactory;
import entity.User;

import java.sql.Connection;
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

    private static class PersistUser extends User {
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
                MySqlUserDAO.PersistUser user = new PersistUser();
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

    public User getByUsername(String username) throws PersistException {
        List<User> list;
        String sql = getSelectQuery() + " WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }
}
