package dao.mysql.pool;

import dao.pool.ConnectionPool;

final public class MySqlConnectionPool extends ConnectionPool {
    private static final MySqlConnectionPool instance = new MySqlConnectionPool();

    public static MySqlConnectionPool getInstance() {
        return instance;
    }
}
