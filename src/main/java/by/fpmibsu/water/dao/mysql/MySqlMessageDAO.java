package by.fpmibsu.water.dao.mysql;


import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.entity.Chat;
import by.fpmibsu.water.entity.Message;
import by.fpmibsu.water.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlMessageDAO extends AbstractJDBCDao<Message, String> {
    private final static String selectQ = "SELECT * FROM water.message WHERE message_id=?;";
    private final static String selectAllQ = "SELECT * FROM water.message";
    private final static String insertQ = "INSERT INTO water.message (sender_id, chat_id, content, created_date) \n" +
            "VALUES (?);";
    private final static String updateQ = "UPDATE water.message SET content=? WHERE message_id= ?;";
    private final static String deleteQ = "DELETE FROM water.message WHERE message_id= ?;";
    private class PersistMessage extends Message {
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
    public Message create() throws PersistException {
        Message Message = new Message();
        return persist(Message);
    }

    public MySqlMessageDAO(DAOFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<Message> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Message> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistMessage Message = new PersistMessage();
                Message.setId(rs.getString("message_id"));
                String SenderId = rs.getString("sender_id");
                String ChatId = rs.getString("chat_id");
                MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
                MySqlUserDAO userDAO = (MySqlUserDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getContext(), User.class);
                MySqlChatDAO chatDAO = (MySqlChatDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getContext(), Chat.class);
                Message.setSender(userDAO.getByPrimaryKey(SenderId));
                Message.setChat(chatDAO.getByPrimaryKey(ChatId));
                Message.setContent(rs.getString("content"));
                result.add(Message);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Message object) throws PersistException {
        try {
            statement.setString(1, object.getSender().getId());
            statement.setString(2, object.getChat().getId());
            statement.setString(3, object.getContent());
            statement.setString(4, object.getDate().toString());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Message object) throws PersistException {
        try {
            statement.setString(1, object.getContent());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
