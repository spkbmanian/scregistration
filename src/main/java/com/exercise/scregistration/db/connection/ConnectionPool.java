package com.exercise.scregistration.db.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool<T> {

    Connection getConnection() throws SQLException,InterruptedException;
    
    boolean releaseConnection(Connection connection) ;
    
    T getConnectionPool();
    
    int getSize();
    
    String getUrl();
    
    String getUser();

    String getPassword();
    
    void shutdown() throws SQLException;
}   