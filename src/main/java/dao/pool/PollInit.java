package dao.pool;

import dao.PersistException;
import dao.mongodb.pool.MongoDBConnectionPool;
import dao.mysql.pool.MySqlConnectionPool;

public class PollInit {
    public static void init() throws PersistException {
        MySqlConnectionPool.getInstance().init();
       // MongoDBConnectionPool.getInstance().init();
    }
}
