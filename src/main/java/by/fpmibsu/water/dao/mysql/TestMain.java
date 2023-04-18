package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.entity.User;

public class TestMain {
    public static void main(String[] argv) throws PersistException {
        MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
        MySqlUserDAO userDAO = (MySqlUserDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getContext(), User.class);
        System.out.println(userDAO.getAll());
    }
}
