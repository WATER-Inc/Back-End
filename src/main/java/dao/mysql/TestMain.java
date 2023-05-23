package dao.mysql;

import dao.PersistException;
import entity.Chat;
import entity.Message;
import entity.User;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] argv) throws PersistException {
        MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
        MySqlUserDAO userDAO  = (MySqlUserDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), User.class);
    }
}
