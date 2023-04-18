package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.AbstractJDBCDao;
import by.fpmibsu.water.dao.DAOFactory;
import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.dao.entity.Participants;
import by.fpmibsu.water.entity.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlParticipantsDAO extends AbstractJDBCDao<Participants, String> {

    private class PersistParticipants extends Chat {
        public void setId(String id) {
            super.setId(id);
        }
    }


    @Override
    public String getSelectQuery() {
        return "SELECT chat_user_id, chat_id, user_id, role_id FROM water.chat_user";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO water.chat_user (chat_id, user_id, role_id) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE water.chat_user SET chat_id=?, user_id=?, role_id=? WHERE chat_user_id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM water.chat_user WHERE chat_user_id= ?;";
    }

    @Override
    public Participants create() throws PersistException {
        Participants participants = new Participants();
        return persist(participants);
    }

    public MySqlParticipantsDAO(DAOFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<Participants> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Chat> result = new LinkedList<Chat>();
        try {
            while (rs.next()) {
                MySqlParticipantsDAO.PersistParticipants participants = new PersistParticipants();
                participants.setId(rs.getString("chat_id"));
                chat.setName(rs.getString("name"));
                //TODO need to add SELECT all users to list;
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