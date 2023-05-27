package test.service;

import entity.User;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.ServiceFactory;

public class UserServiceTest extends ServiceTest<User> {

    @BeforeTest
    @Override
    public void init() {
        service = factory.getService(User.class);
    }

    @DataProvider
    @Override
    public Object[][] parseLocaleData() {
        return new Object[0][];
    }

    @Override
    public void getByIdTest() {

    }

    @Override
    public void getAllTest() {

    }

    @Override
    public void persistTest() {

    }

    @Override
    public void deleteTest() {

    }

    @Override
    public void updateTest() {

    }
}
