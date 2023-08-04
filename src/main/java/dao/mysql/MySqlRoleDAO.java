package dao.mysql;

import dao.PersistException;
import dao.AbstractJDBCDao;
import dao.DAOFactory;
import entity.Role;

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

    private static class PersistRole extends Role {
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
                MySqlRoleDAO.PersistRole role = new PersistRole();
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
    public Role getByTitle(String title) throws PersistException {
        if(title == null)
            throw new PersistException("Empty role title.");
        List<Role> list;
        String sql = getSelectQuery() + " WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
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
