package service;

import dao.PersistException;
import entity.Entity;
import entity.Role;

import java.util.List;

public class RoleService extends Service {

    public RoleService() throws PersistException {
        super(Role.class);
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
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
