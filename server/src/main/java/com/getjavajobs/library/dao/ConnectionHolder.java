package com.getjavajobs.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.getjavajobs.library.exceptions.DAOException;

/**
 * Класс - хранилище соединений.
 */
public class ConnectionHolder {

    private static ConnectionHolder instance;

    private Queue<Connection> connectionStore;
    private Lock commonLock;
    private Condition commonCondition;
    private ThreadLocal<ConnectionRef> busyConnections;

    private ConnectionHolder() {
        try {
            this.commonLock = new ReentrantLock();
            this.commonCondition = this.commonLock.newCondition();
            this.busyConnections = new ThreadLocal<>();
            this.connectionStore = new ArrayDeque<>();
            for (int i = 0; i < 10; i++) {
                this.connectionStore.add(createConnection());
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private Connection createConnection() throws SQLException, NamingException {
//    	Context initContext = new InitialContext();
//    	Context envContext  = (Context)initContext.lookup("java:/comp/env");
//    	DataSource ds = (DataSource)envContext.lookup("jdbc/library");
//    	return ds.getConnection();
    	return DriverManager.getConnection("jdbc:mysql://localhost:3306/Library", "root", "root");
    }

    // Получить экземпляр класса ConnectionHolder.
    public static ConnectionHolder getInstance() {
        if (instance == null) {
            instance = new ConnectionHolder();
        }
        return instance;
    }

    // Получить соединение, если возможно. Если соединение занято - обратившийся
    // поток будет ожидать
    // освобождения соединения.
    public Connection getConnection() {
        ConnectionRef connectionRef = this.busyConnections.get();
        if (connectionRef != null) {
            connectionRef.count++;
            return connectionRef.connection;
        }
        Connection connection = null;
        this.commonLock.lock();
        try {
            do {
                connection = this.connectionStore.poll();
                if (connection == null) {
                    try {
                        this.commonCondition.await();
                    } catch (InterruptedException e) {
                        throw new DAOException("Failed to get connection", e);
                    }
                }
            } while (connection == null);
        } finally {
            this.commonLock.unlock();
        }
        this.busyConnections.set(new ConnectionRef(connection));
        return connection;
    }

    // Освободить соединение.
    public void releaseConnection(Connection con) {
        ConnectionRef connectionRef = this.busyConnections.get();
        connectionRef.count--;
        if (connectionRef.count != 0) {
            return;
        }
        this.commonLock.lock();
        try {
            this.busyConnections.remove();
            this.connectionStore.add(con);
            this.commonCondition.signal();
        } finally {
            this.commonLock.unlock();
        }
    }
    private static class ConnectionRef {
        private Connection connection;
        private int count;
        public ConnectionRef(Connection connection) {
            this.connection = connection;
            this.count = 1;
        }
    }
}