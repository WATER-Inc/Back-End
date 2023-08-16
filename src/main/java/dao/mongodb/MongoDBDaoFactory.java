package dao.mongodb;

import dao.pool.ConnectionPool;

public class MongoDBDaoFactory extends ConnectionPool {
    private static final MongoDBDaoFactory instance = new MongoDBDaoFactory();

    public static MongoDBDaoFactory getInstance() {
        return instance;
    }
}
