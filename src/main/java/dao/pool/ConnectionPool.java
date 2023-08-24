package dao.pool;


import dao.PersistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int startSize;
    private int checkConnectionTimeout;

    private final BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private final Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    protected ConnectionPool() {
    }

    public synchronized PooledConnection getConnection() throws PersistException {
        PooledConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                        } catch (SQLException e) {
                            logger.warn("Can't close connection");
                        }
                        connection = null;
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    logger.error("The limit of number of database connections is exceeded");
                    throw new PersistException();
                }
            } catch (InterruptedException | SQLException e) {
                logger.error("It is impossible to connect to a database", e);
                throw new PersistException(e);
            }
        }
        usedConnections.add(connection);
        logger.info(String.format("Connection was received from pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
        return connection;
    }

    synchronized void freeConnection(PooledConnection connection) {
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.info(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
                logger.warn("Can't close connection");
            }
        }
    }

    public synchronized void init() throws PersistException {
        try {
            destroy();
            setProperties();
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (ClassNotFoundException | SQLException | InterruptedException | IOException e) {
            logger.fatal("It is impossible to initialize connection pool", e);
            throw new PersistException(e);
        }
    }
    protected String getConfigPath() throws IOException {
        return "database/config.properties";
    }
    protected void setProperties() throws ClassNotFoundException, IOException {
        String configPath = getConfigPath();
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream in = classloader.getResourceAsStream(configPath);
        properties.load(in);
        Objects.requireNonNull(in).close();
       // try {
            Class.forName(properties.getProperty("DB_DRIVER_CLASS"));
        //}catch (ClassNotFoundException ignore){}
        this.url = properties.getProperty("DB_URL");
        this.user = properties.getProperty("DB_USER");
        this.password = properties.getProperty("DB_PASSWORD");
        this.maxSize = Integer.parseInt(properties.getProperty("DB_POOL_MAX_SIZE"));
        this.startSize = Integer.parseInt(properties.getProperty("DB_POOL_START_SIZE"));
        this.checkConnectionTimeout = Integer.parseInt(properties.getProperty("DB_POOL_CHECK_CONNECTION_TIMEOUT"));
    }

    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url, user, password), this);
    }

    public synchronized void destroy() {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                logger.warn("Can't close connection");
            }
        }
        usedConnections.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }
}

