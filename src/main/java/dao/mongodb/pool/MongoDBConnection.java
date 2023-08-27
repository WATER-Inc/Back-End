package dao.mongodb.pool;

import com.mongodb.client.MongoClient;
import dao.PersistException;
import dao.mysql.pool.MySqlConnectionPool;
import dao.pool.ConnectionPool;
import dao.pool.PoolConnection;

public class MongoDBConnection extends PoolConnection<MongoClient> {
    public MongoDBConnection(MongoClient mongoClient, MongoDBConnectionPool pool_) {
        super(mongoClient, pool_);
    }

    @Override
    public void closePermanently() throws PersistException {
        getConnection().close();
    }

    @Override
    public void free() throws PersistException {
        //TODO
    }
}
