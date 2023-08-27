package dao.mongodb;

import dao.DAOFactory;
import dao.mongodb.pool.MongoDBConnection;
import dao.pool.ConnectionPool;

import java.io.IOException;

public abstract class MongoDBDaoFactory implements DAOFactory<MongoDBConnection> {

}
