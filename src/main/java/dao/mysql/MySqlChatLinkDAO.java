package dao.mysql;

import dao.PersistException;
import dao.AbstractJDBCDao;
import dao.DAOFactory;
import dao.entity.ChatLink;
import dao.entity.Participants;
import entity.Chat;
import entity.Role;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlChatLinkDAO extends AbstractJDBCDao<ChatLink, String> {
    private final static String selectQ = "SELECT * FROM water.chat_user";
    private final static String selectUsersByChat = "SELECT link.user_id, link.role_id FROM chat_user link WHERE link.chat_id = ?;";
    private final static String selectChatsByUser = "SELECT link.user_id, link.role_id FROM chat_user link WHERE link.user_id = ?;";
    private final static String selectALlQ = "SELECT * FROM water.chat_user;";
    private final static String insertQ = "INSERT INTO water.chat_user (chat_id, user_id, role_id) \n" + "VALUES (?, ?, ?);";
    private final static String updateQ = "UPDATE water.chat_user SET chat_id=?, user_id=?, role_id=? WHERE id= ?;";
    private final static String deleteQ = "DELETE FROM water.chat_user WHERE id= ?;";

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
                link.setId(rs.getString("id"));
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

    public Participants getParticipants(final Chat chat) throws PersistException {
        String sql = selectUsersByChat;
        Participants participants = new Participants();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chat.getId());
            ResultSet rs = statement.executeQuery();
            MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
            MySqlRoleDAO roleDAO = (MySqlRoleDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), Role.class);
            MySqlUserDAO userDAO = (MySqlUserDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), User.class);
            while (rs.next())
                participants.addUser(userDAO.getByPrimaryKey(rs.getString("user_id")), roleDAO.getByPrimaryKey(rs.getString("role_id")));
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return participants;
    }

    public List<Chat> getChats(final User user) throws PersistException {
        String sql = selectChatsByUser;
        List<Chat> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getId());
            ResultSet rs = statement.executeQuery();
            MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
            MySqlChatDAO chatDAO = (MySqlChatDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), Chat.class);
            while (rs.next())
                list.add(chatDAO.getByPrimaryKey(rs.getString("chat_id")));
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

}
