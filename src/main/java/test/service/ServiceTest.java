package test.service;

import dao.PersistException;
import entity.Entity;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.Service;
import service.ServiceFactory;

import java.util.List;

public abstract class ServiceTest<T> extends Assert {
    protected Service service;
    protected static final ServiceFactory factory = new ServiceFactory();

    @BeforeTest
    public abstract void init();

    @DataProvider
    public abstract Object[][] parseLocaleData();

    @Test(dataProvider = "parseLocaleData")
    public abstract void getByIdTest();

    @Test(dataProvider = "parseLocaleData")
    public abstract void getAllTest();

    @Test(dataProvider = "parseLocaleData")
    public abstract void persistTest();

    @Test(dataProvider = "parseLocaleData")
    public abstract void deleteTest();

    @Test(dataProvider = "parseLocaleData")
    public abstract void updateTest();
}
