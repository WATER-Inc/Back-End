package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.dao.entity.ChatLink;
import by.fpmibsu.water.entity.Chat;
import by.fpmibsu.water.entity.Message;
import by.fpmibsu.water.entity.Role;
import by.fpmibsu.water.entity.User;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] argv) throws PersistException {
        MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
        MySqlMessageDAO messageDAO  = (MySqlMessageDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), Message.class);
        Message message = new Message();

        while(true) {
            Scanner scanner = new Scanner(System.in);
            String id = scanner.next();
            Chat chat = new Chat();
            chat.setId(id);
            try {
                System.out.println(messageDAO.getMessages(chat));
                //System.out.println(chatLinkDAO.getByChat(chat));
            }
            catch (PersistException e){
                e.printStackTrace();
            }
        }
    }
}
