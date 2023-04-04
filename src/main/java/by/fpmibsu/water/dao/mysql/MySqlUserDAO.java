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

public class MySqlUserDAO extends AbstractJDBCDao<User, Integer> {

    private class PersistUser extends User {
        public void setId(int id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return "SELECT user_id, username, email, password_hash, last_seen, about_me FROM water.user";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO water.user (username, email, password_hash, last_seen, about_me) \n" +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE water.user SET username=?, email=?, password_hash=?, last_seen=?, about_me=? WHERE user_id= ?;";
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
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setLastSeen(rs.getDate("last_seen"));
                user.setAboutMe(rs.getString("about_me"));
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
            statement.setString(2,object.getEmail());
            statement.setString(3,object.getPasswordHash());
            statement.setDate(4, (Date) object.getLastSeen());
            statement.setString(5,object.getAboutMe());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getUsername());
            statement.setString(2,object.getEmail());
            statement.setString(3,object.getPasswordHash());
            statement.setDate(4, (Date) object.getLastSeen());
            statement.setString(5,object.getAboutMe());
            statement.setInt(6,object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
