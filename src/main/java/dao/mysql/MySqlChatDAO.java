package dao.mysql;

import dao.PersistException;
import dao.AbstractJDBCDao;
import dao.DAOFactory;
import dao.entity.ChatLink;
import entity.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlChatDAO extends AbstractJDBCDao<Chat, String> {
    private final static String selectQ = "SELECT * FROM water.chat";
    private final static String selectAllQ = "SELECT * FROM water.chat";
    private final static String insertQ = "INSERT INTO water.chat (name) \n" + "VALUES (?);";
    private final static String updateQ = "UPDATE water.chat SET name=? WHERE id= ?;";
    private final static String deleteQ = "DELETE FROM water.chat WHERE id= ?;";

    private class PersistChat extends Chat {
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
                MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
                MySqlChatLinkDAO chatLinkDAO = (MySqlChatLinkDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), ChatLink.class);
                chat.setParticipants(chatLinkDAO.getParticipants(chat));
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


}