package dao.mysql;


import dao.PersistException;
import dao.AbstractJDBCDao;
import dao.DAOFactory;
import entity.Chat;
import entity.Message;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlMessageDAO extends AbstractJDBCDao<Message, String> {
    private final static String selectQ = "SELECT * FROM water.messages";
    private final static String selectAllQ = "SELECT * FROM water.messages";
    private final static String insertQ = "INSERT INTO water.messages (sender_id, chat_id, content, created_date) \n" +
            "VALUES (?, ?, ?, ?);";
    private final static String selectByUser = "SELECT messages.id FROM water.messages WHERE messages.sender_id = ?;";
    private final static String selectByChat = "SELECT messages.id FROM water.messages WHERE messages.chat_id = ?;";
    private final static String updateQ = "UPDATE water.messages SET content=? WHERE id= ?;";
    private final static String deleteQ = "DELETE FROM water.messages WHERE id= ?;";

    private class PersistMessage extends Message {
        public void setId(String id) {
            super.setId(id);
        }
    }

    public String getByUser() {
        return selectByUser;
    }

    public String getByChat() {
        return selectByChat;
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
                Message.setId(rs.getString("id"));
                String SenderId = rs.getString("sender_id");
                String ChatId = rs.getString("chat_id");
                User sender = new User();
                sender.setId(SenderId);
                Message.setSender(sender);
                Chat chat = new Chat();
                chat.setId(ChatId);
                Message.setChat(chat);
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
            statement.setString(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public List<Message> getMessages(final Chat chat) throws PersistException {
        String sql = getByChat();
        List<Message> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chat.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getString("id"));
                list.add(message);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }
    public List<Message> getMessages(final User user) throws PersistException {
        String sql = getByUser();
        List<Message> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getString("id"));
                list.add(message);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }
}
