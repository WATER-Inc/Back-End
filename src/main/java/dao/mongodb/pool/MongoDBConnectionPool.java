package dao.mongodb.pool;

import dao.mysql.pool.MySqlConnectionPool;
import dao.pool.ConnectionPool;

import java.io.IOException;

public class MongoDBConnectionPool  extends ConnectionPool {
    private static final MongoDBConnectionPool instance = new MongoDBConnectionPool();

    public static MongoDBConnectionPool getInstance() {
        return instance;
    }

    @Override
    protected String getConfigPath() throws IOException {
        return "database/mongodb/config.properties";
    }
}
