package test.service;

import dao.PersistException;
import dao.pool.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.Service;
import service.ServiceFactory;


public abstract class ServiceTest<T> extends Assert {
    protected Service service;
    protected static final ServiceFactory factory = new ServiceFactory();

    public static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/water?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "20November3;5.-65@1234";
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;
    @BeforeTest(groups = {"service"})
    public void init() throws PersistException {
        ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
    };


    @Test(groups = {"service"})
    public abstract void getByIdTest() throws PersistException;

    @Test(groups = {"service"})
    public abstract void getAllTest() throws PersistException;

    @Test(groups = {"service"})
    public abstract void persistTest() throws PersistException;

    @Test(groups = {"service"})
    public abstract void deleteTest() throws PersistException;

    @Test(groups = {"service"})
    public abstract void updateTest() throws PersistException;
}
