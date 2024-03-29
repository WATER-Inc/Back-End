package dao.mysql;

import dao.PersistException;
import dao.AbstractJDBCDao;
import dao.DAOFactory;
import entity.Chat;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class MySqlChatDAO extends AbstractJDBCDao<Chat, String> {
    private final static String selectQ = "SELECT * FROM water.chat";
    private final static String selectAllQ = "SELECT * FROM water.chat";
    private final static String selectByUser = "SELECT * FROM water.chat WHERE water.chat.id IN (SELECT water.chat_user.chat_id FROM water.chat_user WHERE user_id = ?);";
    private final static String insertQ = "INSERT INTO water.chat (name) \n" + "VALUES (?);";
    private final static String updateQ = "UPDATE water.chat SET name=? WHERE id= ?;";
    private final static String deleteQ = "DELETE FROM water.chat WHERE id= ?;";
    private final static String lastDateQ = "SELECT created_date from water.messages WHERE chat_id = ? ORDER BY created_date DESC LIMIT 1;";
    private static class PersistChat extends Chat {
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

    public String getSelectByUser(){return selectByUser;}
    protected String getLastDateQ(){return lastDateQ;}
    @Override
    public Chat create() throws PersistException {
        Chat chat = new Chat();
        return persist(chat);
    }

    public MySqlChatDAO(DAOFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<Chat> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Chat> result = new LinkedList<Chat>();
        try {
            while (rs.next()) {
                PersistChat chat = new PersistChat();
                chat.setId(rs.getString("id"));
                chat.setName(rs.getString("name"));
                chat.setLastMessageDate(getLastMessageDate(chat));
                result.add(chat);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Chat object) throws PersistException {
        try {
            statement.setString(1, object.getName());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Chat object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    public List<Chat> getByUser(final User user) throws PersistException {
        String sql = getSelectByUser();
        List<Chat> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                list.add(getByPrimaryKey(rs.getString("id")));
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }
    protected java.util.Date getLastMessageDate(Chat chat) throws PersistException {
        String sql = getLastDateQ();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chat.getId());
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                return new java.util.Date(rs.getTimestamp("created_date").getTime());
            else
                return null;
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

}