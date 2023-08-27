package dao.mongodb;

import dao.pool.ConnectionPool;

import java.io.IOException;

public abstract class MongoDBDaoFactory extends ConnectionPool {
    //private static final MongoDBDaoFactory instance = new MongoDBDaoFactory();
//
//    public static MongoDBDaoFactory getInstance() {
//        return instance;
//    }

    @Override
    protected String getConfigPath() throws IOException {
        return "database/mongodb/config.properties";
    }
}
