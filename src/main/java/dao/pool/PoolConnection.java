package dao.pool;

import dao.PersistException;

import java.sql.*;

public abstract class PoolConnection<Connection> implements Comparable<PoolConnection> {
    private final Connection connection;
    private final ConnectionPool pool;
    public PoolConnection(Connection connection, ConnectionPool pool_) {
        this.connection = connection;
        this.pool = pool_;
    }

    public Connection getConnection() {
        return connection;
    }


    public void close() throws SQLException {
        if(pool != null)
            pool.freeConnection(this);
    }

    @Override
    public int compareTo(PoolConnection connection) {
        return hashCode() - connection.hashCode();
    }


    public abstract void closePermanently() throws PersistException;
    public abstract void free() throws PersistException;

    public ConnectionPool getPool() {
        return pool;
    }
}
