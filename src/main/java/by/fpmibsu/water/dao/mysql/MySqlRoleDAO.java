package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlRoleDAO extends AbstractJDBCDao<Role, String> {

    private final static String selectQ = "SELECT * FROM water.role";
    private final static String selectALlQ = "SELECT * FROM water.role";
    private final static String insertQ = "INSERT INTO water.role (title) \n" + "VALUES (?);";
    private final static String updateQ = "UPDATE water.role SET title=? WHERE id= ?;";
    private final static String deleteQ = "DELETE FROM water.role WHERE id= ?;";

    private class PersistRole extends Role {
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
                role.setId(rs.getString("id"));
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
            statement.setString(1, object.getTitle());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Role object) throws PersistException {
        try {
            statement.setString(1, object.getTitle());
            statement.setString(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
