package dao.mysql;

import dao.PersistException;
import entity.Chat;
import entity.Message;

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
