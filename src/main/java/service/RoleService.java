package service;

import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import dao.mysql.MySqlRoleDAO;
import entity.Entity;
import entity.Role;

import java.util.List;

public class RoleService extends Service {

    public RoleService(MySqlDaoFactory factory) throws PersistException {
        super(Role.class, factory);
    }

    @Override
    public Role getById(String id) throws PersistException {
        return (Role) super.getById(id);
    }

    @Override
    public List<Role> getAll() throws PersistException {
        return (List<Role>) super.getAll();
    }

    @Override
    public Role persist(Entity object) throws PersistException {
        return (Role) super.persist(object);
    }
    public Role getByTitle(String title) throws PersistException {
        return ((MySqlRoleDAO) genericDAO).getByTitle(title);
    }
}
