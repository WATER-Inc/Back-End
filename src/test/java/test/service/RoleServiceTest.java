package test.service;


import dao.PersistException;
import dao.pool.ConnectionPool;
import entity.Role;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.Service;
import service.ServiceFactory;

import java.util.List;

@Test(groups = {"service"})
public class RoleServiceTest extends ServiceTest<Role> {

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
        service = factory.getService(Role.class);
    }
    @Test(groups = {"service"})
    @Override
    public void getByIdTest() throws PersistException {
        Role role = new Role("test");
        role = (Role) service.persist(role);
        Role retrievedRole = (Role) service.getById(role.getId());
        service.delete(role);
        assertEquals(role, retrievedRole);
    }
    @Test(groups = {"service"})
    @Override
    public void getAllTest() throws PersistException {
        Role role1 = new Role("test1");
        role1 = (Role) service.persist(role1);
        Role role2 = new Role("test2");
        role2 = (Role) service.persist(role2);
        List<Role> roleList = (List<Role>) service.getAll();
        service.delete(role1);
        service.delete(role2);
        assertEquals(2, roleList.size());
        assertTrue(roleList.contains(role1));
        assertTrue(roleList.contains(role2));
    }
    @Test(groups = {"service"})
    @Override
    public void persistTest() throws PersistException {
        Role role = new Role("test");
        Role persistedRole = (Role) service.persist(role);
        assertNotNull(persistedRole.getId());
        Role retrievedRole = (Role) service.getById(persistedRole.getId());
        service.delete(persistedRole);
        assertEquals(persistedRole, retrievedRole);
    }
    @Test(groups = {"service"})
    @Override
    public void deleteTest() throws PersistException {
        Role role = new Role("test");
        role = (Role) service.persist(role);
        service.delete(role);
        assertNull(service.getById(role.getId()));
    }
    @Test(groups = {"service"})
    @Override
    public void updateTest() throws PersistException {
        Role role = new Role("test");
        role = (Role) service.persist(role);
        role.setTitle("updated");
        service.update(role);
        Role retrievedRole = (Role) service.getById(role.getId());
        service.delete(role);
        assertEquals(role, retrievedRole);
    }
}
