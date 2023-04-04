package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.dao.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlRoleDAO extends AbstractJDBCDao<Role, Integer> {

    private class PersistRole extends Role {
        public void setId(int id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return "SELECT role_id, user_id, title FROM water.role";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO water.role (user_id, title) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE water.role SET user_id=?, title=? WHERE role_id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM water.role WHERE role_id= ?;";
    }

    @Override
    public Role create() throws PersistException {
        Role role = new Role();
        return persist(role);
    }

    public MySqlRoleDAO(DAOFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Role> result = new LinkedList<Role>();
        try {
            while (rs.next()) {
                MySqlRoleDAO.PersistRole role = new MySqlRoleDAO.PersistRole();
                role.setId(rs.getInt("role_id"));
                role.setUserId(rs.getInt("user_id"));
                role.setTitle(rs.getString("title"));
                result.add(role);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Role object) throws PersistException {
        try {
            statement.setInt(1, object.getUserId());
            statement.setString(2, object.getTitle());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Role object) throws PersistException {
        try {
            statement.setInt(1, object.getUserId());
            statement.setString(2, object.getTitle());
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
