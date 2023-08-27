package dao.mysql.pool;

import dao.PersistException;
import dao.pool.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

final public class MySqlConnectionPool extends ConnectionPool<MySqlConnection> {
    private static final MySqlConnectionPool instance = new MySqlConnectionPool();

    public static MySqlConnectionPool getInstance() {
        return instance;
    }

    public MySqlConnectionPool() {
    }

    @Override
    protected boolean isValid(MySqlConnection connection) throws PersistException {
        try {
            return connection.isValid(getCheckConnectionTimeout());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected String getConfigPath() throws IOException {
        return "database/mysql/config.properties";
    }

    @Override
    protected MySqlConnection createConnection() throws PersistException {
        try {
            return new MySqlConnection(DriverManager.getConnection(getUrl(), getUser(), getPassword()), this);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void setProperties() throws IOException {
        super.setProperties();
        String configPath = getConfigPath();
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream in = classloader.getResourceAsStream(configPath);
        properties.load(in);
        Objects.requireNonNull(in).close();
         try {
            Class.forName(properties.getProperty("DB_DRIVER_CLASS"));//TODO only for jdbc
        }catch (ClassNotFoundException ignore){
             throw new IOException(ignore);
         }
    }
}
