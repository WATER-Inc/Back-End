package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.dao.entity.Follower;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlFollowerDAO extends AbstractJDBCDao<Follower, String> {
    private final static String selectQ = "SELECT * FROM water.followers WHERE followed_id=? ;";
    private final static String selectAllQ = "SELECT * FROM water.followers";
    private final static String insertQ = "INSERT INTO water.followers (follower_id, followed_id) \n" +
            "VALUES (?);";
    private final static String updateQ = "UPDATE water.followers SET follower_id=? WHERE followed_id= ? AND follower_id= ?;";
    private final static String deleteQ = "DELETE FROM water.followers WHERE followed_id= ? AND follower_id= ?;";

    private class PersistFollower extends Follower {
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
        return selectAllQ;
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
    public Follower create() throws PersistException {
        Follower follower = new Follower();
        return persist(follower);
    }

    public MySqlFollowerDAO(DAOFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<Follower> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Follower> result = new LinkedList<>();
        try {
            while (rs.next()) {
                MySqlFollowerDAO.PersistFollower Follower = new MySqlFollowerDAO.PersistFollower();
                Follower.setFollowerId(rs.getString("follower_id"));
                Follower.setFollowedId(rs.getString("follower_id"));
                result.add(Follower);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Follower object) throws PersistException {
        try {
            statement.setString(1,object.getFollowerId());
            statement.setString(2,object.getFollowedId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Follower object) throws PersistException {
        try {
            statement.setString(1, object.getFollowerId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }


}