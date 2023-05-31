package test.service;

import dao.PersistException;
import dao.pool.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import service.Service;
import service.ServiceFactory;


public abstract class ServiceTest<T> extends Assert {




    public abstract void getByIdTest() throws PersistException;


    public abstract void getAllTest() throws PersistException;


    public abstract void persistTest() throws PersistException;


    public abstract void deleteTest() throws PersistException;


    public abstract void updateTest() throws PersistException;
}
