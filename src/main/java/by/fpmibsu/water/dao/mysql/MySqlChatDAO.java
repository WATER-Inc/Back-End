package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.dao.entity.ChatLink;
import by.fpmibsu.water.dao.entity.Participants;
import by.fpmibsu.water.entity.Role;
import by.fpmibsu.water.entity.Chat;
import by.fpmibsu.water.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlChatDAO extends AbstractJDBCDao<Chat, String> {
    private final static String selectQ = "SELECT name FROM water.chat WHERE chat_id= ?;";
    private final static String selectAllQ = "SELECT chat_id, name FROM water.chat";
    private final static String insertQ = "INSERT INTO water.chat (name) \n" +
            "VALUES (?);";
    private final static String updateQ = "UPDATE water.chat SET name=? WHERE chat_id= ?;";
    private final static String deleteQ = "DELETE FROM water.chat WHERE chat_id= ?;";
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
                chat.setId(rs.getString("chat_id"));
                chat.setName(rs.getString("name"));
                MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
                MySqlUserDAO userDAO = (MySqlUserDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), User.class);
                MySqlRoleDAO roleDAO = (MySqlRoleDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), Role.class);
                MySqlChatLinkDAO chatLinkDAO = (MySqlChatLinkDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), ChatLink.class);
                ArrayList<ChatLink> list = (ArrayList<ChatLink>) chatLinkDAO.getByChat(chat);
                Participants participants = new Participants();
                for(ChatLink chatLink : list){
                    participants.addUser(userDAO.getByPrimaryKey(chatLink.getUserId()), roleDAO.getByPrimaryKey(chatLink.getRoleId()));
                }
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