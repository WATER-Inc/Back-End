package by.fpmibsu.water.dao.mysql;

import by.fpmibsu.water.dao.PersistException;
import by.fpmibsu.water.entity.Role;
import by.fpmibsu.water.entity.User;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] argv) throws PersistException {
        MySqlDaoFactory mySqlDaoFactory = new MySqlDaoFactory();
        MySqlRoleDAO roleDAO = (MySqlRoleDAO) mySqlDaoFactory.getDao(mySqlDaoFactory.getConnection(), Role.class);
        Role role = new Role();
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String id = scanner.next();
            String title = scanner.next();
            role.setTitle(title);
            try {
                roleDAO.persist(role);
                //System.out.println(roleDAO.getAll());
                //roleDAO.delete(role);
               // roleDAO.update(role);
            }
            catch (PersistException e){
                e.printStackTrace();
            }
        }
    }
}
