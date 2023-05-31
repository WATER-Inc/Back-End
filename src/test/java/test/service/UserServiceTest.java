package test.service;

import dao.PersistException;
import dao.pool.ConnectionPool;
import entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.Service;
import service.ServiceFactory;
import service.UserService;

import java.util.List;

@Test(groups = {"service"})
public class UserServiceTest extends ServiceTest<User> {

    protected Service service;
    protected static final ServiceFactory factory = new ServiceFactory();

    public static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/water?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "20November3;5.-65@1234";
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;
    @BeforeClass(groups = {"service"})
    public void init() throws PersistException {
        ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        service = factory.getService(User.class);
    }
    @Test(groups = {"service"})
    @Override
    public void getByIdTest() throws PersistException {
        User user = new User();
        user.setUsername("johndoe");
        user.setPasswordHash("password");
        user = (User) service.persist(user);
        User retrievedUser = (User) service.getById(user.getId());
        service.delete(user);
        assertEquals(user, retrievedUser);
    }
    @Test(groups = {"service"})
    @Override
    public void getAllTest() throws PersistException {
        User user1 = new User();
        user1.setUsername("johndoe");
        user1.setPasswordHash("password");
        user1 = (User) service.persist(user1);
        User user2 = new User();
        user2.setUsername("janedoe");
        user2.setPasswordHash("password");
        user2 = (User) service.persist(user2);
        List<User> userList = (List<User>) service.getAll();
        service.delete(user1);
        service.delete(user2);
        assertEquals(2, userList.size());
        assertTrue(userList.contains(user1));
        assertTrue(userList.contains(user2));
    }
    @Test(groups = {"service"})
    @Override
    public void persistTest() throws PersistException {
        User user = new User();
        user.setUsername("johndoe");
        user.setPasswordHash("password");
        User persistedUser = (User) service.persist(user);
        assertNotNull(persistedUser.getId());
        User retrievedUser = (User) service.getById(persistedUser.getId());
        service.delete(persistedUser);
        assertEquals(persistedUser, retrievedUser);
    }
    @Test(groups = {"service"})
    @Override
    public void deleteTest() throws PersistException {
        User user = new User();
        user.setUsername("johndoe");
        user.setPasswordHash("password");
        user = (User) service.persist(user);
        service.delete(user);
        assertNull(service.getById(user.getId()));
    }
    @Test(groups = {"service"})
    @Override
    public void updateTest() throws PersistException {
        User user = new User();
        user.setUsername("johndoe");
        user.setPasswordHash("password");
        user = (User) service.persist(user);
        user.setPasswordHash("newpassword");
        service.update(user);
        User retrievedUser = (User) service.getById(user.getId());
        service.delete(user);
        assertEquals(user, retrievedUser);
    }

    @Test(groups = {"service"})
    public void getByUsernameAndPasswordTest() throws PersistException {
        User user = new User();
        user.setUsername("johndoe");
        user.setPasswordHash("password");
        user = (User) service.persist(user);
        User retrievedUser = ((UserService) service).getByUsernameAndPassword("johndoe", "password");
        service.delete(user);
        assertEquals(user, retrievedUser);
    }
}
