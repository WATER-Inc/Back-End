package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.dao.entity.ChatLink;
import by.fpmibsu.water.entity.Chat;
import by.fpmibsu.water.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlChatLinkDAO extends AbstractJDBCDao<ChatLink, String> {
    private final static String selectQ = "SELECT chat_user_id, chat_id, user_id, role_id FROM water.chat_user WHERE chat_user_id= ?;";
    private final static String selectByChat = "SELECT chat_user_id, chat_id, user_id, role_id FROM water.chat_user WHERE chat_id= ?;";
    private final static String selectALlQ = "SELECT chat_user_id, chat_id, user_id, role_id FROM water.chat_user";
    private final static String insertQ = "INSERT INTO water.chat_user (chat_id, user_id, role_id) \n" +
            "VALUES (?, ?, ?);";
    private final static String updateQ = "UPDATE water.chat_user SET chat_id=?, user_id=?, role_id=? WHERE chat_user_id= ?;";
    private final static String deleteQ = "DELETE FROM water.chat_user WHERE chat_user_id= ?;";
    private class PersistChatLink extends ChatLink {
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
    public ChatLink create() throws PersistException {
        ChatLink chatLink = new ChatLink();
        return persist(chatLink);
    }

    public MySqlChatLinkDAO(DAOFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<ChatLink> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<ChatLink> result = new LinkedList<ChatLink>();
        try {
            while (rs.next()) {
                PersistChatLink link = new PersistChatLink();
                link.setId(rs.getString("chat_user_id"));
                link.setChatId(rs.getString("chat_id"));
                link.setUserId(rs.getString("user_id"));
                link.setRoleId(rs.getString("role_id"));
                result.add(link);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ChatLink object) throws PersistException {
        try {
            statement.setString(1, object.getChatId());
            statement.setString(2, object.getUserId());
            statement.setString(3, object.getRoleId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ChatLink object) throws PersistException {
        try {
            statement.setString(1, object.getChatId());
            statement.setString(2, object.getUserId());
            statement.setString(3, object.getRoleId());
            statement.setString(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
    public List<ChatLink> getByChat(final Chat chat) throws PersistException {
        List<ChatLink> list;
        String sql = selectByChat;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chat.getId());
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }
}
