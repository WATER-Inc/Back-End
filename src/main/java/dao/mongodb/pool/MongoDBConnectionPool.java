package dao.mongodb.pool;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dao.PersistException;
import dao.mysql.pool.MySqlConnectionPool;
import dao.pool.ConnectionPool;

import java.io.IOException;

public class MongoDBConnectionPool  extends ConnectionPool<MongoDBConnection> {
    private static final MongoDBConnectionPool instance = new MongoDBConnectionPool();

    public static MongoDBConnectionPool getInstance() {
        return instance;
    }

    @Override
    protected boolean isValid(MongoDBConnection connection) throws PersistException {
        return true;//TODO create valid method
    }

    @Override
    protected String getConfigPath() throws IOException {
        return "database/mongodb/config.properties";
    }

    @Override
    protected MongoDBConnection createConnection() throws PersistException {
        MongoClient client = MongoClients.create(getUrl());
        return new MongoDBConnection(client, this);
    }
}
