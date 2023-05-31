package test.service;


import dao.PersistException;
import entity.Role;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = {"service"})
public class RoleServiceTest extends ServiceTest<Role> {

    @BeforeTest(groups = {"service"})
    @Override
    public void init() throws PersistException {
        super.init();
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
