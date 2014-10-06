package com.getjavajobs.library.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionHolder {

    private static ConnectionHolder instance;

    public static ConnectionHolder getInstance() {
        if (instance == null) {
            instance = new ConnectionHolder();
        }
        return instance;
    }

    public Connection getConnection() {
    	try {
        	Context initContext = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	DataSource ds = (DataSource)envContext.lookup("jdbc/library");
			return ds.getConnection();
		} catch (Exception e) {
			return null;
		}
    }

    // Освободить соединение.
    public void releaseConnection(Connection con) {
        try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}