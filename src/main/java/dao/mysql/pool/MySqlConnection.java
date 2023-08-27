package dao.mysql.pool;


import dao.PersistException;
import dao.pool.ConnectionPool;
import dao.pool.PoolConnection;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MySqlConnection extends PoolConnection<Connection> implements Connection {

    public MySqlConnection(Connection connection, MySqlConnectionPool pool_) {
        super(connection, pool_);
    }
    public Connection getConnection() {
        return getConnection();
    }

    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return getConnection().isWrapperFor(arg0);
    }


    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return getConnection().unwrap(arg0);
    }

    public void abort(Executor arg0) throws SQLException {
        getConnection().abort(arg0);
    }

    public void clearWarnings() throws SQLException {
        getConnection().clearWarnings();
    }

    public void commit() throws SQLException {
        getConnection().commit();
    }

    public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
        return getConnection().createArrayOf(arg0, arg1);
    }

    public Blob createBlob() throws SQLException {
        return getConnection().createBlob();
    }

    public Clob createClob() throws SQLException {
        return getConnection().createClob();
    }

    public NClob createNClob() throws SQLException {
        return getConnection().createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return getConnection().createSQLXML();
    }

    public Statement createStatement() throws SQLException {
        return getConnection().createStatement();
    }

    public Statement createStatement(int arg0, int arg1) throws SQLException {
        return getConnection().createStatement(arg0, arg1);
    }

    public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
        return getConnection().createStatement(arg0, arg1, arg2);
    }

    public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
        return getConnection().createStruct(arg0, arg1);
    }

    public boolean getAutoCommit() throws SQLException {
        return getConnection().getAutoCommit();
    }

    public String getCatalog() throws SQLException {
        return getConnection().getCatalog();
    }

    public Properties getClientInfo() throws SQLException {
        return getConnection().getClientInfo();
    }

    public String getClientInfo(String arg0) throws SQLException {
        return getConnection().getClientInfo(arg0);
    }

    public int getHoldability() throws SQLException {
        return getConnection().getHoldability();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return getConnection().getMetaData();
    }

    public int getNetworkTimeout() throws SQLException {
        return getConnection().getNetworkTimeout();
    }

    public String getSchema() throws SQLException {
        return getConnection().getSchema();
    }

    public int getTransactionIsolation() throws SQLException {
        return getConnection().getTransactionIsolation();
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return getConnection().getTypeMap();
    }

    public SQLWarning getWarnings() throws SQLException {
        return getConnection().getWarnings();
    }

    public boolean isClosed() throws SQLException {
        return getConnection().isClosed();
    }

    public boolean isReadOnly() throws SQLException {
        return getConnection().isReadOnly();
    }

    public boolean isValid(int arg0) throws SQLException {
        return getConnection().isValid(arg0);
    }

    public String nativeSQL(String arg0) throws SQLException {
        return getConnection().nativeSQL(arg0);
    }

    public CallableStatement prepareCall(String arg0) throws SQLException {
        return getConnection().prepareCall(arg0);
    }

    public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
        return getConnection().prepareCall(arg0, arg1, arg2);
    }

    public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        return getConnection().prepareCall(arg0, arg1, arg2, arg3);
    }

    public PreparedStatement prepareStatement(String arg0) throws SQLException {
        return getConnection().prepareStatement(arg0);
    }

    public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
        return getConnection().prepareStatement(arg0, arg1);
    }

    public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
        return getConnection().prepareStatement(arg0, arg1);
    }

    public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
        return getConnection().prepareStatement(arg0, arg1);
    }

    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
        return getConnection().prepareStatement(arg0, arg1, arg2);
    }

    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
        return getConnection().prepareStatement(arg0, arg1, arg2, arg3);
    }

    public void releaseSavepoint(Savepoint arg0) throws SQLException {
        getConnection().releaseSavepoint(arg0);
    }

    public void rollback() throws SQLException {
        getConnection().rollback();
    }

    public void rollback(Savepoint arg0) throws SQLException {
        getConnection().rollback(arg0);
    }

    public void setAutoCommit(boolean arg0) throws SQLException {
        getConnection().setAutoCommit(arg0);
    }

    public void setCatalog(String arg0) throws SQLException {
        getConnection().setCatalog(arg0);
    }

    public void setClientInfo(Properties arg0) throws SQLClientInfoException {
        getConnection().setClientInfo(arg0);
    }

    public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
        getConnection().setClientInfo(arg0, arg1);
    }

    public void setHoldability(int arg0) throws SQLException {
        getConnection().setHoldability(arg0);
    }

    public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
        getConnection().setNetworkTimeout(arg0, arg1);
    }

    public void setReadOnly(boolean arg0) throws SQLException {
        getConnection().setReadOnly(arg0);
    }

    public Savepoint setSavepoint() throws SQLException {
        return getConnection().setSavepoint();
    }

    public Savepoint setSavepoint(String arg0) throws SQLException {
        return getConnection().setSavepoint(arg0);
    }

    public void setSchema(String arg0) throws SQLException {
        getConnection().setSchema(arg0);
    }

    public void setTransactionIsolation(int arg0) throws SQLException {
        getConnection().setTransactionIsolation(arg0);
    }

    public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
        getConnection().setTypeMap(arg0);
    }


    @Override
    public void closePermanently() throws PersistException {
        try {
            getConnection().close();
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void free() throws PersistException {
        try {
            getConnection().clearWarnings();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            throw new PersistException(e);
        }

    }
}

