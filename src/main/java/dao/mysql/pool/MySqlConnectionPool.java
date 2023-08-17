package dao.mysql.pool;

import dao.pool.ConnectionPool;

import java.io.IOException;

final public class MySqlConnectionPool extends ConnectionPool {
    private static final MySqlConnectionPool instance = new MySqlConnectionPool();

    public static MySqlConnectionPool getInstance() {
        return instance;
    }

    @Override
    protected String getConfigPath() throws IOException {
        return "database/mysql/config.properties";
    }
}
