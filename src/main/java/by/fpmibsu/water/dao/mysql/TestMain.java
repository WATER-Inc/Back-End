package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.entity.User;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] argv) throws PersistException {
        MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
        MySqlUserDAO userDAO = (MySqlUserDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), User.class);
        User user = new User();
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String id = scanner.next();
            String username = scanner.next();
            String password = scanner.next();
            user.setId(id);
            user.setUsername(username);
            user.setPasswordHash(password);
            try {
                userDAO.update(user);
            }
            catch (PersistException e){
                e.printStackTrace();
            }
        }
    }
}
